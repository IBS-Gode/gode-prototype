package org.ibs.cdx.gode.codegen.velocity.entity.persistence;

public class PersistenceMode extends APersistenceDecisionModel<PersistenceMode> {


    public PersistenceMode(PersistenceQualification qualification) {
        super(qualification);
    }

    @Override
    public Mode.Cache cacheModel() {
        return Mode.Cache.NONE;
    }

    @Override
    public Mode.DB datastore() {
        return Mode.DB.RDBMS;
    }
}
