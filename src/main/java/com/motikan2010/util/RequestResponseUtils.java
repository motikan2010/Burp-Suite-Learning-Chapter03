package com.motikan2010.util;

import burp.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class RequestResponseUtils {

    private static RequestResponseUtils utils;
    private static IExtensionHelpers iExtensionHelpers;

    private static final String NEW_LINE = System.lineSeparator();

    private RequestResponseUtils() {
        utils = null;
    }

    public static RequestResponseUtils getInstance() {
        iExtensionHelpers = BurpExtender.getHelpers();
        if (utils == null) {
            utils = new RequestResponseUtils();
        }
        return utils;
    }

    public String getNewLine() {
        return NEW_LINE;
    }

    /**
     * リクエスト情報を取得
     *
     * @param iHttpRequestResponse リクエスト&レスポンス
     * @return String
     */
    public String showRequest(IHttpRequestResponse iHttpRequestResponse) {
        StringBuilder stringBuilder = new StringBuilder();

        // リクエスト情報を取得
        IRequestInfo iRequestInfo = iExtensionHelpers.analyzeRequest(iHttpRequestResponse);

        // リクエストヘッダ情報を取得
        List<String> headers = iRequestInfo.getHeaders();
        stringBuilder.append(createHeaderRaw(headers));

        // リクエストボディ情報を取得
        byte[] requestBytes = iHttpRequestResponse.getRequest();
        stringBuilder.append(createBodyRaw(requestBytes));

        return stringBuilder.toString();
    }

    /**
     * レスポンス情報を取得
     *
     * @param iHttpRequestResponse リクエスト&レスポンス
     * @return String
     */
    public String showResponse(IHttpRequestResponse iHttpRequestResponse) {
        StringBuilder stringBuilder = new StringBuilder();

        // レスポンス情報を取得
        IResponseInfo iResponseInfo = iExtensionHelpers.analyzeResponse(iHttpRequestResponse.getResponse());

        // レスポンスヘッダ情報を取得
        List<String> headers = iResponseInfo.getHeaders();
        stringBuilder.append(createHeaderRaw(headers));

        // レスポンスボディ情報を取得
        byte[] responseBytes = iHttpRequestResponse.getResponse();
        stringBuilder.append(createBodyRaw(responseBytes));

        return stringBuilder.toString();
    }

    /**
     * ヘッダーリストを文字列に変換
     *
     * @param headers ヘッダーのリスト
     * @return ヘッダーのテキスト
     */
    private static String createHeaderRaw(List<String> headers) {
        StringBuilder stringBuilder = new StringBuilder();
        // リクエストヘッダ情報を取得
        for (String header : headers) {
            stringBuilder.append(header);
            stringBuilder.append(NEW_LINE);
        }
        return stringBuilder.toString();
    }

    /**
     * ボディのバイト配列を文字列に変換
     *
     * @param bodyBytes ヘッダーのバイト配列
     * @return ボディのテキスト
     */
    private static String createBodyRaw(byte[] bodyBytes) {
        String response = "";
        try {
            response = new String(bodyBytes, "UTF-8");
            response = response.substring(iExtensionHelpers.analyzeResponse(bodyBytes).getBodyOffset());
        } catch (UnsupportedEncodingException e) {
            System.out.println("Error converting string");
        }

        if (response.length() > 0) {
            response = NEW_LINE + response;
            return response;
        } else {
            return "";
        }
    }
}
