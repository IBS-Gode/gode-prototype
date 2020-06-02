package org.ibs.cdx.gode.exception;

public class ClassicRuntimeException extends RuntimeException{
	
	public ClassicRuntimeException (String errorMessage) {
		super(errorMessage);
	}

	public ClassicRuntimeException (String errorMessage, Throwable e) {
		super(errorMessage, e);
	}
}
