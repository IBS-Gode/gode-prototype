package org.ibs.cdx.gode.entity.gql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.ibs.cdx.gode.entity.BasicEntity;
import org.ibs.cdx.gode.entity.StoreEntity;
import org.ibs.cdx.gode.entity.manager.EntityManager;
import org.ibs.cdx.gode.util.EntityUtil;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

public abstract class GraphQueryManager<Manager extends EntityManager<ViewEntity,StoredEntity,Id,?>,ViewEntity extends BasicEntity<Id>,StoredEntity extends StoreEntity<Id>,Id extends Serializable> implements GraphQLQueryResolver, GraphQLMutationResolver {

    private Manager manager;
    public GraphQueryManager(Manager manager){
        this.manager = manager;
    }

    public <T extends ViewEntity> ViewEntity  save(T entity){
        return this.manager.save(toEntity(entity));
    }

    public boolean delete(Id id){
        return this.manager.delete(id);
    }

    public boolean disable(Id id){
        return this.manager.disable(id);
    }

    public ViewEntity find(Id id){
        return this.manager.find(id).orElse(null);
    }

    public <T extends ViewEntity> boolean validate(T entity){
        this.manager.validateUserEntity(toEntity(entity));
        return true;
    }

    public <T extends ViewEntity> ViewEntity toEntity(T entity){
        ViewEntity viewEntity = EntityUtil.createEntityById(entity.getEntityId(), getEntityClass());
        BeanUtils.copyProperties(entity, viewEntity);
        return  viewEntity;
    }

    public abstract  Class<ViewEntity> getEntityClass();
}
