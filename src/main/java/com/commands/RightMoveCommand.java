package com.commands;

import org.apache.log4j.Logger;

import com.components.GameElement;
import com.infrastruture.Command;

public class RightMoveCommand implements Command{
	
	protected Logger logger = Logger.getLogger(RightMoveCommand.class);
	private GameElement element;
	private int prevX;
	
	public RightMoveCommand(GameElement element) {
		this.element = element;
	}
	
	@Override
	public void execute() {
		prevX = element.getY();
		
		element.setX(element.getY() + element.getVelX());
	}

	@Override
	public void undo() {
		element.setX(prevX);
	}

}
