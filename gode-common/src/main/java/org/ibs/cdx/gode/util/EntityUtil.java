package org.ibs.cdx.gode.util;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.StandardToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ibs.cdx.gode.entity.BasicEntity;
import org.ibs.cdx.gode.exception.ClassicRuntimeException;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class EntityUtil {

	private static final StandardToStringStyle style;
	
	static {
		style = new StandardToStringStyle();
		style.setUseIdentityHashCode(false);
		style.setUseShortClassName(true);
	}
	
	public static <T> int hashCode(T obj) {
		return HashCodeBuilder.reflectionHashCode(obj, false);
	}
	
	public static <T> String toString(T obj) {
		return ToStringBuilder.reflectionToString(obj,style);
	}

	public static <Entity extends BasicEntity<Id>,Id extends Serializable> Entity createEntityById(Id id, Class<Entity> entityClass) {
		if(id == null) throw new ClassicRuntimeException("Failed to create entity instance");
		try {
			Entity newInstance = entityClass.newInstance();
			newInstance.setEntityId(id);
			return newInstance;
		} catch (InstantiationException | IllegalAccessException e) {
			throw new ClassicRuntimeException("Failed to create entity instance", e);
		}

	}

	public static <T> List<String> attributes(Class<T> some,String... avoidable){
		return Arrays.stream(some.getFields()).map(field->field.getName()).filter(field -> !field.equals(avoidable)).collect(Collectors.toList());
	}
}
