package com.commands;

import org.apache.log4j.Logger;

import com.components.GameElement;
import com.infrastruture.Command;
import com.infrastruture.KeyType;

public class KeyVelChange implements Command{
	
	public static final Logger logger = Logger.getLogger(KeyVelChange.class);
	private GameElement element;
	private KeyType keyType;
	
	public KeyVelChange(GameElement element, KeyType keyType) {
		this.element = element;
		this.keyType = keyType;
	}

	@Override
	public void execute() {
		switch(keyType) {
		case RIGHT:
			if(element.getVelX() < 0) {
				element.setVelX(-1 * element.getVelX());
			}
			break;
		case LEFT:
			if(element.getVelX() > 0) {
				element.setVelX(-1 * element.getVelX());
			}
			break;
		case UP:
			if(element.getVelY() < 0) {
				element.setVelY(-1 * element.getVelY());
			}
			break;
		case DOWN:
			if(element.getVelY() > 0) {
				element.setVelY(-1 * element.getVelY());
			}
			break;
		}
	}

	@Override
	public void undo() {
		switch(keyType) {
		case RIGHT:
			if(element.getVelX() < 0) {
				element.setVelX(-1 * element.getVelX());
			}
			break;
		case LEFT:
			if(element.getVelX() > 0) {
				element.setVelX(-1 * element.getVelX());
			}
			break;
		case UP:
			if(element.getVelY() < 0) {
				element.setVelY(-1 * element.getVelY());
			}
			break;
		case DOWN:
			if(element.getVelY() > 0) {
				element.setVelY(-1 * element.getVelY());
			}
			break;
		}
	}
}
