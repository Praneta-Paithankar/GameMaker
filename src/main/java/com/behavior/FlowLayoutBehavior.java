package com.behavior;

import java.awt.Dimension;
import java.awt.FlowLayout;

import com.ui.AbstractPanel;
import com.ui.LayoutBehavior;

public class FlowLayoutBehavior implements LayoutBehavior {
	@Override
	public void updateLayoutBehavior(AbstractPanel abstractPanel, int width, int height) {
		abstractPanel.setLayout(new FlowLayout());
		abstractPanel.setPreferredSize(new Dimension(width, height));
	}

}
