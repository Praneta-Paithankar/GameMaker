package com.commands;

import org.apache.log4j.Logger;

import com.components.GameElement;
import com.infrastruture.Command;

public class LeftMoveCommand implements Command{
	
	protected Logger logger = Logger.getLogger(LeftMoveCommand.class);
	private GameElement element;
	private int prevX;
	
	public LeftMoveCommand(GameElement element) {
		this.element = element;
	}
	
	@Override
	public void execute() {
		prevX = element.getY();
		
		element.setX(element.getY() - element.getVelX());
	}

	@Override
	public void undo() {
		element.setX(prevX);
	}


}
