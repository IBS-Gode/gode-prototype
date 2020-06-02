package org.ibs.cdx.gode.pagination;


import org.ibs.cdx.gode.util.EntityUtil;

public class Sortable {
	
	public enum Type {
	    ASC,
	    DESC;
	}

	private Type sortType;
	private String field;

	public Sortable(Type sortType, String field) {
	        this.sortType = sortType;
	        this.field = field;
	    }

	public static Sortable by(String field) {
		return new Sortable(Type.ASC, field);
	}

	public static Sortable by(Type type, String field) {
		return new Sortable(type == null ? Type.ASC : type, field);
	}

	public Type getSortType() {
		return sortType;
	}

	public void setSortType(Type sortType) {
		this.sortType = sortType;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	@Override
	public String toString() {
		return EntityUtil.toString(this);
	}
}
