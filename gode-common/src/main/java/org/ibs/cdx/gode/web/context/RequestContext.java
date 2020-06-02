package org.ibs.cdx.gode.web.context;

public class RequestContext implements MessageContext{

	public String requestHandle;

	public RequestContext() {
		
	}
	
	public RequestContext(String requestHandle) {
		this.requestHandle = requestHandle;
	}
	
	public String getRequestHandle() {
		return requestHandle;
	}

	public void setRequestHandle(String requestHandle) {
		this.requestHandle = requestHandle;
	}
}
