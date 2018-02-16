package com.motikan2010;


import burp.*;
import com.motikan2010.entity.RequestResponseEntity;
import com.motikan2010.util.RequestResponseUtils;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SampleTab extends JPanel {

    private final RequestTableModel requestTableModel;
    private final RequestTableManager requestTableManager;

    private JTextArea requestTextArea;
    private JTextArea responseTextArea;

    private IBurpExtenderCallbacks iBurpExtenderCallbacks;
    private IExtensionHelpers iExtensionHelpers;
    private RequestResponseUtils requestResponseUtils;

    public SampleTab(RequestResponseUtils utils) {
        iBurpExtenderCallbacks = BurpExtender.getCallbacks();
        iExtensionHelpers = BurpExtender.getHelpers();
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
        jTable.getColumnModel().getColumn(RequestTableModel.METHOD_COLUMN_INDEX).setWidth(300);

        // Url Column
        jTable.getColumnModel().getColumn(RequestTableModel.PATH_COLUMN_INDEX).setMaxWidth(300);

        // Status Column
        jTable.getColumnModel().getColumn(RequestTableModel.STATUS_COLUMN_INDEX).setPreferredWidth(150);
        jTable.getColumnModel().getColumn(RequestTableModel.STATUS_COLUMN_INDEX).setResizable(false);

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
        RequestResponseEntity requestResponseEntity = requestTableManager.getRequestResponse(rowNum);
        String request = requestResponseUtils.showRequest(requestResponseEntity.getRequestResponse());
        String response  = requestResponseUtils.showResponse(requestResponseEntity.getRequestResponse());
        requestTextArea.setText(request);
        responseTextArea.setText(response);
    }

    public void keepRequest(RequestResponseEntity requestResponseEntity) {
        requestTableManager.addRequestResponse(requestResponseEntity);
    }
}