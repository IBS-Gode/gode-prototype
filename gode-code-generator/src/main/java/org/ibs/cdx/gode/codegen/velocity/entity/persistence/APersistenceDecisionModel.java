package org.ibs.cdx.gode.codegen.velocity.entity.persistence;

public abstract class APersistenceDecisionModel<T> implements PersistenceDecisionModel{

    protected PersistenceQualification qualification;
    public APersistenceDecisionModel(PersistenceQualification qualification){
        this.qualification = qualification;
    }

}
