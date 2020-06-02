package org.ibs.cdx.gode.codegen.velocity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.ibs.cdx.gode.codegen.velocity.app.AppVelocityModel;
import org.ibs.cdx.gode.codegen.velocity.endpoint.DeploymentOpts;
import org.ibs.cdx.gode.codegen.velocity.entity.EntityVelocityModel;
import org.ibs.cdx.gode.codegen.velocity.mvn.Mvn;
import org.ibs.cdx.gode.codegen.velocity.yconfig.VelocityGenConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;
@Component
public class CodeManager {

    private static final String POM_XML = "pom.xml";
    private final String CONFIG_PATH;
    private final String TEMPLATE_PATH;
    private final String OUTPUT_PATH;
    private final String PROCESS_PATH;

    private final String PROJECT_PREFIX;
    private final String EXE;

    private final String ADMIN;

    private static final String ARG="-Dspring-boot.run.arguments=--server.port=%s,--%s=%s,--%s=%s,--%s=%s";
    private static final Logger LOG = LoggerFactory.getLogger(CodeManager.class);

    @Autowired
    public CodeManager(Environment environment){
        this.CONFIG_PATH = environment.getProperty("gode.codegen.config.path");
        this.TEMPLATE_PATH = environment.getProperty("gode.codegen.templates.path");
        this.OUTPUT_PATH = environment.getProperty("gode.output");
        this.PROCESS_PATH = environment.getProperty("gode.codegen.process.path");
        this.PROJECT_PREFIX=environment.getProperty("gode.codegen.project.structure");
        this.EXE=environment.getProperty("gode.codegen.project.exe");
        this.ADMIN="http://localhost".concat(":").concat(environment.getProperty("local.server.port","8080")).concat("/gode");
    }

    public boolean build(CodeManagerConfig<?> configuration) throws IOException {
        if(configuration.getOutput().exists()){
            configuration.getOutput().delete();
        }
        if(configuration.getOutput().createNewFile()){
            Writer writer = new FileWriter(configuration.getOutput());
            Velocity.evaluate(configuration.getContext(), writer, "Generation", configuration.getTemplate());
            writer.flush();
            writer.close();
            LOG.info("Generated {}", configuration.getOutput());
        };
        return true;
    }

    public boolean build(AppVelocityModel appVelocityModel) throws IOException {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.init();
        appVelocityModel.getEntities().forEach(entityVelocityModel->{
            try {
                processConfiguration(appVelocityModel, entityVelocityModel);
                processVm(appVelocityModel, entityVelocityModel);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        //System.setProperty("maven.home","/usr/local/Cellar/maven/3.6.2/libexec");
        String pomFile = dir().add(OUTPUT_PATH).add(appVelocityModel.getVersion()).add(appVelocityModel.getName().toLowerCase()).add(POM_XML).toString();
        LOG.info("Building {}",pomFile);
        return Mvn.run(pomFile, "install");
    }

    public boolean deploy(DeploymentOpts deploymentOpts) throws IOException {
        //System.setProperty("maven.home","/usr/local/Cellar/maven/3.6.2/libexec");
        String pomFile = dir().add(OUTPUT_PATH).add(deploymentOpts.getVersion()).add(deploymentOpts.getAppName().toLowerCase()).add(EXE).add(POM_XML).toString();
        String appName= deploymentOpts.getAppName().toUpperCase();
        LOG.info("Deploying {}",pomFile);
        String args = String.format(ARG, deploymentOpts.getPort(),appName.concat("_DB"),"jdbc:mysql://localhost:3306/".concat(deploymentOpts.getStore().getName()),
                appName.concat("_USER"),deploymentOpts.getStore().getUsername(),
                appName.concat("_PASSWD"),deploymentOpts.getStore().getPassword());
        CompletableFuture.supplyAsync(()-> {
            LOG.info("Deployment started {} with args:{}",pomFile, args);
            return Mvn.runWithOpts(pomFile, args ,"spring-boot:run");
        })
        .whenComplete((e,v)->{
            if(v !=null){
                v.printStackTrace();
            }
        });
        return true;
    }

    public StringJoiner dir(){
        return new StringJoiner(File.separator);
    }
    private void processConfiguration(AppVelocityModel appVelocityModel, EntityVelocityModel model) throws IOException {
        ObjectMapper ymlMapper = new ObjectMapper(new YAMLFactory());
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources(CONFIG_PATH.concat(File.separator).concat("**"));
        for (Resource yml: resources) {
            if(yml.getFilename().endsWith(".vm")){
                System.out.println(yml);
                try {
                    Reader templateReader = new BufferedReader(new InputStreamReader(yml.getInputStream()));
                    VelocityGenConfig config = ymlMapper.readValue(yml.getURL(), VelocityGenConfig.class);
                    CodeManagerConfig configuration = new CodeManagerConfig(appVelocityModel, model);
                    configuration.add("parent",OUTPUT_PATH.concat(File.separator).concat(appVelocityModel.getVersion()).concat(File.separator));
                    configuration.add("project",PROJECT_PREFIX);
                    configuration.add("admin",ADMIN);
                    configuration.setTemplate(templateReader);
                    configuration.setOutput(PROCESS_PATH,config.getType().concat(".yml"));
                    this.build(configuration);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void processVm(AppVelocityModel appVelocityModel, EntityVelocityModel model) throws IOException {
        ObjectMapper ymlMapper = new ObjectMapper(new YAMLFactory());
        Files.walk(Paths.get(PROCESS_PATH)).peek(System.out::println).filter(k->k.getFileName().toString().endsWith(".yml")).forEach(yml->{
            try {
                VelocityGenConfig config = ymlMapper.readValue(yml.toFile(), VelocityGenConfig.class);
                config.getConfiguration().stream().forEach(c->{
                    CodeManagerConfig configuration = new CodeManagerConfig(appVelocityModel, model);
                    configuration.add("parent",c.getPath());
                    configuration.add("project",PROJECT_PREFIX);
                    configuration.add("admin",ADMIN);
                    try {
                        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
                        Resource template = resolver.getResource(TEMPLATE_PATH.concat(File.separator).concat(c.getTemplate()));
                        Reader templateReader = new BufferedReader(new InputStreamReader(template.getInputStream()));
                        configuration.setTemplate(templateReader);
                        configuration.setOutput(c.getPath(),c.getName());
                        this.build(configuration);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
