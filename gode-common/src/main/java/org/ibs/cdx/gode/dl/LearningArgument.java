package org.ibs.cdx.gode.dl;

import java.util.Map;

public class LearningArgument {

    public enum Default{
        TRAINING_DATA,
        TRAINING_DATA_BATCH,
        TEST_DATA,
        NORMALISER,
        MODEL;
    }

    private Map<String ,Object> arguments;
    private Map<String ,Class<?>> argumentTypes;

    public <T> void add(String handle, T data){
        this.arguments.put(handle, data);
        this.argumentTypes.put(handle,data.getClass());
    }
    public <T> void add(Default handle, T data){
        String key = handle.toString();
        this.add(key, data);
    }

    public <T> T get(String handle){
        Object data = arguments.get(handle);
        return data != null ? (T) argumentTypes.get(handle).cast(data) : null;
    }
    public <T> T get(Default handle){
        String key = handle.toString();
        return get(key);
    }

}
