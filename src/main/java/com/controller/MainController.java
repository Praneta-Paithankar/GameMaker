package com.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayDeque;
import java.util.Deque;

import org.apache.log4j.Logger;

import com.infrastruture.Command;
import com.infrastruture.Observer;
import com.timer.GameTimer;
import com.ui.GUI;

public class MainController implements Observer, KeyListener,ActionListener{
	protected static Logger log = Logger.getLogger(MainController.class);
	private GUI gui;
    private GameTimer observable;
    private boolean isGamePaused;
    private Deque<Command> commandQueue;
		
	public MainController(GUI gui,GameTimer observable) { // Might keep CollisionChecker collisionChecker) {
		
		
		this.gui = gui;
		this.observable = observable;
		// this.collisionChecker = collisionChecker;
		isGamePaused = false;
		commandQueue = new ArrayDeque<Command>();
		//timerCommand = new TimerCommand(clock);	
		//initCommands();
    }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}	
	
	public void pause() {
		isGamePaused = true;
		if(!observable.isObserverListEmpty()) {
		observable.removeObserver(this);
		}
	}
}
