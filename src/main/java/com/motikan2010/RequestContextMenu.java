package com.motikan2010;

import burp.IHttpRequestResponse;
import com.motikan2010.entity.RequestResponseEntity;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class RequestContextMenu implements MouseListener {

    private IHttpRequestResponse[] requestResponseArray;
    private SampleTab sampleTab;

    public RequestContextMenu(IHttpRequestResponse[] requestResponseArray, SampleTab sampleTab) {
        this.requestResponseArray = requestResponseArray;
        this.sampleTab = sampleTab;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (IHttpRequestResponse iHttpRequestResponse : this.requestResponseArray) {
            this.sampleTab.keepRequest(new RequestResponseEntity(iHttpRequestResponse));
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
