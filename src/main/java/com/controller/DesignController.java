package com.controller;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.swing.JComponent;

import com.commands.GameOverEvent;
import com.commands.ScoreEvent;
import com.commands.SoundEvent;
import com.components.Clock;
import com.components.GameElement;
import com.components.ScoreBoard;
import com.dimension.Coordinate;
import com.dimension.Dimensions;
import com.helper.Collider;
import com.helper.CollisionChecker;
import com.infrastruture.ActionType;
import com.infrastruture.CollisionType;
import com.infrastruture.Command;
import com.infrastruture.Constants;
import com.infrastruture.Element;
import com.infrastruture.ElementListener;
import com.infrastruture.Event;
import com.infrastruture.GameElementShape;
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
	private List<GameElement> scoreElementList;
	private long timeConstraintinmilliSeconds;
	private boolean timerConstraintSpecified;
	private Clock clock;
	private MainController mainController;
	private ScoreBoard scoreBoard;

	public DesignController(GUI gui) {
		mainJframe = gui;
		graphicsElements = new ArrayList<>();
		timerElements  = new ArrayList<>();
		keyboardElements = new HashMap<>();
		controlElements = new HashMap<>();
		colliders = new ArrayList<>();
		scoreElementList = new ArrayList<>();
		setTimerConstraintSpecified(false);
		clock = new Clock(new Coordinate(0, 0));
		scoreBoard = new ScoreBoard(new Coordinate(0, 0));
		keyboardElements.put(KeyEvent.VK_LEFT, new ArrayList<GameElement>());
		keyboardElements.put(KeyEvent.VK_RIGHT, new ArrayList<GameElement>());
		keyboardElements.put(KeyEvent.VK_UP, new ArrayList<GameElement>());
		keyboardElements.put(KeyEvent.VK_DOWN, new ArrayList<GameElement>());
		
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
//		GameElement elementPaddle = new GameElement(new Dimensions(80,10), new Coordinate(300, 350), "Paddle", MoveType.LEFTRIGHT,20,0, "Rectangle");
//		elementPaddle.setColor(Color.GREEN);
//		elementPaddle.setDraw(new DrawRectangularColorShape());
//		elementPaddle.setVisible(true);
//		elementPaddle.setGameElementShape(GameElementShape.RECTANGLE);
//		
//		//scoreElement , graphics ele
//		
//		
//		
////		controlElements.put("START", ActionType.P);
////		controlElements.put("PAUSE", ActionType.PAUSE);
////		controlElements.put("UNDO", ActionType.UNDO);
////		controlElements.put("SAVE", ActionType.SAVE);
////		controlElements.put("LOAD", ActionType.LOAD);
////		controlElements.put("REPLAY", ActionType.REPLAY);
////		controlElements.put("CHANGELAYOUT", ActionType.CHANGELAYOUT);
////		
//		Command soundEvent = new SoundEvent("explosion.wav");
//		Command scoreUpdate = new ScoreEvent(scoreBoard);
//		Command GameOver = new GameOverEvent(mainController);
//		
//		ArrayList<Command> eventList= new ArrayList<>(Arrays.asList(soundEvent, scoreUpdate));
//		ArrayList<Command> eventList1= new ArrayList<>(Arrays.asList(soundEvent, scoreUpdate,GameOver));
//		
//		CollisionChecker collisionChecker = new CollisionChecker();
//		Collider ballPaddle = new Collider(elementBall, elementPaddle, CollisionType.BOUNCE, CollisionType.FIXED, collisionChecker, null);
//		Collider ballBrick1 = new Collider(elementBall, elementBrick1, CollisionType.BOUNCE, CollisionType.EXPLODE, collisionChecker, eventList);
//		Collider ballBrick2 = new Collider(elementBall, elementBrick2, CollisionType.BOUNCE, CollisionType.EXPLODE, collisionChecker, eventList);
//		Collider ballBrick3 = new Collider(elementBall, elementBrick3, CollisionType.BOUNCE, CollisionType.EXPLODE, collisionChecker, eventList1);
//		
//		Collider ballPaddle1 = new Collider(elementBall1, elementPaddle, CollisionType.BOUNCE, CollisionType.FIXED, collisionChecker, null);
//		Collider ballBrick11 = new Collider(elementBall1, elementBrick1, CollisionType.BOUNCE, CollisionType.EXPLODE, collisionChecker, null);
//		Collider ballBrick21 = new Collider(elementBall1, elementBrick2, CollisionType.BOUNCE, CollisionType.EXPLODE, collisionChecker, null);
//		Collider ballBrick31 = new Collider(elementBall1, elementBrick3, CollisionType.BOUNCE, CollisionType.EXPLODE, collisionChecker, null);
//		Collider brickbrick = new Collider(elementBrick3, elementBrick2, CollisionType.BOUNCE, CollisionType.BOUNCE, collisionChecker, null);
//
//		
//		
//		Collider ballball = new Collider(elementBall, elementBall1, CollisionType.BOUNCE, CollisionType.BOUNCE, collisionChecker, null);
//		
//		colliders.add(ballPaddle);
//		colliders.add(ballBrick1);
//		colliders.add(ballBrick2);
//		colliders.add(ballBrick3);
//		colliders.add(ballball);
//		colliders.add(ballPaddle1);
//		colliders.add(ballBrick11);
//		colliders.add(ballBrick21);
//		colliders.add(brickbrick);
////		
////		
//		// add elements into gamePanel
//		mainJframe.getGamePanel().addComponent(elementPaddle);
//		mainJframe.revalidate();
//		mainJframe.repaint();
//		timerConstraintSpecified = true;
//		timeConstraintinmilliSeconds = 10000;
//		// update timer Elements or KeyboardElements
	}

	public void addTimer() {
		// TODO Auto-generated method stub
		clock = new Clock(new Coordinate(Constants.TimerX, Constants.TimerY));
		mainJframe.getControlPanel().addComponent(clock);
		mainJframe.revalidate();
		mainJframe.repaint();
		
	}

	public void addScore() {
		// TODO Auto-generated method stub
		scoreBoard = new ScoreBoard(new Coordinate(Constants.ScoreX,600));
		mainJframe.getControlPanel().addComponent(scoreBoard);
	}
	// To be used if we export some of the logic from the view to the controller
	public void addGameElement(GameElement element, boolean isScoreElement, ElementListener elementListner) {
		System.out.println("X : "+ element.getX() + " y : "+ element.getY());
		if(isScoreElement) {
			scoreElementList.add(element);
		}
		if(elementListner == ElementListener.TIMER) {
			timerElements.add(element);
		}
		if(elementListner == ElementListener.KEY) {
			if(element.getMoveType() == MoveType.LEFTRIGHT) {
				keyboardElements.get(KeyEvent.VK_LEFT).add(element);
				keyboardElements.get(KeyEvent.VK_RIGHT).add(element);
			}
			if(element.getMoveType() == MoveType.UPDOWN) {
				keyboardElements.get(KeyEvent.VK_UP).add(element);
				keyboardElements.get(KeyEvent.VK_DOWN).add(element);
			}
			if(element.getMoveType() == MoveType.FOURWAY) {
				keyboardElements.get(KeyEvent.VK_LEFT).add(element);
				keyboardElements.get(KeyEvent.VK_RIGHT).add(element);
				keyboardElements.get(KeyEvent.VK_UP).add(element);
				keyboardElements.get(KeyEvent.VK_DOWN).add(element);
			}

		}
		// add element into elements
		graphicsElements.add(element);
		mainJframe.getGamePanel().addComponent(element);
		mainJframe.revalidate();
		mainJframe.repaint();
	}
	
	public void addGameColliders(Collider collider) {
		colliders.add(collider);
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

	
	
	public ScoreBoard getScoreBoard() {
		return scoreBoard;
	}

	public void setScoreBoard(ScoreBoard scoreBoard) {
		this.scoreBoard = scoreBoard;
	}
	
	public List<GameElement> getScoreElementList() {
		return scoreElementList;
	}

	public void setScoreElementList(List<GameElement> scoreElementList) {
		this.scoreElementList = scoreElementList;
	}

	
	public boolean isTimerConstraintSpecified() {
		return timerConstraintSpecified;
	}

	public void setTimerConstraintSpecified(boolean timerConstraintSpecified) {
		this.timerConstraintSpecified = timerConstraintSpecified;
	}

	public long getTimeConstraintinmilliSeconds() {
		return timeConstraintinmilliSeconds;
	}

	public void setTimeConstraintinmilliSeconds(long timeConstraintinmilliSeconds) {
		this.timeConstraintinmilliSeconds = timeConstraintinmilliSeconds;
	}
}
