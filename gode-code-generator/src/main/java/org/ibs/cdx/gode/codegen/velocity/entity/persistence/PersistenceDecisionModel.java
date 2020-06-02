package org.ibs.cdx.gode.codegen.velocity.entity.persistence;

public interface PersistenceDecisionModel {

    class Mode{
        public enum DB{
            RDBMS,
            NOSQL;
        }

        public enum Cache{
            NONE,
            NEAR,
            DISTRIBUTED;
        }
    }

    Mode.Cache cacheModel();
    Mode.DB datastore();
}
