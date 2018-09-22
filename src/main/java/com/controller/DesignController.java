package com.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.components.Clock;
import com.components.GameElement;
import com.helper.Collider;
import com.infrastruture.ActionType;
import com.infrastruture.Element;
import com.ui.CustomButton;
import com.ui.GUI;

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
		keyboardElements = new HashMap<>();
		controlElements = new HashMap<>();
		colliders = new ArrayList<>();
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
		// add element into elements
		// add elements into gamePanel
		// update timer Elements or KeyboardElements
	}
	
	
	public void addControlElement() {
		
		CustomButton button = mainJframe.getDesignPanel().getButton();
	    controlElements.put(button.getActionCommand(), button.getActionType());
	    mainJframe.getControlPanel().addComponent(button);
	    mainJframe.getControlPanel().add(button);
	    //		controlElements.put(button.getActionCommand(),button.getActionType());
		// gui.getData();
		// add element into elements
		// add elements into controlPanel with actions
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
	
}
