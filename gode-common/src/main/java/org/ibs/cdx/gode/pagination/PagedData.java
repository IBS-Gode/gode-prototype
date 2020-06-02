package org.ibs.cdx.gode.pagination;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.stream.Stream;

public class PagedData<T> {

	private List<T> data;
	private ResponsePageContext context;

	/**
	 * @return the data
	 */
	public List<T> getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(List<T> data) {
		this.data = data;
	}
	/**
	 * @return the context
	 */
	public ResponsePageContext getContext() {
		return context;
	}
	/**
	 * @param context the context to set
	 */
	public void setContext(ResponsePageContext context) {
		this.context = context;
	}


	@JsonIgnore
	public Stream<T>  stream(){
		return CollectionUtils.isEmpty(data) ?  Stream.empty() : data.stream();
	}
}
