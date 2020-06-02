package org.ibs.cdx.gode.app.function;

@FunctionalInterface
public interface FunctionLogic<Request,Response> {

    Response process(Request object) throws Exception;
}
