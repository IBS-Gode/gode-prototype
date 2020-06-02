package org.ibs.cdx.gode.util;

import org.ibs.cdx.gode.executor.status.Status;
import org.ibs.cdx.gode.pagination.PagedData;
import org.ibs.cdx.gode.web.Request;
import org.ibs.cdx.gode.web.Response;
import org.ibs.cdx.gode.web.context.RequestContext;
import org.ibs.cdx.gode.web.context.ResponseContext;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MessageUtils {

	public static <T,D> Response<T> response(T data) {
		return new Response<T>(data);
	}

	public static <T,D,O>  Response<T> successResponse(T data, Request<O> request) {
		Response<T> response = new Response<T>(data);
		ResponseContext context=new ResponseContext(request.getContext());
		context.setStatus(Status.SUCCESS);
		response.setContext(context);
		return response;
	}


	public static <T>  Response<T> successResponse(T data,String requestType) {
		Response<T> response = new Response<>(data);
		RequestContext context = new RequestContext();
		context.setRequestHandle("IS="+requestType);
		ResponseContext respContext=new ResponseContext(context);
		respContext.setStatus(Status.SUCCESS);
		response.setContext(respContext);
		return response;
	}

	public static <T,D>  Response<T> failureResponse(String error,RequestContext requestContext) {
		Response<T> response = new Response<T>(null);
		ResponseContext context=new ResponseContext(requestContext);
		context.setStatus(Status.FAILURE).setErrorDetail(error);
		response.setContext(context);
		return response;
	}

	public static <T,D,O>  Response<T> failureResponse(Request<O> request,String error, Throwable e,String errorDetails) {
		Response<T> response = new Response<T>(null);
		ResponseContext context=new ResponseContext(request.getContext());
		context.setStatus(Status.FAILURE).setErrorDetail(error);
		response.setContext(context);
		return response;
	}

	public static <T,D,O>  Response<T> failureResponse(Request<O> request,String error) {
		Response<T> response = new Response<T>(null);
		ResponseContext context=new ResponseContext(request.getContext());
		context.setStatus(Status.FAILURE).setErrorDetail(error);
		response.setContext(context);
		return response;
	}

	public static <T,D>  Response<List<T>> successResponse(PagedData<T> data, String requestType) {
		Response<List<T>> response = new Response<>(data != null ? data.getData() : null);
		return getResponse(data, requestType, response);
	}

	@NotNull
	private static <T, V> Response<V> getResponse(PagedData<T> data, String requestType, Response<V> response) {
		RequestContext context = new RequestContext();
		context.setRequestHandle("IS="+requestType);
		ResponseContext respContext=new ResponseContext(context);
		respContext.setPageDetail(data != null ? data.getContext() : null);
		respContext.setStatus(Status.SUCCESS);
		response.setContext(respContext);
		return response;
	}

}
