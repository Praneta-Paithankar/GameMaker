package com.controller;

import java.util.ArrayList;
import java.util.List;

import com.components.GameElement;
import com.ui.GUI;

public class DesignController {
	private List<GameElement> elements;
	private GUI mainJframe;
	
	public DesignController(GUI gui) {
		elements = new ArrayList<>();
		mainJframe = gui;
	}
	
	public void addGameElement() {
		
	}
	
	public List<GameElement> getElements() {
		return elements;
	}

	public void setElements(List<GameElement> elements) {
		this.elements = elements;
	}
	
}
