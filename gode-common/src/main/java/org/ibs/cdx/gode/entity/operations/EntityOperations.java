package org.ibs.cdx.gode.entity.operations;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.ibs.cdx.gode.entity.BasicEntity;
import org.ibs.cdx.gode.pagination.PageContext;
import org.ibs.cdx.gode.pagination.PagedData;


public interface EntityOperations<Entity extends BasicEntity<Id>,Id extends Serializable> {

	Entity save(Entity entity);
	boolean delete(Id id);
	boolean disable(Id id);
	
	Optional<Entity> find(Id id);
	PagedData<Entity> findAll(PageContext request);
	PagedData<Entity> findDisabled(PageContext request);
}
