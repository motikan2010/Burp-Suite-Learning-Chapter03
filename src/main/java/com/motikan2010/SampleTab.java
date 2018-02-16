package com.motikan2010;


import burp.*;
import com.motikan2010.util.RequestResponseUtils;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SampleTab extends JPanel {

    private RequestTableModel requestTableModel;
    private RequestTableManager requestTableManager;

    private JTextArea requestTextArea;
    private JTextArea responseTextArea;

    private IBurpExtenderCallbacks iBurpExtenderCallbacks;
    private IExtensionHelpers iExtensionHelpers;
    private RequestResponseUtils requestResponseUtils;

    public SampleTab(IBurpExtenderCallbacks callbacks, RequestResponseUtils utils) {
        iBurpExtenderCallbacks = callbacks;
        iExtensionHelpers = callbacks.getHelpers();
        requestResponseUtils = utils;

        requestTableModel = new RequestTableModel();
        requestTableManager = new RequestTableManager(requestTableModel);
    }

    public void render() {

        setLayout(new GridLayout(1, 2));

        Panel panel1 = new Panel();
        Panel panel2 = new Panel();
        panel1.setLayout(new GridLayout(1, 1));
        panel2.setLayout(new GridLayout(2, 1));

        requestTableModel = new RequestTableModel();
        JTable jTable = new JTable(requestTableModel);

        // Click table row
        jTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                selectRequest(jTable.getSelectedRow());
            }
        });

        // Host Column
        jTable.getColumnModel().getColumn(RequestTableModel.HOST_COLUMN_INDEX).setPreferredWidth(150);
        jTable.getColumnModel().getColumn(RequestTableModel.HOST_COLUMN_INDEX).setMinWidth(100);
        jTable.getColumnModel().getColumn(RequestTableModel.HOST_COLUMN_INDEX).setMaxWidth(250);

        // Method Column
        jTable.getColumnModel().getColumn(RequestTableModel.METHOD_COLUMN_INDEX).setWidth(30);
        jTable.getColumnModel().getColumn(RequestTableModel.METHOD_COLUMN_INDEX).setMaxWidth(40);

        // Url Column
        jTable.getColumnModel().getColumn(RequestTableModel.URL_COLUMN_INDEX).setMaxWidth(50);
        jTable.getColumnModel().getColumn(RequestTableModel.URL_COLUMN_INDEX).setResizable(false);

        // Status Column
        jTable.getColumnModel().getColumn(RequestTableModel.STATUS_COLUMN_INDEX).setPreferredWidth(150);

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
        IHttpRequestResponse iHttpRequestResponse = requestTableManager.getRequestResponse(rowNum);
        String request = requestResponseUtils.showRequest(iHttpRequestResponse);
        String response  = requestResponseUtils.showResponse(iHttpRequestResponse);
        requestTextArea.setText(request);
        responseTextArea.setText(response);
    }

    public void keepRequest(IRequestInfo iRequestInfo, IHttpRequestResponse iHttpRequestResponse) {
        Integer rowIndex = requestTableManager.getRowCount();
        requestTableModel.setValueAt(iRequestInfo.getUrl().getHost(), rowIndex, RequestTableModel.HOST_COLUMN_INDEX);
        requestTableModel.setValueAt(iRequestInfo.getMethod(), rowIndex, RequestTableModel.METHOD_COLUMN_INDEX);
        requestTableModel.setValueAt(iRequestInfo.getUrl().getPath(), rowIndex, RequestTableModel.URL_COLUMN_INDEX);
        requestTableModel.setValueAt(iExtensionHelpers.analyzeResponse(iHttpRequestResponse.getResponse()).getStatusCode(),
                rowIndex, RequestTableModel.STATUS_COLUMN_INDEX);
        requestTableManager.addRequestResponse(iHttpRequestResponse);
    }
}