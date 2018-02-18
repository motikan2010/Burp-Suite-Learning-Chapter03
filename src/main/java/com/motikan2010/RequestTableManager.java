package com.motikan2010;

import com.motikan2010.entity.RequestResponseEntity;

public class RequestTableManager {

    private RequestTableModel model;

    public RequestTableManager(RequestTableModel requestTableModel) {
        this.model = requestTableModel;
    }

    /**
     * テーブルの添字からデータ（エンティティ）の取得
     *
     * @param rowIndex
     * @return RequestResponseEntity
     */
    public synchronized RequestResponseEntity getRequestResponse(int rowIndex) {
        return this.model.getRequestResponse(rowIndex);
    }

    /**
     * テーブル内にデータを追加
     *
     * @param requestResponse
     */
    public void addRequestResponse(RequestResponseEntity requestResponse) {
        this.model.addRequestResponse(requestResponse);
    }
}
