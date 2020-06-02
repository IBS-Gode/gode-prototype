package org.ibs.cdx.gode.app.function;

import io.swagger.annotations.ApiOperation;
import org.ibs.cdx.gode.util.MessageUtils;
import org.ibs.cdx.gode.util.ObjectTranslator;
import org.ibs.cdx.gode.web.Request;
import org.ibs.cdx.gode.web.Response;
import org.ibs.cdx.gode.web.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public abstract class FunctionalEndPoint {

    @Autowired
    private ObjectTranslator translator;

    public FunctionalEndPoint(){
        this.managerMap = new HashMap<>();
        this.classMap = new HashMap<>();
        addHandlers();
    }

    private Map<String, FunctionLogic<?,?>> managerMap;
    private Map<String, Class<?>> classMap;

    public final <T> void _add(String handle, Class<T> classType, FunctionLogic<T,?> functionLogic){
        this.managerMap.put(handle, functionLogic);
        this.classMap.put(handle, classType);
    };

    public abstract void addHandlers();

    private <T> T parseData(Object data, String requestHandle) {
        return (T) translator.mapToObject(data, classMap.get(requestHandle));
    }


    @PostMapping(path = "/universal")
    @ApiOperation("Dynamic function endpoint")
    public final @ResponseBody Response<ModelMap> process(@RequestBody Request<ModelMap> request) throws Exception {
        RequestContext context = request.getContext();
        if (context == null || context.getRequestHandle() == null) return noContextToTrack(context);
        String requestHandle = context.getRequestHandle();
        FunctionLogic logic = managerMap.get(requestHandle);
        if (logic == null) return noContextToTrack(context);
        Object requestData = request.getData().get(requestHandle);
        if(requestData == null) return noDataToTrack(context);
        Object processedResult = logic.process(parseData(requestData, requestHandle));
        ModelMap response = new ModelMap();
        if(processedResult != null){
            response.put(processedResult.getClass().getSimpleName(), processedResult);
        }
        return MessageUtils.successResponse(response, requestHandle);
    }

    private Response<ModelMap> noContextToTrack(RequestContext context) {
        return MessageUtils.failureResponse("No recorded behaviour for the context", context);
    }

    private Response<ModelMap> noDataToTrack(RequestContext context) {
        return MessageUtils.failureResponse("No data provided for the context", context);
    }
}
