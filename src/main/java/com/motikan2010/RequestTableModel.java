package com.motikan2010;

import burp.IHttpRequestResponse;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class RequestTableModel extends AbstractTableModel {

    private final List<IHttpRequestResponse> iHttpRequestResponseList;

    private static Object[][] tableData;

    private static final String[] COLUMN_NAMES = {"Host", "Method", "URL", "Status"};

    private static final int TABLE_ROW_COUNT = 10;
    private static final int TABLE_COLUMN_COUNT = 4;

    public static final int HOST_COLUMN_INDEX   = 0;
    public static final int METHOD_COLUMN_INDEX = 1;
    public static final int URL_COLUMN_INDEX    = 2;
    public static final int STATUS_COLUMN_INDEX = 3;

    public RequestTableModel() {
        iHttpRequestResponseList = new ArrayList<>();
        tableData = new Object[TABLE_ROW_COUNT][TABLE_COLUMN_COUNT];
        for(int i = 0; i < TABLE_ROW_COUNT; i++) {
            tableData[i][HOST_COLUMN_INDEX]   = "";
            tableData[i][METHOD_COLUMN_INDEX] = "";
            tableData[i][URL_COLUMN_INDEX]    = "";
            tableData[i][STATUS_COLUMN_INDEX] = "";
        }
    }

    public void addRequestResponse(IHttpRequestResponse requestResponse) {
        iHttpRequestResponseList.add(requestResponse);
    }

    public IHttpRequestResponse getRequestResponse(int rowIndex) {
        return iHttpRequestResponseList.get(rowIndex);
    }

    public List<IHttpRequestResponse> getRequestResponseList() {
        return iHttpRequestResponseList;
    }

    public int getColumnCount() {
        return TABLE_COLUMN_COUNT;
    }

    public int getRowCount() {
        return TABLE_ROW_COUNT;
        // return iHttpRequestResponseList.size();
    }

    public int getCount() {
        return iHttpRequestResponseList.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return tableData[rowIndex][columnIndex];
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
        return true;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        tableData[rowIndex][columnIndex] = value;
        fireTableDataChanged();
    }
}