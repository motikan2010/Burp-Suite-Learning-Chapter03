package com.motikan2010;


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

    private RequestResponseUtils requestResponseUtils;

    public SampleTab(RequestResponseUtils utils) {
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
        jTable.setShowVerticalLines(true);
        jTable.setShowHorizontalLines(false);

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
        jTable.getColumnModel().getColumn(RequestTableModel.METHOD_COLUMN_INDEX).setMinWidth(50);
        jTable.getColumnModel().getColumn(RequestTableModel.METHOD_COLUMN_INDEX).setMaxWidth(60);

        // Path Column
        jTable.getColumnModel().getColumn(RequestTableModel.PATH_COLUMN_INDEX).setMinWidth(200);
        jTable.getColumnModel().getColumn(RequestTableModel.PATH_COLUMN_INDEX).setMaxWidth(300);

        // Status Column
        jTable.getColumnModel().getColumn(RequestTableModel.STATUS_COLUMN_INDEX).setMinWidth(50);
        jTable.getColumnModel().getColumn(RequestTableModel.STATUS_COLUMN_INDEX).setMaxWidth(60);

        // スクロールパネルにテーブルを追加します
        JScrollPane requestScrollPane = new JScrollPane(jTable);

        // リクエスト内容を表示するテキストエリア
        requestTextArea = new JTextArea();
        requestTextArea.setLineWrap(true);
        JScrollPane requestTextAreaPane = new JScrollPane(requestTextArea);

        // レスポンス内容を表示するテキストエリア
        responseTextArea = new JTextArea();
        responseTextArea.setLineWrap(true);
        JScrollPane responseTextAreaPane = new JScrollPane(responseTextArea);

        panel1.add(requestScrollPane);
        panel2.add(requestTextAreaPane);
        panel2.add(responseTextAreaPane);

        add(panel1);
        add(panel2);
    }

    /**
     * テーブルの行を選択
     * @param rowNum 行番号
     */
    public void selectRequest(int rowNum) {
        RequestResponseEntity requestResponseEntity = requestTableManager.getRequestResponse(rowNum);
        String request = requestResponseUtils.showRequest(requestResponseEntity.getRequestResponse());
        String response  = requestResponseUtils.showResponse(requestResponseEntity.getRequestResponse());
        requestTextArea.setText(request);
        responseTextArea.setText(response);
    }

    /**
     * リクエスト&レスポンスを保存
     *
     * @param requestResponseEntity 保存対象のリクエスト&レスポンスのエンティティ
     */
    public void keepRequest(RequestResponseEntity requestResponseEntity) {
        requestTableManager.addRequestResponse(requestResponseEntity);
    }
}