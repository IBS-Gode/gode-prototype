package org.ibs.cdx.gode.codegen.velocity;

import org.apache.commons.lang.ArrayUtils;
import org.apache.velocity.VelocityContext;
import org.ibs.cdx.gode.codegen.velocity.util.StringUtils;

import java.io.File;
import java.io.Reader;
import java.util.Arrays;

public class CodeManagerConfig<T extends VelocityModel<?>>{

    protected VelocityContext getContext() {
        return context;
    }

    private final VelocityContext context;
    private Reader template;
    private File output;

    public CodeManagerConfig add(String name, Object data){
        this.context.put(name, data);
        return this;
    }

    public Reader getTemplate() {
        return template;
    }

    public void setTemplate(Reader template) {
        this.template = template;
    }

    public File getOutput() {
        return output;
    }

    public void setOutput(File output) {
        this.output = output;
    }

    public CodeManagerConfig(T... models){
        VelocityContext context = new VelocityContext();
        context.put("StringUtils", StringUtils.class);
        context.put("String",String.class);
        if(!ArrayUtils.isEmpty(models)) Arrays.stream(models).forEach(model->context.put(model.getModelType(),model));
        this.context = context;
    }

    public void setOutput(String outputFilePath, String outputFile) {
        File parentPath = new File(outputFilePath);
        if(!parentPath.exists()){
            parentPath.mkdirs();
        }
        this.output = new File(outputFilePath.concat(File.separator).concat(outputFile));
    }

}
