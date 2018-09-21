package com.commands;

import org.apache.log4j.Logger;

import com.components.GameElement;
import com.infrastruture.Command;

public class UpMoveCommand implements Command{
	
	protected Logger logger = Logger.getLogger(UpMoveCommand.class);
	private GameElement element;
	private int prevY;
	
	public UpMoveCommand(GameElement element) {
		this.element = element;
	}
	
	@Override
	public void execute() {
		prevY = element.getY();
		
		element.setY(element.getY() - element.getVelY());
	}

	@Override
	public void undo() {
		element.setY(prevY);
	}
}
