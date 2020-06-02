package org.ibs.cdx.gode.codegen.velocity;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class GodeGenerator {

    public static void main(String[] args) {
        SpringApplication.run(GodeGenerator.class, args);
    }
}
