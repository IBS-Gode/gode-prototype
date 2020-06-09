package org.ibs.cdx.gode.store;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class JPAStoreEnabler  implements  Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        //return Boolean.valueOf(context.getEnvironment().getProperty("gode.datastore.jpa.enabled"));
        return false;
    }

}