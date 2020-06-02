package org.ibs.cdx.gode.app;

import org.ibs.cdx.gode.exception.ClassicRuntimeException;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.atomic.AtomicReference;

public class GodeRegister {

    private static GodeRegister godeRegister;
    private AtomicReference<ConfigurableApplicationContext> applicationContext;
    private GodeRegister(ConfigurableApplicationContext applicationContext){
        this.applicationContext = new AtomicReference<>(applicationContext);
    };

    protected static void ofApp(ConfigurableApplicationContext applicationContext){
         godeRegister = new GodeRegister(applicationContext);
    }

    public static <T> T getEntryOf(Class<T> className) {
        try {
            return godeRegister.applicationContext.get().getBean(className);
        } catch (Exception e) {
            throw new ClassicRuntimeException("Class not found", e);
        }
    }


    @SuppressWarnings("unchecked")
    public <T> T getEntryOf(String beanName) {
        try {
            return (T) godeRegister.applicationContext.get().getBean(beanName);
        } catch (Exception e) {
            throw new ClassicRuntimeException("Class not found", e);
        }

    }

}
