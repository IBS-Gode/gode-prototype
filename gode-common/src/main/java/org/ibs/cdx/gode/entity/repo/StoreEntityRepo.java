package org.ibs.cdx.gode.entity.repo;

import com.querydsl.core.types.Predicate;
import org.ibs.cdx.gode.entity.StoreEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.io.Serializable;
import java.util.Optional;
import java.util.stream.Stream;

public interface StoreEntityRepo<Entity extends StoreEntity<Id>,Id extends Serializable> extends Repo<Entity, Id>, QuerydslPredicateExecutor<Entity> {
	Optional<Entity> findById(Id id);
	Stream<Entity> findByEnabled(boolean enabled);
	Page<Entity> findByEnabled(boolean enabled, Pageable pageable);
	void deleteById(Id id);
	Entity save(Entity entity);

	Page<Entity> findAll(Predicate predicate, Pageable pageable);
}
