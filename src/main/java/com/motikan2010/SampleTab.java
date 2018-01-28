package com.motikan2010;

import javax.swing.*;

public class SampleTab extends JPanel {

    private static SampleTab panel;

    public static SampleTab getInstance() {
        if (panel == null) {
            panel = new SampleTab();
        }
        return panel;
    }

    public void render() {
        setLayout(null);
    }
}
