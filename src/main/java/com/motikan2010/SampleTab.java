package com.motikan2010;

import javax.swing.*;
import java.awt.*;

public class SampleTab extends JPanel {

    private static SampleTab panel;

    private static final int PANEL_X = 10;
    private static final int PANEL_Y = 10;

    public static SampleTab getInstance() {
        if (panel == null) {
            panel = new SampleTab();
        }
        return panel;
    }

    public void render() {
        setLayout(null);
        this.commonRender();
        this.syncRender();
    }

    private void commonRender() {

    }

    private void syncRender() {
        Label sampleLabel = new Label("Hello, Tab");
        sampleLabel.setForeground(new Color(255, 0, 0));
        sampleLabel.setFont(new Font("Dialog", Font.BOLD, 15));
        sampleLabel.setBounds(PANEL_X, PANEL_Y, 200, 50);
        this.add(sampleLabel);
    }
}