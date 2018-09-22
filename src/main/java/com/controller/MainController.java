package com.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import org.apache.log4j.Logger;

import com.commands.MoveCommand;
import com.commands.NullCommand;
import com.commands.TimerCommand;
import com.components.GameElement;
import com.infrastruture.Command;
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
	private GameController gameController;
	
	public MainController(GUI gui,GameTimer observable,DesignController designController,GameController gameController) { 
		this.gui = gui;
		this.observable = observable;
		this.gameController = gameController;
		this.designController = designController;
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
		TimerCommand timerCommand = new TimerCommand(designController.getClock());
		List<GameElement> graphicsElements = designController.getTimerElements();
		for(GameElement element: graphicsElements) {
			Command command = createCommand(element);
			command.execute();
			addCommand(command);
		}
		
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
