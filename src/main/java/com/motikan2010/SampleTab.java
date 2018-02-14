package com.motikan2010;


import burp.*;
import com.motikan2010.util.RequestResponseUtils;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

public class SampleTab extends JPanel {

    private static SampleTab panel;

    private RequestTable requestTable;
    private JTextArea requestTextArea;
    private JTextArea responseTextArea;

    private static IBurpExtenderCallbacks iBurpExtenderCallbacks;
    private static IExtensionHelpers iExtensionHelpers;
    private static RequestResponseUtils requestResponseUtils;

    private List<IHttpRequestResponse> iHttpRequestResponseList;

    public static SampleTab getInstance(IBurpExtenderCallbacks callbacks, RequestResponseUtils utils) {
        iBurpExtenderCallbacks = callbacks;
        iExtensionHelpers = callbacks.getHelpers();
        requestResponseUtils = utils;
        if (panel == null) {
            panel = new SampleTab();
        }
        return panel;
    }

    public void render() {
        iHttpRequestResponseList = new LinkedList<>();

        setLayout(new GridLayout(1, 2));

        Panel panel1 = new Panel();
        Panel panel2 = new Panel();
        panel1.setLayout(new GridLayout(1, 1));
        panel2.setLayout(new GridLayout(2, 1));

        requestTable = new RequestTable();
        JTable jTable = new JTable(requestTable);

        // Click table row
        jTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                selectRequest(jTable.getSelectedRow());
            }
        });

        // Host Column
        jTable.getColumnModel().getColumn(RequestTable.HOST_COLUMN_INDEX).setPreferredWidth(150);
        jTable.getColumnModel().getColumn(RequestTable.HOST_COLUMN_INDEX).setMinWidth(100);
        jTable.getColumnModel().getColumn(RequestTable.HOST_COLUMN_INDEX).setMaxWidth(250);

        // Method Column
        jTable.getColumnModel().getColumn(RequestTable.METHOD_COLUMN_INDEX).setWidth(30);
        jTable.getColumnModel().getColumn(RequestTable.METHOD_COLUMN_INDEX).setMaxWidth(40);

        // Url Column
        jTable.getColumnModel().getColumn(RequestTable.URL_COLUMN_INDEX).setMaxWidth(50);
        jTable.getColumnModel().getColumn(RequestTable.URL_COLUMN_INDEX).setResizable(false);

        // Status Column
        jTable.getColumnModel().getColumn(RequestTable.STATUS_COLUMN_INDEX).setPreferredWidth(150);

        JScrollPane requestScrollPane = new JScrollPane(jTable);

        requestTextArea = new JTextArea();
        JScrollPane requestTextAreaPane = new JScrollPane(requestTextArea);

        responseTextArea = new JTextArea();
        JScrollPane responseTextAreaPane = new JScrollPane(responseTextArea);

        panel1.add(requestScrollPane);
        panel2.add(requestTextAreaPane);
        panel2.add(responseTextAreaPane);

        add(panel1);
        add(panel2);
    }

    public void selectRequest(int rowNum) {
        IHttpRequestResponse iHttpRequestResponse = iHttpRequestResponseList.get(rowNum);
        String request = requestResponseUtils.showRequest(iHttpRequestResponse);
        String response  = requestResponseUtils.showResponse(iHttpRequestResponse);
        requestTextArea.setText(request);
        responseTextArea.setText(response);
    }

    public void keepRequest(IRequestInfo iRequestInfo, IHttpRequestResponse iHttpRequestResponse) {
        Integer rowIndex = iHttpRequestResponseList.size();
        requestTable.setValueAt(iRequestInfo.getUrl().getHost(), rowIndex, RequestTable.HOST_COLUMN_INDEX);
        requestTable.setValueAt(iRequestInfo.getMethod(), rowIndex, RequestTable.METHOD_COLUMN_INDEX);
        requestTable.setValueAt(iRequestInfo.getUrl().getPath(), rowIndex, RequestTable.URL_COLUMN_INDEX);
        requestTable.setValueAt(iExtensionHelpers.analyzeResponse(iHttpRequestResponse.getResponse()).getStatusCode(),
                rowIndex, RequestTable.STATUS_COLUMN_INDEX);
        iHttpRequestResponseList.add(iHttpRequestResponse);
    }
}