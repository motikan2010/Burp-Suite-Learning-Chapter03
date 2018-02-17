package com.motikan2010;


import com.motikan2010.entity.RequestResponseEntity;

public class RequestTableManager {

    private RequestTableModel model;

    public RequestTableManager(RequestTableModel requestTableModel) {
        this.model = requestTableModel;
    }

    public synchronized RequestResponseEntity getRequestResponse(int rowIndex) {
        return this.model.getRequestResponse(rowIndex);
    }

    public void addRequestResponse(RequestResponseEntity requestResponse) {
        this.model.addRequestResponse(requestResponse);
    }
}
