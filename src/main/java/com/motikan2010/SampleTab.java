package com.motikan2010;

import javax.swing.*;
import java.awt.*;

public class SampleTab extends JPanel {

    private static SampleTab panel;

    public static final int FLOW_LAYOUT = 0;
    public static final int GRID_LAYOUT = 1;
    public static final int GRID_BAG_LAYOUT = 2;
    public static final int BORDER_LAYOUT = 3;
    public static final int BOX_LAYOUT = 4;
    public static final int PANEL_LAYOUT = 5;

    public static SampleTab getInstance() {
        if (panel == null) {
            panel = new SampleTab();
        }
        return panel;
    }

    public void render(int type) {
        switch (type) {
            case 0:
                renderFlowLayout();
                break;

            case 1:
                renderGridLayout();
                break;

            case 2:
                renderGridBagLayout();
                break;

            case 3:
                renderBorderLayout();
                break;

            case 4:
                renderBoxLayout();
                break;

            case 5:
                renderPanelLayout();
                break;
        }
    }

    /**
     * フローレイアウト
     */
    private void renderFlowLayout() {
        setLayout(new FlowLayout());

        JButton jButton1 = new JButton("ボタン1");
        JButton jButton2 = new JButton("ボタン2");
        JButton jButton3 = new JButton("ボタン3");
        add(jButton1);
        add(jButton2);
        add(jButton3);
    }

    /**
     * グリッドレイアウト
     */
    private void renderGridLayout() {
        setLayout(new GridLayout(2, 3));

        add(new JButton("ボタン1"));
        add(new JButton("ボタン2"));
        add(new JButton("ボタン3"));
        add(new JButton("ボタン4"));
        add(new JButton("ボタン5"));
        add(new JButton("ボタン6"));
    }

    /**
     * グリッドバッグレイアウト
     */
    private void renderGridBagLayout() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);

        GridBagConstraints gbc = new GridBagConstraints();

        JButton jButton1 = new JButton("ボタン1");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gridBagLayout.setConstraints(jButton1, gbc);

        JButton jButton2 = new JButton("ボタン2");
        jButton2.setFont(new Font("Arial", Font.PLAIN, 30));

        gbc.gridx = 1;
        gbc.gridy = 0;
        gridBagLayout.setConstraints(jButton2, gbc);


        JButton jButton3 = new JButton("ボタン3");
        gbc.gridx = 1;
        gbc.gridy = 1;
        gridBagLayout.setConstraints(jButton3, gbc);

        add(jButton1);
        add(jButton2);
        add(jButton3);
    }

    /**
     * ボーダーレイアウト
     */
    private void renderBorderLayout() {
        setLayout(new BorderLayout());

        add("North", new JButton("ボタン1"));
        add("East", new JButton("ボタン2"));
        add("South", new JButton("ボタン3"));
        add("West", new JButton("ボタン4"));
        add("Center", new JButton("ボタン5"));
    }

    /**
     * ボックスレイアウト
     */
    private void renderBoxLayout() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(new JButton("ボタン1"));
        add(new JButton("ボタン2"));
        add(new JButton("ボタン3"));
    }

    /**
     * パネルを利用
     */
    private void renderPanelLayout() {
        setLayout(new GridLayout(1, 2));

        Panel panel1 = new Panel();
        Panel panel2 = new Panel();
        panel1.setLayout(new GridLayout(1, 1));
        panel2.setLayout(new GridLayout(3, 1));

        panel1.add(new JButton("ボタン1"));
        panel2.add(new JButton("ボタン2"));
        panel2.add(new JButton("ボタン3"));
        panel2.add(new JButton("ボタン4"));

        add(panel1);
        add(panel2);
    }
}