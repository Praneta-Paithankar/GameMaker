package com.controller;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.swing.JComponent;

import com.components.Clock;
import com.components.GameElement;
import com.dimension.Coordinate;
import com.dimension.Dimensions;
import com.helper.Collider;
import com.helper.CollisionChecker;
import com.infrastruture.ActionType;
import com.infrastruture.CollisionType;
import com.infrastruture.Element;
import com.infrastruture.MoveType;
import com.strategy.DrawOvalColor;
import com.strategy.DrawRectangularColorShape;
import com.ui.CustomButton;
import com.ui.GUI;
import com.ui.GamePanel;

public class DesignController {
	
	private List<GameElement> graphicsElements;
	private GUI mainJframe;
	private List<GameElement> timerElements;
	private HashMap<Integer,List<GameElement>> keyboardElements;
	private HashMap <String,ActionType> controlElements;
	private List<Collider> colliders;
	private Clock clock;
	private MainController mainController;
	
	public DesignController(GUI gui) {
		mainJframe = gui;
		graphicsElements = new ArrayList<>();
		timerElements  = new ArrayList<>();
		keyboardElements = new HashMap<>();
		controlElements = new HashMap<>();
		colliders = new ArrayList<>();
	}
	
	public List<GameElement> getKeyboardElementsBasedKeys(int key)
	{
		if(keyboardElements.containsKey(key)) {
			return keyboardElements.get(key);
		}
		return null;
	}
	public ActionType getActionTypeBasedOnButtonCommand(String key) {
		if(controlElements.containsKey(key)) {
			return controlElements.get(key);
		}
		return null;
	}
	public void addGameElement() {

		// gui.getData();
		GameElement elementPaddle = new GameElement(new Dimensions(80,10), new Coordinate(100, 350), "Paddle", MoveType.LEFTRIGHT);
		elementPaddle.setColor(Color.BLACK);
		elementPaddle.setDraw(new DrawRectangularColorShape());
		
		GameElement elementBall =  new GameElement(new Dimensions(15), new Coordinate(50, 50), "Ball", MoveType.FREE);
		elementBall.setColor(Color.RED);
		elementBall.setDraw(new DrawOvalColor());
		
		GameElement elementBrick1 = new GameElement(new Dimensions(50, 25), new Coordinate(250, 90), "Brick", MoveType.FIXED);
		elementBrick1.setColor(Color.BLUE);
		elementBrick1.setDraw(new DrawRectangularColorShape());
		
		GameElement elementBrick2 = new GameElement(new Dimensions(50, 25), new Coordinate(370, 90), "Brick", MoveType.FIXED);
		elementBrick2.setColor(Color.BLUE);
		elementBrick2.setDraw(new DrawRectangularColorShape());
		
		clock = new Clock();
		
		
		// add element into elements
		graphicsElements.add(elementPaddle);
		graphicsElements.add(elementBall);
		graphicsElements.add(elementBrick1);
		graphicsElements.add(elementBrick2);
		
		timerElements.add(elementBall);
		
//		keyboardElements.put(KeyEvent.VK_LEFT, new ArrayList<GameElement>(Arrays.asList(elementPaddle)));
//		keyboardElements.put(KeyEvent.VK_RIGHT, new ArrayList<GameElement>(Arrays.asList(elementPaddle)));
//		
//		controlElements.put("START", ActionType.START);
//		controlElements.put("PAUSE", ActionType.PAUSE);
//		controlElements.put("UNDO", ActionType.UNDO);
//		controlElements.put("SAVE", ActionType.SAVE);
//		controlElements.put("LOAD", ActionType.LOAD);
//		controlElements.put("REPLAY", ActionType.REPLAY);
//		controlElements.put("CHANGELAYOUT", ActionType.CHANGELAYOUT);
//		
		CollisionChecker collisionChecker = new CollisionChecker();
		Collider ballPaddle = new Collider(elementBall, elementPaddle, CollisionType.BOUNCE, CollisionType.FIXED, collisionChecker);
		Collider ballBrick1 = new Collider(elementBall, elementBrick1, CollisionType.BOUNCE, CollisionType.EXPLODE, collisionChecker);
		Collider ballBrick2 = new Collider(elementBall, elementBrick2, CollisionType.BOUNCE, CollisionType.EXPLODE, collisionChecker);
		
		colliders.add(ballPaddle);
		colliders.add(ballBrick1);
		colliders.add(ballBrick2);
//		
//		
		// add elements into gamePanel
		mainJframe.getGamePanel().addComponent(elementPaddle);
		mainJframe.getGamePanel().addComponent(elementBall);
		mainJframe.getGamePanel().addComponent(elementBrick1);
		mainJframe.getGamePanel().addComponent(elementBrick2);
		mainJframe.getControlPanel().addComponent(clock);

		mainJframe.revalidate();
		mainJframe.repaint();
		// update timer Elements or KeyboardElements
	}
	
	
	public void addControlElement() {
		
		JComponent component = mainJframe.getDesignPanel().getControlElement();
		if(component instanceof CustomButton) {
			  CustomButton received = (CustomButton) component;
			  CustomButton button = new CustomButton(received.getText(),received.getText(),received.getWidth(),received.getHeight(),mainController);
			  button.setActionType(received.getActionType());
			  controlElements.put(button.getActionCommand(), button.getActionType());
			  mainJframe.getControlPanel().addComponent(button);
			  mainJframe.getControlPanel().add(button);
		}
	    mainJframe.getControlPanel().revalidate();
	}

	public List<GameElement> getTimerElements() {
		return timerElements;
	}

	public void setTimerElements(List<GameElement> timerElements) {
		this.timerElements = timerElements;
	}

	public Clock getClock() {
		return clock;
	}

	public void setClock(Clock clock) {
		this.clock = clock;
	}

	public HashMap <String,ActionType> getControlElements() {
		return controlElements;
	}

	public void setControlElements(HashMap <String,ActionType> controlElements) {
		this.controlElements = controlElements;
	}

	public List<Collider> getColliders() {
		return colliders;
	}

	public void setColliders(List<Collider> colliders) {
		this.colliders = colliders;
	}
	public List<GameElement> getGraphicsElements() {
		return graphicsElements;
	}

	public void setGraphicsElements(List<GameElement> graphicsElements) {
		this.graphicsElements = graphicsElements;
	}

	public HashMap<Integer, List<GameElement>> getKeyboardElements() {
		return keyboardElements;
	}

	public void setKeyboardElements(HashMap<Integer, List<GameElement>> keyboardElements) {
		this.keyboardElements = keyboardElements;
	}

	public MainController getMainController() {
		return mainController;
	}

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}
	
}
