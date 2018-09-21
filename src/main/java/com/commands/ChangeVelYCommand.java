package com.commands;

import org.apache.log4j.Logger;

import com.components.GameElement;
import com.infrastruture.Command;

public class ChangeVelYCommand implements Command{

	public static final Logger logger = Logger.getLogger(ChangeVelYCommand.class);
	private GameElement element;
	
	public ChangeVelYCommand(GameElement element) {
		this.element = element;
	}
	
	@Override
	public void execute() {
		element.setVelY(-1 * element.getVelY());
	}

	@Override
	public void undo() {
		element.setVelY(-1 * element.getVelY());
	}

}
