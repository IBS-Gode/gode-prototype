package org.ibs.cdx.gode.codegen.velocity.endpoint;

public class DeploymentOpts {

    private Integer port;
    private String appName;
    private String version;
    private Database store;

    public Database getStore() {
        return store;
    }

    public void setStore(Database store) {
        this.store = store;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
