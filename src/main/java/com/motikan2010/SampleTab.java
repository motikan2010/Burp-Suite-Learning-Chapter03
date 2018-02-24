package com.motikan2010;


import burp.BurpExtender;
import burp.IHttpRequestResponse;
import com.motikan2010.entity.RequestResponseEntity;
import com.motikan2010.util.RequestResponseUtils;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SampleTab extends JPanel {

    private final RequestTableModel requestTableModel;
    private final RequestTableManager requestTableManager;

    private JTextArea requestTextArea;
    private JTextArea requestResponseTextArea;

    private RequestResponseUtils requestResponseUtils;

    public SampleTab(RequestResponseUtils utils) {
        requestResponseUtils = utils;

        requestTableModel = new RequestTableModel();
        requestTableManager = new RequestTableManager(requestTableModel);
    }

    public void render() {

        setLayout(new GridLayout(1, 2));

        JPanel leftJPanel = new JPanel();
        JPanel rightJPanel = new JPanel();
        GridLayout leftLayout = new GridLayout(2, 1);
        leftJPanel.setLayout(leftLayout);

        GridBagLayout rightLayout = new GridBagLayout();
        rightJPanel.setLayout(rightLayout);

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

        // Method Column
        jTable.getColumnModel().getColumn(RequestTableModel.METHOD_COLUMN_INDEX).setMinWidth(50);
        jTable.getColumnModel().getColumn(RequestTableModel.METHOD_COLUMN_INDEX).setMaxWidth(60);

        // Path Column
        jTable.getColumnModel().getColumn(RequestTableModel.PATH_COLUMN_INDEX).setMinWidth(200);

        // Status Column
        jTable.getColumnModel().getColumn(RequestTableModel.STATUS_COLUMN_INDEX).setMinWidth(50);
        jTable.getColumnModel().getColumn(RequestTableModel.STATUS_COLUMN_INDEX).setMaxWidth(60);

        // Status Column
        jTable.getColumnModel().getColumn(RequestTableModel.ENABLE_COLUMN_INDEX).setMinWidth(50);
        jTable.getColumnModel().getColumn(RequestTableModel.ENABLE_COLUMN_INDEX).setMaxWidth(60);

        // スクロールパネルにテーブルを追加します
        JScrollPane requestScrollPane = new JScrollPane(jTable);

        // リクエスト内容を表示するテキストエリア
        requestTextArea = new JTextArea();
        requestTextArea.setLineWrap(true);
        JScrollPane requestTextAreaPane = new JScrollPane(requestTextArea);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.NONE;
        JButton sendButton = new JButton("送信");
        rightLayout.setConstraints(sendButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.NONE;
        JButton clearButton = new JButton("クリア");
        rightLayout.setConstraints(clearButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        // レスポンス内容を表示するテキストエリア
        requestResponseTextArea = new JTextArea();
        requestResponseTextArea.setLineWrap(true);
        JScrollPane requestResponseTextAreaPane = new JScrollPane(requestResponseTextArea);
        rightLayout.setConstraints(requestResponseTextAreaPane, gbc);

        // ◆「送信」ボタン押下時に発火
        ExecutorService executor = Executors.newSingleThreadExecutor();
        sendButton.addActionListener(e -> executor.submit(() ->
                // requestResponseEntityList(テーブル内のデータ)に格納されているリクエストを一行ずつ送信
                requestTableManager.getRequestResponseList().stream().forEach(iHttpRequestResponse -> {
                    // リクエストをテキストエリアに追加
                    requestResponseTextArea.append(requestResponseUtils.showRequest(iHttpRequestResponse) // リクエスト情報
                            + requestResponseUtils.getNewLine() + requestResponseUtils.getNewLine());

                    // リクエストの送信
                    IHttpRequestResponse response = BurpExtender.getCallbacks().makeHttpRequest(iHttpRequestResponse.getHttpService(), iHttpRequestResponse.getRequest());

                    // レスポンスをテキストエリアに追加
                    requestResponseTextArea.append(requestResponseUtils.showResponse(response) // レスポンス情報
                            + requestResponseUtils.getNewLine() + requestResponseUtils.getNewLine() + requestResponseUtils.getNewLine()); //
                })
        ));

        // 「クリア」ボタン押下時に発火
        // リクエスト&レスポンステキストエリアを空文字で初期化
        clearButton.addActionListener(e -> requestResponseTextArea.setText(""));

        leftJPanel.add(requestScrollPane);
        leftJPanel.add(requestTextAreaPane);
        rightJPanel.add(sendButton);
        rightJPanel.add(clearButton);
        rightJPanel.add(requestResponseTextAreaPane);

        add(leftJPanel);
        add(rightJPanel);
    }

    private void sendRequest() {

    }

    /**
     * テーブルの行を選択
     * @param rowNum 行番号
     */
    public void selectRequest(int rowNum) {
        RequestResponseEntity requestResponseEntity = requestTableManager.getRequestResponse(rowNum);
        String request = requestResponseUtils.showRequest(requestResponseEntity.getRequestResponse());
        requestTextArea.setText(request);
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