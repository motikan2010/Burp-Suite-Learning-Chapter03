package com.motikan2010;

import com.motikan2010.entity.RequestResponseEntity;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class RequestTableModel extends AbstractTableModel {

    private final List<RequestResponseEntity> requestResponseEntityList = new ArrayList<>();

    private static final String[] COLUMN_NAMES = {"Host", "Method", "Path", "Status", "Enable"};

    private static final int TABLE_COLUMN_COUNT = 5;

    public static final int HOST_COLUMN_INDEX   = 0;
    public static final int METHOD_COLUMN_INDEX = 1;
    public static final int PATH_COLUMN_INDEX   = 2;
    public static final int STATUS_COLUMN_INDEX = 3;
    public static final int ENABLE_COLUMN_INDEX = 4;

    public void addRequestResponse(RequestResponseEntity requestResponse) {
        requestResponseEntityList.add(requestResponse);

        // https://docs.oracle.com/javase/8/docs/api/javax/swing/table/AbstractTableModel.html#fireTableRowsInserted-int-int-
        fireTableRowsInserted(0, requestResponseEntityList.size() - 1);
    }

    public RequestResponseEntity getRequestResponse(int rowIndex) {
        return requestResponseEntityList.get(rowIndex);
    }

    public List<RequestResponseEntity> getRequestResponseEntityList() {
        return this.requestResponseEntityList;
    }

    public int getColumnCount() {
        return TABLE_COLUMN_COUNT;
    }

    public int getRowCount() {
        return requestResponseEntityList.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        RequestResponseEntity requestResponseEntity = requestResponseEntityList.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return requestResponseEntity.getHost();
            case 1:
                return requestResponseEntity.getMethod();
            case 2:
                return requestResponseEntity.getPath();
            case 3:
                return requestResponseEntity.getResponseStatus();
            case 4:
                return requestResponseEntity.isEnabled();
            default:
                return "";
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        return COLUMN_NAMES[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return (columnIndex == ENABLE_COLUMN_INDEX);
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        RequestResponseEntity requestResponseEntity = requestResponseEntityList.get(rowIndex);

        switch (columnIndex) {
            case 4:
                requestResponseEntity.setEnabled((boolean) value);
        }
        requestResponseEntityList.set(rowIndex, requestResponseEntity);
    }
}