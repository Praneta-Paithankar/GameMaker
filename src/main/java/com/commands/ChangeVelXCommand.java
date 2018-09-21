package com.commands;

import org.apache.log4j.Logger;

import com.components.GameElement;
import com.infrastruture.Command;

public class ChangeVelXCommand implements Command{

	public static final Logger logger = Logger.getLogger(ChangeVelXCommand.class);
	private GameElement element;
	
	public ChangeVelXCommand(GameElement element) {
		this.element = element;
	}
	
	@Override
	public void execute() {
		element.setVelX(-1 * element.getVelX());
	}

	@Override
	public void undo() {
		element.setVelX(-1 * element.getVelX());
	}
}
