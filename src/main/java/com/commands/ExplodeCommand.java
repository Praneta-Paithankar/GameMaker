package com.commands;

import org.apache.log4j.Logger;

import com.components.GameElement;
import com.infrastruture.Command;

public class ExplodeCommand implements Command{
	
	public static final Logger logger = Logger.getLogger(ExplodeCommand.class);
	private GameElement element;

	public ExplodeCommand(GameElement element) {
		this.element = element;
	}

	@Override
	public void execute() {
		element.setVisible(false);
	}

	@Override
	public void undo() {
		element.setVisible(true);
	}
	
}
