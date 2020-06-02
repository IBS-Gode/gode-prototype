package org.ibs.cdx.gode.entity;

import java.io.Serializable;
import java.time.OffsetDateTime;

import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.ibs.cdx.gode.dl.LearningRequired;
import org.ibs.cdx.gode.util.EntityUtil;

@MappedSuperclass
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BasicEntity<IdField extends Serializable> implements Serializable{

	private boolean enabled = true;
	private OffsetDateTime createdDate;
	private OffsetDateTime updatedDate;

	public OffsetDateTime getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(OffsetDateTime createdDate) {
		this.createdDate = createdDate;
	}
	public OffsetDateTime getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(OffsetDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@JsonIgnore
	public abstract IdField getEntityId();

	@JsonIgnore
	public abstract void setEntityId(IdField entityId);

	@JsonIgnore
	public abstract String identifierField();

	@JsonIgnore
	public String getType() {
		return this != null ? this.getClass().getSimpleName() : null;
	}

	@Override
	public int hashCode() {
		return EntityUtil.hashCode(this);
	}

	@Override
	public String toString() {
		return EntityUtil.toString(this);
	}

	@JsonIgnore
	public abstract boolean isPersisted();

}
