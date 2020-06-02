package org.ibs.cdx.gode.app.function;

public class FunctionLogicHandler {

    private final String handle;
    private final FunctionLogic<?,?> functionLogic;

    public String getHandle() {
        return handle;
    }

    public FunctionLogic<?, ?> getFunctionLogic() {
        return functionLogic;
    }

    private FunctionLogicHandler(String handle, FunctionLogic<?,?> functionLogic){
        this.handle = handle;
        this.functionLogic = functionLogic;
    }

    public static FunctionLogicHandler of(String handle, FunctionLogic<?,?> functionLogic ){
        return  new FunctionLogicHandler(handle, functionLogic);
    }
}
