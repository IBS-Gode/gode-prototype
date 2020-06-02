package org.ibs.cdx.gode.entity.controller;

import com.querydsl.core.types.Predicate;
import org.ibs.cdx.gode.dl.LearningManager;
import org.ibs.cdx.gode.entity.BasicEntity;
import org.ibs.cdx.gode.entity.manager.EntityManager;
import org.ibs.cdx.gode.executor.Executor;
import org.ibs.cdx.gode.pagination.PageContext;
import org.ibs.cdx.gode.util.EntityUtil;
import org.ibs.cdx.gode.util.MessageUtils;
import org.ibs.cdx.gode.web.Request;
import org.ibs.cdx.gode.web.Response;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Controller
public abstract class EntityEndpoint<Entity extends BasicEntity<Id>,Manager extends EntityManager<Entity,?,Id,?>,LearnManager extends LearningManager<?,String,?>, Id extends Serializable> {

    private Manager manager;
    private LearnManager learnManager;
    private static final EntityUtil entity = new EntityUtil();

    public EntityEndpoint(Manager manager, LearnManager learnManager){
        this.manager = manager;
        this.learnManager = learnManager;
    }

    private static final String TEMP = "Failed to execute %s operation";

    private String getMsg(String operation){
        return String.format(TEMP, operation);
    }

    public Response<Entity> save(Request<Entity> request){
        String operation = "save";
        return new Executor<Entity,Manager,Entity>(operation)
                .withRequest(request)
                .ifFails(getMsg(operation))
                .withLogic((entity,manager) ->manager.save(entity))
                .run(manager);
    }

    public Response<Boolean> disable(Request<Id> request){
        String operation = "disable";
        return new Executor<Id,Manager,Boolean>(operation)
                .withRequest(request)
                .ifFails(getMsg(operation))
                .withLogic((id,manager) ->manager.disable(id))
                .run(manager);
    }

    public Response<Boolean> delete(Request<Id> request){
        String operation = "delete";
        return new Executor<Id,Manager,Boolean>(operation)
                .withRequest(request)
                .ifFails(getMsg(operation))
                .withLogic((id,manager) ->manager.delete(id))
                .run(manager);
    }

    public Response<Entity> find(Id request){
        String operation = "find";
        return new Executor<Id,Manager,Entity>(operation)
                .withRequest(new Request<>(request))
                .ifFails(getMsg(operation))
                .withLogic((id,manager) ->manager.find(id).orElse(null))
                .run(manager);
    }

    public Response<List<Entity>> findAll(@QuerydslPredicate Predicate predicate, @ModelAttribute PageContext pageRequest){
        String operation = "findAll";
        return MessageUtils.successResponse(manager.findAll(predicate, pageRequest), operation);
    }

    public Response<Boolean> validate(Request<Entity> request){
        String operation = "find";
        return new Executor<Entity,Manager,Boolean>(operation)
                .withRequest(request)
                .ifFails(getMsg(operation))
                .withLogic((id,manager) ->{
                    manager.validateUserEntity(id);
                    return true;
                })
                .run(manager);
    }

    public Response<Boolean> train(String handle, MultipartFile data){
        learnManager.trainingData(handle, data);
        return MessageUtils.successResponse(true,"Training data load");
    }

    public Response<ModelMap> classify(String handle, MultipartFile testData){
        learnManager.testData(handle, testData);
        Map results= learnManager.classify();
        ModelMap result = new ModelMap();
        result.put("results",results );
        return MessageUtils.successResponse(result,handle);
    }
}
