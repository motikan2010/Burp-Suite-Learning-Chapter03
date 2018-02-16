package com.motikan2010;


import burp.IHttpRequestResponse;

public class RequestTableManager {

    private RequestTableModel model;

    public RequestTableManager(RequestTableModel requestTableModel) {
        this.model = requestTableModel;
    }

    public synchronized int getRowCount()  {
        return this.model.getCount();
    }

    public synchronized RequestTableModel getRequestTableModel() {
        return this.model;
    }

    public synchronized IHttpRequestResponse getRequestResponse(int rowIndex) {
        return this.model.getRequestResponse(rowIndex);
    }

    public synchronized void addRequestResponse(IHttpRequestResponse requestResponse) {
        this.model.addRequestResponse(requestResponse);
    }
}
