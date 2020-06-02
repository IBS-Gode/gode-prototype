package org.ibs.cdx.gode.executor;

import org.ibs.cdx.gode.exception.ClassicRuntimeException;
import org.ibs.cdx.gode.util.MessageUtils;
import org.ibs.cdx.gode.web.Request;
import org.ibs.cdx.gode.web.Response;


import java.util.function.BiFunction;

public class Executor<RequestType, Manager, ResponseType> {

	private Request<RequestType> request;
	private BiFunction<RequestType,Manager,ResponseType> applicableFunction;
	private String genericError;
	private String context;

	public Executor(String context){
		this.context= context;
	}

	public Executor<RequestType, Manager, ResponseType> withRequest(Request<RequestType> request) {
		this.request = request;
		return this;
	}

	public Executor<RequestType, Manager, ResponseType> withLogic(BiFunction<RequestType,Manager,ResponseType> applicableFunction ) {
		this.applicableFunction = applicableFunction;
		return this;
	}


	public Executor<RequestType, Manager, ResponseType> ifFails(String genericError) {
		this.genericError = genericError;
		return this;
	}

	public Response<ResponseType> run(Manager manager) {
		try {
			if(manager==null)  throw new ClassicRuntimeException(genericError);
			return MessageUtils.successResponse(applicableFunction.apply(request.getData(), manager), context);
		} catch(ClassicRuntimeException e) {
			throw e;
		}catch (Throwable e) {
			throw new ClassicRuntimeException(genericError,e);
		}
	}
}
