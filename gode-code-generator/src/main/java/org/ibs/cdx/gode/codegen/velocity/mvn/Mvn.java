package org.ibs.cdx.gode.codegen.velocity.mvn;

import org.apache.maven.shared.invoker.*;

import java.io.File;
import java.util.Arrays;

public class Mvn {

    public static boolean run(String pomFile, String... args) {
        return runWithOpts(pomFile, null,args);
    }

    static{
        System.setProperty("maven.home",findMvn());
    }

    public static boolean runWithOpts(String pomFile, String mvnOpts, String... args) {
        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile(new File(pomFile));
        request.setGoals(Arrays.asList(args));
        if(mvnOpts != null)request.setMavenOpts(mvnOpts);
        Invoker invoker = new DefaultInvoker();
        try {
            return invoker.execute(request).getExitCode() == 0;
        } catch (MavenInvocationException e) {
            throw new RuntimeException("Failed to execute maven",e);
        }
    }

    private static String findMvn() {
        if (System.getenv("M2_HOME") != null) {
            return System.getenv("M2_HOME");
        }

        for (String dirname : System.getenv("PATH").split(File.pathSeparator)) {
            File file = new File(dirname, "mvn");
            if (file.isFile() && file.canExecute()) {
                return  new File(dirname).getParentFile().toString();
            }
        }
        throw new RuntimeException("No mvn found, please install mvn and/or setup M2_HOME");
    }
}
