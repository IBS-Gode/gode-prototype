package org.ibs.cdx.gode.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import javax.persistence.MappedSuperclass;
import javax.persistence.Id;

@MappedSuperclass
public abstract class StoreEntity<IdField extends Serializable> extends BasicEntity<IdField>{

	@Override @JsonIgnore
	public boolean isPersisted() {
		return true;
	}
}
