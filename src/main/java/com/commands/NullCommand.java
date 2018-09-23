package com.commands;

import com.components.GameElement;
import com.infrastruture.Command;

public class NullCommand implements Command {
	private GameElement element;
	
	public NullCommand(GameElement element) {
		this.element = element;
	}

	@Override
	public void execute() {
		
	}

	@Override
	public void undo() {
		
	}
	
}
