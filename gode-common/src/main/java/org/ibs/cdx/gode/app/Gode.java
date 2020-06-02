package org.ibs.cdx.gode.app;

import org.springframework.boot.SpringApplication;

public class Gode {

    public static void start(Class<?> runner,String... args){
        GodeRegister.ofApp(SpringApplication.run(runner, args));
    }

}
