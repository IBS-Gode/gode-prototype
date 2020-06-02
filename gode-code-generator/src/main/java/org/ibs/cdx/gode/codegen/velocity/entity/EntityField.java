package org.ibs.cdx.gode.codegen.velocity.entity;

import java.util.Set;

public class EntityField {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String name;
    private String datatype;
    private String description;

    public Set<EntityFieldProperty> getProperties() {
        return properties;
    }

    public void setProperties(Set<EntityFieldProperty> properties) {
        this.properties = properties;
    }

    private Set<EntityFieldProperty> properties;
}
