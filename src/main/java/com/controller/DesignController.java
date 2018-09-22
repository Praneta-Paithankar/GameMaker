package com.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.components.Clock;
import com.components.GameElement;
import com.helper.Collider;
import com.infrastruture.ActionType;
import com.infrastruture.Element;
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
