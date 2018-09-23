package com.controller;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.Serializable;
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

public class DesignController implements Serializable{
	
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
		GameElement elementPaddle = new GameElement(new Dimensions(80,10), new Coordinate(300, 350), "Paddle", MoveType.LEFTRIGHT,20,0, "Rectangle");
		elementPaddle.setColor(Color.GREEN);
		elementPaddle.setDraw(new DrawRectangularColorShape());
		elementPaddle.setVisible(true);
		
		GameElement elementBall =  new GameElement(new Dimensions(15), new Coordinate(50, 50), "Ball", MoveType.FREE,2,2, "Oval");
		elementBall.setColor(Color.RED);
		elementBall.setDraw(new DrawOvalColor());
		elementBall.setVisible(true);
		
		GameElement elementBall1 =  new GameElement(new Dimensions(15), new Coordinate(100, 100), "Ball", MoveType.FREE,1,1, "Oval");
		elementBall1.setColor(Color.BLACK);
		elementBall1.setDraw(new DrawOvalColor());
		elementBall1.setVelX(1);
		elementBall1.setVelY(1);
		elementBall1.setVisible(true);
		
		GameElement elementBrick1 = new GameElement(new Dimensions(50, 25), new Coordinate(250, 90), "Brick", MoveType.FIXED,0,0, "Rectangle");
		elementBrick1.setColor(Color.BLUE);
		elementBrick1.setDraw(new DrawRectangularColorShape());
		elementBrick1.setVisible(true);
		
		GameElement elementBrick2 = new GameElement(new Dimensions(50, 25), new Coordinate(563, 79), "Brick", MoveType.FIXED,0,0, "Rectangle");
		elementBrick2.setColor(Color.BLUE);
		elementBrick2.setDraw(new DrawRectangularColorShape());
		elementBrick2.setVisible(true);

		GameElement elementBrick3 = new GameElement(new Dimensions(70, 50), new Coordinate(563, 79), "Brick", MoveType.LEFTRIGHT,1,0, "Rectangle");
		elementBrick3.setColor(Color.BLUE);
		elementBrick3.setDraw(new DrawRectangularColorShape());
		elementBrick3.setVisible(true);
		clock = new Clock(new Coordinate(30, 60));
		
		
		// add element into elements
		graphicsElements.add(elementPaddle);
		graphicsElements.add(elementBall);
		graphicsElements.add(elementBall1);
		graphicsElements.add(elementBrick1);
		graphicsElements.add(elementBrick2);
//		graphicsElements.add(elementBrick3);
		
		timerElements.add(elementBall);
		timerElements.add(elementBall1);
//		timerElements.add(elementBrick3);
		
		keyboardElements.put(KeyEvent.VK_LEFT, new ArrayList<GameElement>(Arrays.asList(elementPaddle)));
		keyboardElements.put(KeyEvent.VK_RIGHT, new ArrayList<GameElement>(Arrays.asList(elementPaddle)));
		
//		controlElements.put("START", ActionType.P);
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
		Collider ballBrick3 = new Collider(elementBall, elementBrick3, CollisionType.BOUNCE, CollisionType.EXPLODE, collisionChecker);
		
		Collider ballPaddle1 = new Collider(elementBall1, elementPaddle, CollisionType.BOUNCE, CollisionType.FIXED, collisionChecker);
		Collider ballBrick11 = new Collider(elementBall1, elementBrick1, CollisionType.BOUNCE, CollisionType.EXPLODE, collisionChecker);
		Collider ballBrick21 = new Collider(elementBall1, elementBrick2, CollisionType.BOUNCE, CollisionType.EXPLODE, collisionChecker);
		Collider ballBrick31 = new Collider(elementBall1, elementBrick3, CollisionType.BOUNCE, CollisionType.EXPLODE, collisionChecker);
		
		
		
		Collider ballball = new Collider(elementBall, elementBall1, CollisionType.BOUNCE, CollisionType.BOUNCE, collisionChecker);
		
		colliders.add(ballPaddle);
		colliders.add(ballBrick1);
		colliders.add(ballBrick2);
		colliders.add(ballBrick3);
		colliders.add(ballball);
		colliders.add(ballPaddle1);
		colliders.add(ballBrick11);
		colliders.add(ballBrick21);
		
//		
//		
		// add elements into gamePanel
		mainJframe.getGamePanel().addComponent(elementPaddle);
		mainJframe.getGamePanel().addComponent(elementBall);
		mainJframe.getGamePanel().addComponent(elementBall1);
		mainJframe.getGamePanel().addComponent(elementBrick1);
		mainJframe.getGamePanel().addComponent(elementBrick3);
		mainJframe.getGamePanel().addComponent(elementBrick2);
		mainJframe.getControlPanel().addComponent(clock);

		mainJframe.revalidate();
		mainJframe.repaint();
		// update timer Elements or KeyboardElements
	}
	// To be used if we export some of the logic from the view to the controller
	public void addGameElement(GameElement element) {
		mainJframe.getGamePanel().addComponent(element);
		mainJframe.revalidate();
		mainJframe.repaint();
	}
	
	public void addControlElement() {
		
		JComponent component = mainJframe.getDesignPanel().getControlElement();
		if(component instanceof CustomButton) {
			  CustomButton received = (CustomButton) component;
			  CustomButton button = new CustomButton(received.getText(),received.getText(),received.getWidth(),received.getHeight(),mainController);
			  button.setActionType(received.getActionType());
			  controlElements.put(button.getActionCommand(), button.getActionType());
//			  mainJframe.getControlPanel().addComponent(button);
			  mainJframe.getControlPanel().add(button);
			  mainJframe.getControlPanel().addButtons(button);
		}
	    mainJframe.getControlPanel().revalidate();
	}
	
	public void pushToPreview(GameElement temp) {
		// TODO Auto-generated method stub
		temp.pushToPreview();
		this.mainJframe.getDesignPanel().pushToPreview(temp);
		
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
