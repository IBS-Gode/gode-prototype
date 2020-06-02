package org.ibs.cdx.gode.codegen.velocity.yconfig;

import com.fasterxml.jackson.annotation.*;
import org.ibs.cdx.gode.codegen.velocity.VelocityModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "type",
        "configuration"
})
public class VelocityGenConfig extends VelocityModel<VelocityGenConfig> {

    @JsonProperty("type")
    private String type;
    @JsonProperty("configuration")
    private List<Configuration> configuration = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("configuration")
    public List<Configuration> getConfiguration() {
        return configuration;
    }

    @JsonProperty("configuration")
    public void setConfiguration(List<Configuration> configuration) {
        this.configuration = configuration;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String getModelType() {
        return "config";
    }

    @Override
    public Class<VelocityGenConfig> getModelClass() {
        return VelocityGenConfig.class;
    }
}