package com.helper;

public enum ActionType {
	SAVE,LOAD,PLAY,PAUSE,REPLAY;
	private static String [] representStr = {"SAVE","LOAD","PLAY","PAUSE", "REPLAY"};
	
	public static String [] getActionTypes() {
		return representStr;
	}
}
