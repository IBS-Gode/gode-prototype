package org.ibs.cdx.gode.web;

import org.ibs.cdx.gode.web.context.ResponseContext;

public class Response<T> implements Message<T,ResponseContext> {

	private T data;
	private ResponseContext context;

	
	public Response() {
		
	}
	
	public Response(T data){
		this.data=data;
	}
	
	
	@Override
	public T getData() {
		return data;
	}

	@Override
	public ResponseContext getContext() {
		return context;
	}

	public Response<T> setContext(ResponseContext context) {
		this.context = context;
		return this;
	}
	
}
