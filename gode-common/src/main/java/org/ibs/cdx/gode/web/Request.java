package org.ibs.cdx.gode.web;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.ibs.cdx.gode.web.context.RequestContext;

public class Request<T> implements Message<T, RequestContext> {

	public T data;
	public RequestContext context;
	
	public Request() {
		
	}
	
	public Request(T data){
		this.data=data;
	}
	
	
	@Override
	public T getData() {	
		return data;
	}

	@Override
	public RequestContext getContext() {
		return context;
	}


	public void setContext(RequestContext context) {
		this.context = context;
	}

	@JsonIgnore
	public static final Request<?> emptyObject(){
		return new Request<>();
	}

}
