package com.motikan2010.entity;

import burp.*;

import java.net.URL;

public class RequestResponseEntity {

    public RequestResponseEntity(IHttpRequestResponse requestResponse) {
        IRequestInfo iRequestInfo = BurpExtender.getHelpers().analyzeRequest(requestResponse);
        IResponseInfo iResponseInfo = BurpExtender.getHelpers().analyzeResponse(requestResponse.getResponse());

        this.requestResponse = requestResponse;
        this.url = iRequestInfo.getUrl();
        this.method = iRequestInfo.getMethod();
        this.responseStatus = iResponseInfo.getStatusCode();
        this.isEnabled = true;
    }

    private IHttpRequestResponse requestResponse;

    private URL url;

    private String method;

    private int responseStatus;

    private boolean isEnabled;

    public IHttpRequestResponse getRequestResponse() {
        return requestResponse;
    }

    public String getHost() {
        return this.url.getHost();
    }

    public String getMethod() {
        return this.method;
    }

    public String getPath() {
        return this.url.getPath();
    }

    public int getResponseStatus() {
        return this.responseStatus;
    }

    public boolean isEnabled() {
        return this.isEnabled;
    }

    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
    }
}
