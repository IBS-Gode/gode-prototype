package org.ibs.cdx.gode.web.context;

import org.ibs.cdx.gode.executor.status.Status;
import org.ibs.cdx.gode.pagination.ResponsePageContext;

public class ResponseContext implements MessageContext {


    private RequestContext requestContext;
    private Status status;
    private String errorDetail;
    private ResponsePageContext pageDetail;
    private String warning;

    public ResponseContext(){};
    public ResponseContext(RequestContext requestContext) {
        this.requestContext = requestContext;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public Status getStatus() {
        return status;
    }

    public ResponseContext setStatus(Status status) {
        this.status = status;
        return this;
    }

    public String getErrorDetail() {
        return errorDetail;
    }

    public ResponseContext setErrorDetail(String error) {
        this.errorDetail = error;
        return this;
    }

    public RequestContext getRequestContext() {
        return requestContext;
    }

    /**
     * @return the pageDetail
     */
    public ResponsePageContext getPageDetail() {
        return pageDetail;
    }

    /**
     * @param pageDetail the pageDetail to set
     */
    public void setPageDetail(ResponsePageContext pageDetail) {
        this.pageDetail = pageDetail;
    }
}
