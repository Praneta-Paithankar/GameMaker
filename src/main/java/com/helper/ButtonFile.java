package com.helper;

import javax.swing.JButton;

public class ButtonFile extends JButton{
	ActionType actionType;

	public ActionType getActionType() {
		return actionType;
	}

	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}
}
