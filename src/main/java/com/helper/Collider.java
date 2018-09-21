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
import com.infrastruture.Command;
import com.infrastruture.Direction;

public class Collider {
	public static final Logger logger = Logger.getLogger(Collider.class);
	private GameElement element1;
	private GameElement element2;
	private CollisionChecker collisionChecker;
	private MoveType element1MoveType;
	private MoveType element2MoveType;
	private ArrayList<Command> element1Command;
	private ArrayList<Command> element2Command;
	
	public Collider(GameElement element1, GameElement element2, MoveType element1Behaviour, MoveType element2Behaviour) {
		this.element1 = element1;
		this.element2 = element2;
		this.element1MoveType = element1Behaviour;
		this.element2MoveType = element2Behaviour;
		this.collisionChecker = new CollisionChecker();
		this.element1Command = getCommandsForBehaviour(element1, element1Behaviour);
		this.element2Command = getCommandsForBehaviour(element2, element2Behaviour);
	}
	
	private ArrayList<Command> getCommandsForBehaviour(GameElement element, MoveType moveType) {
		// TODO Auto-generated method stub
		ArrayList<Command> commandsList = new ArrayList<Command>();
		if(moveType == MoveType.FREE) {
			commandsList.add(new MoveCommand(element));
		}
		else if(moveType == MoveType.FOURWAY) {
			commandsList.add(new LeftMoveCommand(element));
			commandsList.add(new RightMoveCommand(element));
			commandsList.add(new UpMoveCommand(element));
			commandsList.add(new DownMoveCommand(element));
		}
		else if(moveType == MoveType.LEFTRIGHT) {
			commandsList.add(new LeftMoveCommand(element));
			commandsList.add(new RightMoveCommand(element));
		}
		else if(moveType == MoveType.UPDOWN) {
			commandsList.add(new UpMoveCommand(element));
			commandsList.add(new DownMoveCommand(element));
		}
		return commandsList;
	}

	public void execute() {
	}
	
	public void checkCollisions(MoveType behaviour, GameElement element1, GameElement element2) {
		if(collisionChecker.checkIntersectionBetweenElements(element1, element2)) {
			Direction direction = collisionChecker.checkCollisionBetweenGameElements(element1, element2);
			if(behaviour == MoveType.FREE) {
				changeDirectionsOnCollision(element1, direction);
			}
			if(behaviour == MoveType.LEFTRIGHT) {
				
			}
			if(behaviour == MoveType.UPDOWN) {
				
			}
			if(behaviour == MoveType.FOURWAY) {
				
			}
			if(behaviour == MoveType.FIXED) {
				//Do nothing
			}
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
