package org.ibs.cdx.gode.executor.status;

public enum Status implements IStatus{

	
	STARTED("STARTED",0),
	RUNNING("RUNNING",1),
	SUCCESS("SUCCESS",2),
	UNKNOWN("UNKNOWN",4),
	FAILURE("FAILURE",3);
	
	private final String stringValue;
	private final int integerValue;
	
	Status(String toString,int toInt) {
		this.stringValue=toString;
		this.integerValue=toInt;
		
	}
	public String getStringValue() {
		return stringValue;
	}
	public int getIntegerValue() {
		return integerValue;
	}
	
	
	
}
