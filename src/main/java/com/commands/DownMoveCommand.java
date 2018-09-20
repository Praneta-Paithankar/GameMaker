package com.commands;

import org.apache.log4j.Logger;

import com.components.GameElement;
import com.infrastruture.Command;

public class DownMoveCommand implements Command{
	
	protected Logger logger = Logger.getLogger(DownMoveCommand.class);
	private GameElement element;
	private int prevY;
	
	public DownMoveCommand(GameElement element) {
		this.element = element;
	}
	
	@Override
	public void execute() {
		prevY = element.getY();
		
		element.setY(element.getY() + element.getVelY());
	}

	@Override
	public void undo() {
		element.setY(prevY);
	}
}
