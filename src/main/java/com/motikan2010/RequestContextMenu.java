package com.motikan2010;

import burp.IBurpExtenderCallbacks;
import burp.IExtensionHelpers;
import burp.IHttpRequestResponse;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class RequestContextMenu implements MouseListener {

    private IBurpExtenderCallbacks iBurpExtenderCallbacks;
    private IExtensionHelpers iExtensionHelpers;
    private IHttpRequestResponse[] requestResponseArray;
    private SampleTab sampleTab;

    public RequestContextMenu(IBurpExtenderCallbacks callbacks, IHttpRequestResponse[] requestResponseArray, SampleTab sampleTab) {
        this.iBurpExtenderCallbacks = callbacks;
        this.iExtensionHelpers = callbacks.getHelpers();
        this.requestResponseArray = requestResponseArray;
        this.sampleTab = sampleTab;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (IHttpRequestResponse iHttpRequestResponse : this.requestResponseArray) {
            this.sampleTab.keepRequest(this.iExtensionHelpers.analyzeRequest(iHttpRequestResponse), iHttpRequestResponse);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
