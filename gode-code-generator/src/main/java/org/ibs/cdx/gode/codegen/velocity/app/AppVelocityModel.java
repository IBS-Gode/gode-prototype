package org.ibs.cdx.gode.codegen.velocity.app;

import org.ibs.cdx.gode.codegen.velocity.VelocityModel;
import org.ibs.cdx.gode.codegen.velocity.entity.EntityVelocityModel;

import java.util.ArrayList;
import java.util.List;

public class AppVelocityModel extends VelocityModel<AppVelocityModel> {

    public List<EntityVelocityModel> getEntities() {
        return entities;
    }

    public void setEntities(List<EntityVelocityModel> entities) {
        this.entities = entities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    private List<EntityVelocityModel> entities = new ArrayList<>();;


    @Override
    public String getModelType() {
        return "app";
    }

    @Override
    public Class<AppVelocityModel> getModelClass() {
        return AppVelocityModel.class;
    }
}
