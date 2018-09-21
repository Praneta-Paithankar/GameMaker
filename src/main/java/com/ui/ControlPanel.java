/**
 *This class contains all the buttons*/
package com.ui;

import java.awt.Graphics;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.behavior.FlowLayoutBehavior;
import com.controller.MainController;
import com.dimension.Coordinate;
import com.ui.AbstractPanel;
import com.infrastruture.Constants;
import com.infrastruture.Element;

@SuppressWarnings("serial")
public class ControlPanel  extends AbstractPanel implements Element {

	private MainController driver;
	private ArrayList<Element> elementList;
	
	public ControlPanel() {
		setLayoutBehavior(new FlowLayoutBehavior());
		performUpdateLayout(this, Constants.CONTROL_PANEL_WIDTH,Constants.CONTROL_PANEL_HEIGHT);
	}
	
	public void createButtons(MainController driver)
	{
		this.driver = driver;
		elementList = new ArrayList<>();
//	    createReplay();
//	    createUndo();
//	    createStart();
//	    createPause();
//	    
//	    createSave();
//	    createLoad();
//	    createLayout();
	}
	
//	public void createReplay() {
//		ControlPanelButton replayButton = new ControlPanelButton("Replay", "replay", driver);
//		this.add(replayButton);
//
//	}
//	
//	public void createUndo() {
//		ControlPanelButton undoButton = new ControlPanelButton("Undo", "undo", driver);
//		this.add(undoButton);
//
//	}
//	
//	public void createStart() {
//		ControlPanelButton startButton = new ControlPanelButton("Start", "start", driver);
//		this.add(startButton);
//	}
//	
//	public void createPause() {
//		ControlPanelButton pauseButton = new ControlPanelButton("Pause", "pause", driver);
//		this.add(pauseButton);
//	}
//
//	public void createLayout() {
//		ControlPanelButton layoutButton = new ControlPanelButton("Layout", "layout", driver);
//		this.add(layoutButton);
//	}
//
//	public void createLoad() {
//		ControlPanelButton layoutButton = new ControlPanelButton("Load", "load", driver);
//		this.add(layoutButton);
//	}
//
//	public void createSave() {
//		ControlPanelButton layoutButton = new ControlPanelButton("Save", "save", driver);
//		this.add(layoutButton);
//	}
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addComponent(Element e) {
		// TODO Auto-generated method stub
		

	}

	@Override
	public void removeComponent(Element e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(ObjectOutputStream op) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Element load(ObjectInputStream ip) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void resetCoor(Coordinate c) {
		// TODO Auto-generated method stub
		for(Element element : elementList) {
			element.resetCoor(c);
		}
	}
	
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		for(Element element : elementList) {
			element.reset();
		}
	}
}
