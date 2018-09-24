package com.infrastruture;

import java.io.Serializable;

public interface Command extends Serializable{
	public void execute();
	public void undo();
}
