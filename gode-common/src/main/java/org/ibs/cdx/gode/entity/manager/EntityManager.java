package org.ibs.cdx.gode.entity.manager;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.querydsl.core.types.Predicate;
import org.apache.catalina.User;
import org.apache.commons.collections4.CollectionUtils;
import org.ibs.cdx.gode.entity.BasicEntity;
import org.ibs.cdx.gode.entity.StoreEntity;
import org.ibs.cdx.gode.entity.operations.EntityOperations;
import org.ibs.cdx.gode.entity.repo.StoreEntityRepo;
import org.ibs.cdx.gode.exception.ClassicRuntimeException;
import org.ibs.cdx.gode.pagination.PageContext;
import org.ibs.cdx.gode.pagination.PagedData;
import org.ibs.cdx.gode.util.PageUtils;

public abstract class EntityManager<UserEntity extends BasicEntity<Id>, StoredEntity extends StoreEntity<Id>, Id extends Serializable, Repo extends StoreEntityRepo<StoredEntity, Id>>
		implements EntityOperations<UserEntity, Id> {

	protected Repo repo;

	public EntityManager(Repo repo) {
		this.repo = repo;
	}

	protected abstract UserEntity beforeSave(UserEntity entity);

	protected abstract UserEntity afterSave(UserEntity entity);

	protected abstract UserEntity beforeDisable(UserEntity entity);

	protected abstract UserEntity afterDisable(UserEntity entity);

	protected abstract StoredEntity transform(UserEntity entity);

	protected abstract UserEntity transform(StoredEntity entity);

	public abstract void validateStoreEntity(StoredEntity entity) throws ClassicRuntimeException;
	public abstract void validateUserEntity(UserEntity entity) throws ClassicRuntimeException;
	
	public void validateStoreEntity(List<StoredEntity> entities) throws ClassicRuntimeException{
		if(CollectionUtils.isEmpty(entities)) return;
		entities.forEach(this::validateStoreEntity);
	}
	
	public void validateUserEntity(List<UserEntity> entities) throws ClassicRuntimeException{
		if(CollectionUtils.isEmpty(entities)) return;
		entities.forEach(this::validateUserEntity);
	}
	
	private StoredEntity doSave(StoredEntity entity) {
		validateStoreEntity(entity);
		return this.repo.save(entity);
	}

//	private List<StoredEntity> doSave(List<StoredEntity> entities) {
//		validateStoreEntity(entities);
//		List<StoredEntity> results = new ArrayList<>();
//		this.repo.saveAll(entities).forEach(results::add);
//		return results;
//	}

	@Override
	public UserEntity save(UserEntity entity) {
		validateUserEntity(entity);
		setDefaultFields(entity);
		return afterSave(transform(doSave(transform(beforeSave(entity)))));
	}

//	@Override
//	public List<UserEntity> saveAll(List<UserEntity> entities) {
//		if (CollectionUtils.isEmpty(entities))
//			return Collections.emptyList();
//		setDefaultFields(entities);
//		validateUserEntity(entities);
//		List<StoredEntity> savedEntities = this
//				.doSave(entities.stream().map(this::beforeSave).map(this::transform).collect(Collectors.toList()));
//		if (CollectionUtils.isEmpty(savedEntities))
//			return Collections.emptyList();
//		return savedEntities.stream().map(this::transform).map(this::afterSave).collect(Collectors.toList());
//	}

	@Override
	public boolean delete(Id id) {
		this.repo.deleteById(id);
		return true;
	}


	@Override
	public Optional<UserEntity> find(Id id) {
		return this.repo.findById(id).map(this::transform);
	}

	@Override
	public PagedData<UserEntity> findAll(PageContext request) {
		return PageUtils.getData(ctx -> repo.findAll(ctx), request, this::transform);
	}

	public PagedData<UserEntity> findAll(Predicate filter, PageContext request) {
		return PageUtils.getData(ctx -> repo.findAll(filter,ctx), request, this::transform);
	}

	public PagedData<StoredEntity> findAllInternal(Predicate filter, PageContext request) {
		return PageUtils.getData(ctx -> repo.findAll(filter,ctx), request);
	}

	@Override
	public boolean disable(Id id) {
		return this.repo.findById(id).map(entity->{
			entity.setEnabled(false);
			return afterDisable(transform(this.doSave(transform(beforeDisable(transform(entity)))))) != null;
		}).orElse(false);
	}

	@Override
	public PagedData<UserEntity> findDisabled(PageContext request) {
		return PageUtils.getData(ctx -> repo.findByEnabled(false, ctx), request, this::transform);
	}

	protected void setDefaultFields(UserEntity item) {
		OffsetDateTime now = OffsetDateTime.now();
		setDefaultFields(item, now);
	}

	public boolean isNewEntity(UserEntity item) {
		return item.getEntityId() == null;
	}

	private void setDefaultFields(UserEntity item, OffsetDateTime time) {
		if (item.getCreatedDate() == null)
			item.setCreatedDate(time);
		item.setUpdatedDate(time);
	}

	protected void setDefaultFields(List<UserEntity> items) {
		OffsetDateTime now = OffsetDateTime.now();
		items.stream().forEach(item -> setDefaultFields(item, now));
	}

}
