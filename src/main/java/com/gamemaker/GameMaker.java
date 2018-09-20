package com.gamemaker;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.SwingUtilities;

import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;

//import com.component.Ball;
//import com.component.Brick;
//import com.component.Clock;
//import com.component.Paddle;
import com.controller.*;
import com.dimension.Circle;
import com.dimension.Coordinate;
import com.dimension.Rectangle;
//import com.helper.CollisionChecker;
import com.infrastruture.*;
import com.timer.GameTimer;
import com.ui.GUI;
import com.ui.GamePanel;
import com.ui.MainPanel;
import com.ui.DesignPanel;
import com.ui.ControlPanel;

public class GameMaker {
	protected static Logger log = Logger.getLogger(GameMaker.class);
	public static void startGame(boolean isRestart){

		/**
		 *  We need: 
		 *  - Main panel: holds everything
		 *  - Design panel: holds ui for creating objects
		 *  - Game panel: area where graphic elements are placed
		 *  - Control panel: area where control buttons are placed
		 */
		// Create observable
		GameTimer observable  = new GameTimer();
		
		// Create our 4 panels
		MainPanel mainPanel = new MainPanel();
		DesignPanel designPanel = new DesignPanel();
		GamePanel boardPanel = new GamePanel();
		ControlPanel controlPanel = new ControlPanel();
		
		//Add everything to mainPanel in order
		mainPanel.addComponent(designPanel);
		mainPanel.addComponent(boardPanel);
		mainPanel.addComponent(controlPanel);
	
		// Create the GUI class and pass all the panels
		GUI gui = new GUI(mainPanel,designPanel, boardPanel, controlPanel);
		
		gui.addComponent(mainPanel);
		
		// ! Not sure where this logic will end up
		//CollisionChecker checker = new CollisionChecker();
		
		MainController driver = new MainController(gui,observable); // maybe keep this checker);
		
		gui.addDriver(driver);
		observable.startTimer();
		gui.setVisible(true);

		gui.draw(null);
		gui.pack();
		if(isRestart)
			observable.registerObserver(driver);
		else
			driver.pause();
	}
	public static void main(String args[]) {
		PropertyConfigurator.configure("log4j.properties");
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				startGame(false);	
			}
		});
	}
}