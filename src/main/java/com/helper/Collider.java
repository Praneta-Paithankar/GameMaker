package com.helper;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.commands.ChangeVelXCommand;
import com.commands.ChangeVelYCommand;
import com.commands.DownMoveCommand;
import com.commands.LeftMoveCommand;
import com.commands.MoveCommand;
import com.commands.RightMoveCommand;
import com.commands.UpMoveCommand;
import com.components.GameElement;
import com.infrastruture.MoveType;
import com.infrastruture.CollisionType;
import com.infrastruture.Command;
import com.infrastruture.Direction;

public class Collider {
	public static final Logger logger = Logger.getLogger(Collider.class);
	private GameElement element1;
	private GameElement element2;
	private CollisionType collisionType1;
	private CollisionType collisionType2;
	private CollisionChecker collisionChecker;
	
	public Collider(GameElement element1, GameElement element2, CollisionType collisionType1, CollisionType collisionType2,CollisionChecker collisionChecker) {
		this.element1 = element1;
		this.element2 = element2;
		this.collisionType1 = collisionType1;
		this.collisionType2 = collisionType2;
		this.collisionChecker = collisionChecker;
	}
	
	public void execute() {
		if(collisionChecker.checkIntersectionBetweenElements(element1, element2)) {
			Command command = getCollisionAction(element1, collisionType1);
			if (collisionType1 == CollisionType.BOUNCE) {
				Direction direction = collisionChecker.checkCollisionBetweenGameElements(element1, element2);
				changeDirectionsOnCollision(element1, direction);
			}
			command.execute();
			command = getCollisionAction(element2, collisionType2);
			if (collisionType2 == CollisionType.BOUNCE) {
				Direction direction = collisionChecker.checkCollisionBetweenGameElements(element2, element1);
				changeDirectionsOnCollision(element2, direction);
			}
			command.execute();
		}
	}
	
	public void checkCollisions(MoveType behaviour, GameElement element1, GameElement element2) {
		if(collisionChecker.checkIntersectionBetweenElements(element1, element2)) {
			Direction direction = collisionChecker.checkCollisionBetweenGameElements(element1, element2);
			changeDirectionsOnCollision(element1, direction);
			//saveloadCommandList.add(new MoveCommand(element1));
		}
	}
	 
	public Command getCollisionAction(GameElement gameElement, CollisionType collisionType) {
		if(collisionType == CollisionType.BOUNCE) {
			return new MoveCommand(gameElement);
		}
		if(collisionType == CollisionType.EXPLODE) {
			return new ExplodeCommand(gameElement);
		}
		if(collisionType == CollisionType.FIXED) {
			return new FixedCommand(gameElement);
		}
	}
	
	public void changeDirectionsOnCollision(GameElement element, Direction direction) {
		Command changeVelXCommand = null;
		Command changeVelYCommand = null;
		if(direction == Direction.X) {
			 changeVelXCommand = new ChangeVelXCommand(element);
		}
		if(direction == Direction.Y) {
			changeVelYCommand = new ChangeVelYCommand(element);
		}
		if(direction == Direction.BOTH) {
			changeVelXCommand = new ChangeVelXCommand(element);
			changeVelYCommand = new ChangeVelYCommand(element);
		}
		if(changeVelXCommand != null) {
			changeVelXCommand.execute();
		}
		if(changeVelYCommand != null) {
			changeVelYCommand.execute();
		}
	}
}
