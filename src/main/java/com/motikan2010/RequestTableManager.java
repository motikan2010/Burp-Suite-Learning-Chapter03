package com.motikan2010;

import burp.IHttpRequestResponse;
import com.motikan2010.entity.RequestResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

public class RequestTableManager {

    private RequestTableModel model;

    public RequestTableManager(RequestTableModel requestTableModel) {
        this.model = requestTableModel;
    }

    public List<IHttpRequestResponse> getRequestResponseList() {
        List<RequestResponseEntity> requestResponseEntityList = this.model.getRequestResponseEntityList();
        return requestResponseEntityList.stream()
                .filter(RequestResponseEntity::isEnabled) // 列項目「Enable」にチェックが付いている行を対象
                .map(RequestResponseEntity::getRequestResponse).collect(Collectors.toList());
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
