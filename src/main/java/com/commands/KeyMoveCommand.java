package com.commands;

import org.apache.log4j.Logger;

import com.components.GameElement;
import com.infrastruture.Command;
import com.infrastruture.Constants;
import com.infrastruture.KeyType;

public class KeyMoveCommand implements Command{
	
	public static final Logger logger = Logger.getLogger(KeyMoveCommand.class);
	private GameElement element;
	private KeyType keyType;
	private int prevX;
	private int prevY;
	
	public KeyMoveCommand(GameElement element, KeyType keyType) {
		this.element = element;
		this.keyType = keyType;
	}

	@Override
	public void execute() {
		prevX = element.getX();
		prevY = element.getY();
		
		switch(keyType) {
		case RIGHT:
			if(element.getX() + element.getWidth() + element.getVelX() <= Constants.GAME_PANEL_WIDTH) {
				element.setX(element.getX() + element.getVelX());
			}
			break;
		case LEFT:
			if(element.getX() - element.getVelX() >= 0) {
				element.setX(element.getX() - element.getVelX());
			}
			break;
		case UP:
			if(element.getY() - element.getVelY() >= 0) {
				element.setY(element.getY() - element.getVelY());
			}
			break;
		case DOWN:
			if(element.getY() + element.getHeight()+ element.getVelY() <= Constants.GAME_PANEL_HEIGHT) {
				element.setY(element.getY() + element.getVelY());
			}
			break;
		}
	}

	@Override
	public void undo() {
		element.setX(prevX);
		element.setY(prevY);
	}
}
