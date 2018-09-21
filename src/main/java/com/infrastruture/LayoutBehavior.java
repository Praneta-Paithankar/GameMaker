/**
 *This interface is implemented by our view to change the layout*/
package com.infrastruture;

import com.ui.AbstractPanel;

public interface LayoutBehavior {
	public void updateLayoutBehavior(AbstractPanel abstractPanel, int width, int height);
}
