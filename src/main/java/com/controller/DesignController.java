package com.controller;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
	
	public DesignController(GUI gui) {
		mainJframe = gui;
		graphicsElements = new ArrayList<>();
		timerElements  = new ArrayList<>();
		colliders = new ArrayList<>();
		keyboardElements = new HashMap<>();
		controlElements = new HashMap<>();
	}
	
	public List<GameElement> getKeyboardElementsBasedKeys(int key)
	{
		if(keyboardElements.containsKey(key)) {
			List<GameElement> elements = keyboardElements.get(key);
			return elements;
		}
		return null;
	}
	
	public void addGameElement() {

		// gui.getData();
		GameElement elementPaddle = new GameElement(new Dimensions(80,10), new Coordinate(300, 350), "Paddle", MoveType.LEFTRIGHT);
		elementPaddle.setColor(Color.BLACK);
		elementPaddle.setDraw(new DrawRectangularColorShape());
		elementPaddle.setVelX(20);
		elementPaddle.setVisible(true);
		
		GameElement elementBall =  new GameElement(new Dimensions(15), new Coordinate(50, 50), "Ball", MoveType.FREE);
		elementBall.setColor(Color.RED);
		elementBall.setDraw(new DrawOvalColor());
		elementBall.setVelX(1);
		elementBall.setVelY(1);
		elementBall.setVisible(true);
		
		GameElement elementBrick1 = new GameElement(new Dimensions(50, 25), new Coordinate(250, 90), "Brick", MoveType.FIXED);
		elementBrick1.setColor(Color.BLUE);
		elementBrick1.setDraw(new DrawRectangularColorShape());
		elementBrick1.setVisible(true);
		
		GameElement elementBrick2 = new GameElement(new Dimensions(50, 25), new Coordinate(563, 79), "Brick", MoveType.FIXED);
		elementBrick2.setColor(Color.BLUE);
		elementBrick2.setDraw(new DrawRectangularColorShape());
		elementBrick2.setVisible(true);
		
		clock = new Clock();
		
		// add element into elements
		graphicsElements.add(elementPaddle);
		graphicsElements.add(elementBall);
		graphicsElements.add(elementBrick1);
		graphicsElements.add(elementBrick2);
		
		timerElements.add(elementBall);
		
		keyboardElements.put(KeyEvent.VK_LEFT, new ArrayList<GameElement>(Arrays.asList(elementPaddle)));
		keyboardElements.put(KeyEvent.VK_RIGHT, new ArrayList<GameElement>(Arrays.asList(elementPaddle)));
		
//		controlElements.put("START", ActionType.START);
//		controlElements.put("PAUSE", ActionType.PAUSE);
//		controlElements.put("UNDO", ActionType.UNDO);
//		controlElements.put("SAVE", ActionType.SAVE);
//		controlElements.put("LOAD", ActionType.LOAD);
//		controlElements.put("REPLAY", ActionType.REPLAY);
//		controlElements.put("CHANGELAYOUT", ActionType.CHANGELAYOUT);
		
		CollisionChecker collisionChecker = new CollisionChecker();
		Collider ballPaddle = new Collider(elementBall, elementPaddle, CollisionType.BOUNCE, CollisionType.FIXED, collisionChecker);
		Collider ballBrick1 = new Collider(elementBall, elementBrick1, CollisionType.BOUNCE, CollisionType.EXPLODE, collisionChecker);
		Collider ballBrick2 = new Collider(elementBall, elementBrick2, CollisionType.BOUNCE, CollisionType.EXPLODE, collisionChecker);
		
		colliders.add(ballPaddle);
		colliders.add(ballBrick1);
		colliders.add(ballBrick2);

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
	
	public List<GameElement> getElements() {
		return graphicsElements;
	}

	public void setElements(List<GameElement> elements) {
		this.graphicsElements = elements;
	}

	public List<GameElement> getTimerElements() {
		return timerElements;
	}

	public void setTimerElements(List<GameElement> timerElements) {
		this.timerElements = timerElements;
	}

	public void addControlElement() {
		
		// gui.getData();
		// add element into elements
		// add elements into controlPanel with actions
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
	
	
}
