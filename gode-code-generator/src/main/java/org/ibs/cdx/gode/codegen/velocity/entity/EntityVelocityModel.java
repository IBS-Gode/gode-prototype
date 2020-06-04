package org.ibs.cdx.gode.codegen.velocity.entity;

import org.ibs.cdx.gode.codegen.velocity.VelocityModel;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EntityVelocityModel extends VelocityModel<EntityVelocityModel> {

    private String name;
    private Set<EntityField> attributes;
    private List<String> tags;

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<EntityField> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<EntityField> attributes) {
        this.attributes = attributes;
    }

    public EntityVelocityModel addAttribute(EntityField attribute) {
        if(this.attributes == null) this.attributes = new HashSet<>();
        this.attributes.add(attribute);
        return this;
    }

    public EntityField getIdField() {
        return idField;
    }

    public void setIdField(EntityField idField) {
        this.idField = idField;
    }

    private EntityField idField;

    public EntityVelocityModel() {
        super();
        this.attributes = new HashSet<>();
    }

    private String database;

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }


    @Override
    public String getModelType() {
        return "entity";
    }

    @Override
    public Class<EntityVelocityModel> getModelClass() {
        return EntityVelocityModel.class;
    }
}
