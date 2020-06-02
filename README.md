# God(e)

An intelligent codegenerator for generating applications for enterprise requirements.

## How to build
- Git clone the repo
- Run mvn clean install
- cd <baseDir>/gode-code-generator
- Run mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=${port}
- Go to *http://localhost:${port}/gode* for admin app
- Go to *http://localhost:${port}/swagger-ui.html* for apis
