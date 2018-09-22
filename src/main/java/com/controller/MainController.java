package com.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import org.apache.log4j.Logger;

import com.commands.ChangeVelXCommand;
import com.commands.ChangeVelYCommand;
import com.commands.MoveCommand;
import com.commands.NullCommand;
import com.commands.TimerCommand;
import com.components.Clock;
import com.components.GameElement;
import com.helper.Collider;
import com.helper.CollisionChecker;
import com.infrastruture.Command;
import com.infrastruture.Direction;
import com.infrastruture.Observer;
import com.timer.GameTimer;
import com.ui.GUI;

public class MainController implements Observer, KeyListener, ActionListener{
	protected static Logger log = Logger.getLogger(MainController.class);
	private GUI gui;
    private GameTimer observable;
    private boolean isGamePaused;
    private Deque<Command> commandQueue;
	private DesignController designController;
	private CollisionChecker collisionChecker ;
	
	public MainController(GUI gui,GameTimer observable,DesignController designController, CollisionChecker collisionChecker) { 
		this.gui = gui;
		this.observable = observable;

		this.designController = designController;
		this.collisionChecker = collisionChecker;
		isGamePaused = false;
		commandQueue = new ArrayDeque<Command>();
    }

	@Override
	public void actionPerformed(ActionEvent event) {
		String commandText= event.getActionCommand();
		if(commandText.equals("create")) {
			designController.addGameElement();
			designController.addControlElement();
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		Clock clock = designController.getClock();
		TimerCommand timerCommand = new TimerCommand(designController.getClock());
		timerCommand.execute();
		addCommand(timerCommand);
		for(GameElement element : designController.getElements()) {
			Direction direction = collisionChecker.checkCollisionBetweenGameElementAndBounds(element);
			if(direction == direction.X) {
				Command command = new ChangeVelXCommand(element);
				command.execute();
				addCommand(command);
			}
			else if(direction == direction.Y) {
				 Command command = new ChangeVelYCommand(element);
				 command.execute();
				 addCommand(command);
			}
		}
		for(Collider collider: designController.getColliders()) {
			collider.execute(this);
		}
		List<GameElement> graphicsElements = designController.getTimerElements();
		for(GameElement element: graphicsElements) {
			Command command = createCommand(element);
			command.execute();
			System.out.println(element.getName() + " : X ="+ element.getX()+ " and Y : "+ element.getY());
			addCommand(command);
		}
		gui.repaint();
	}	
	
	public Command createCommand(GameElement element) {
		return new MoveCommand(element);
	}
	public void  addCommand(Command command) {
		commandQueue.add(command);
	}
	public void pause() {
		isGamePaused = true;
		if(!observable.isObserverListEmpty()) {
		observable.removeObserver(this);
		}
	}
}
