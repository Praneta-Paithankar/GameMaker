package com.UI;

import javax.swing.*;

import com.constants.Constants;

import java.awt.*;

public class GridBagStrategy implements Strategy {
    private JPanel mainPanel;
    private JPanel preview;
    private JTabbedPane tabbedPane;
    private LayoutManager layout;
   

    public GridBagStrategy(JPanel mainPanel, JTabbedPane tabbedPane, JPanel preview){
        this.mainPanel = mainPanel;
        this.tabbedPane = tabbedPane;
        this.layout = new GridBagLayout();
        this.mainPanel.setLayout(this.layout);
        this.setObjects();
    }

    @Override
    public void setObjects() {

        GridBagConstraints cst = new GridBagConstraints();

        // add button 1 to the panel
        cst.fill = GridBagConstraints.HORIZONTAL;
        cst.gridx = 0;
        cst.gridy = 0;
        panel.add(b1,cst);

        // add button 2 to the panel
        cst.fill = GridBagConstraints.HORIZONTAL;

        cst.gridx = 1;
        cst.gridy = 0;
        panel.add(b2);
        // add button 3 to the panel

        cst.gridx = 0;
        cst.gridy = 1;
        panel.add(b3, cst);
        // add button 4 to the panel

        cst.fill = GridBagConstraints.HORIZONTAL;
        cst.gridwidth = 1;
        cst.gridx = 1;
        cst.gridy = 1;
        panel.add(b4,cst);

        // add button 5 to the panel
        cst.fill = GridBagConstraints.HORIZONTAL;
        cst.gridx = 0;
        cst.gridwidth = 1;
        cst.gridy = 2;       //third row

        panel.add(b5,cst);

        cst.fill = GridBagConstraints.HORIZONTAL;
        cst.gridx = 1;
        cst.gridwidth = 1;
        cst.gridy = 2;       //2 row

        panel.add(b6,cst);

        cst.fill = GridBagConstraints.HORIZONTAL;
        cst.gridx = 0;
        cst.gridwidth = 2;
        cst.gridy = 3;       //third row

        panel.add(b7,cst);

        cst.fill = GridBagConstraints.HORIZONTAL;
        cst.gridx = 0;
        cst.gridwidth = 2;
        cst.gridy = 4;       //third row

        panel.add(b8,cst);


        panel.setMaximumSize(new Dimension(Constants.GRID_WIDTH,Constants.GRID_HEIGHT));
        //frame.add(panel);
        //frame.setVisible(true);
    }
}
