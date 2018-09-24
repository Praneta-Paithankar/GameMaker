package com.behavior;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;

import com.ui.AbstractPanel;
import com.ui.LayoutBehavior;

public class GridBagLayoutBehavior implements LayoutBehavior {

	@Override
	public void updateLayoutBehavior(AbstractPanel abstractPanel, int width, int height) {
		abstractPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		Component[] comp = abstractPanel.getComponents();
		int padding = 7;
		int[] x = new int[] {0, 1, 2, 0, 1, 2, 1, 0, 2};
		int[] y = new int[] {0, 0, 0, 2, 2, 2, 1, 1, 1};
		
		for (int i = 0;i<comp.length;i++) {
			c.weightx = 0.5;
			c.insets = new Insets(padding, padding, padding, padding);
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = x[i];
			c.gridy = y[i];
			c.weightx = 0.5;
			abstractPanel.add(comp[i], c);
		}
		abstractPanel.setPreferredSize(new Dimension(width, height));
		
	}

}
