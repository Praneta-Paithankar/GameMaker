package com.commands;

import org.apache.log4j.Logger;

import com.components.GameElement;
import com.infrastruture.Command;

public class FreeMoveCommand implements Command{
	
	public static final Logger logger = Logger.getLogger(FreeMoveCommand.class);
	private GameElement element;
	
	public FreeMoveCommand(GameElement element) {
		this.element = element;
	}

	@Override
	public void execute() {
		element.setX(element.getX() + element.getVelX());
		element.setY(element.getY() + element.getVelY());
	}

	@Override
	public void undo() {
		element.setX(element.getX() - element.getVelX());
		element.setY(element.getY() - element.getVelY());
	}
	
}
