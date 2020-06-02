package org.ibs.cdx.gode.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.ibs.cdx.gode.web.context.MessageContext;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface Message<T,Context extends MessageContext> {

	T getData();
	Context getContext();

}
