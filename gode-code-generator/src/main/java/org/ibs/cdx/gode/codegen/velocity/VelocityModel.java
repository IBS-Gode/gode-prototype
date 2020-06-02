package org.ibs.cdx.gode.codegen.velocity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;

public abstract class VelocityModel<T extends VelocityModel> {

    public enum ModelSource{
        JSON;
    }

    private ObjectMapper mapper ;
    private String version;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public VelocityModel(){
        mapper = new ObjectMapper();
    }

    public T build(String data, ModelSource source) throws IOException {
        if(source == ModelSource.JSON) return mapper.readValue(data,getModelClass());
        throw new RuntimeException("Only Json config is supported now");
    }

    public T build(String data ) throws IOException {
       return build(data, ModelSource.JSON);
    }

    @JsonIgnore
    public abstract String getModelType();

    @JsonIgnore
    public abstract Class<T> getModelClass();
}
