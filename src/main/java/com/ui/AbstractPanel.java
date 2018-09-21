package com.ui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class AbstractPanel extends JPanel {
	LayoutBehavior layoutBehavior;
	
	public AbstractPanel() {
		setBorder(BorderFactory.createRaisedBevelBorder());
	}
	
	public void setLayoutBehavior(LayoutBehavior layoutBehavior) {
		this.layoutBehavior = layoutBehavior; 
	}
	
	public void performUpdateLayout(AbstractPanel abstractPanel, int width, int height) {
		layoutBehavior.updateLayoutBehavior(abstractPanel, width, height);
		abstractPanel.setMaximumSize(new Dimension(width, height));
		abstractPanel.setMinimumSize(new Dimension(width, height));
		abstractPanel.setPreferredSize(new Dimension(width, height));
	}

	public void reset() {
		// TODO Auto-generated method stub
		
	}
}
