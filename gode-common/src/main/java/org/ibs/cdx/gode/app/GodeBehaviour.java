package org.ibs.cdx.gode.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value="classpath:gode.properties")
public class GodeBehaviour {

    private final Environment envt;

    @Autowired
    public GodeBehaviour(Environment envt){
        this.envt = envt;
    }

    public <T> T getProperty(String prop, Class<T> type){
        return envt.getProperty(prop, type);
    }

    public String getProperty(String prop){
        return envt.getProperty(prop);
    }

    public String getPropertyOrDefault(String prop,String defaultValue){
        return envt.getProperty(prop, defaultValue);
    }
}
