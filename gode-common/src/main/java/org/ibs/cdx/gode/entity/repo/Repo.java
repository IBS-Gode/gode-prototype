package org.ibs.cdx.gode.entity.repo;

import org.ibs.cdx.gode.entity.BasicEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;

public interface Repo<Entity extends BasicEntity<Id>,Id extends Serializable> {
    Page<Entity> findAll(Pageable pageable);
}
