/**
 *This class contains all the buttons*/
package com.ui;

import java.awt.Graphics;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

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
		this.elementList = new ArrayList<>();
		setBorder("ControlPanel");
		elementList = new ArrayList<>();
		setLayoutBehavior(new FlowLayoutBehavior());
		performUpdateLayout(this, Constants.CONTROL_PANEL_WIDTH,Constants.CONTROL_PANEL_HEIGHT);
	}
	
	public void setBorder(String title) {
		Border raisedbevel = BorderFactory.createRaisedBevelBorder();
		this.setBorder(raisedbevel);
		TitledBorder titledBorder = BorderFactory.createTitledBorder(title);
	    titledBorder.setTitleJustification(TitledBorder.LEFT);
	    
	    titledBorder.setTitlePosition(TitledBorder.BELOW_TOP);
	    Border compound = BorderFactory.createCompoundBorder(
                raisedbevel, titledBorder);
	    this.setBorder(compound);
	}
	
	
	public void createButtons(MainController driver)
	{
		this.driver = driver;
		
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
//		CustomButton replayButton = new CustomButton("Replay", "replay", 50,50,driver);
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
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		for(Element element : elementList)
		{
			element.draw(g);
		}
	}	

	@Override
	public void draw(Graphics g) {
		repaint();
	}

	public void addComponent(Element e) {
		elementList.add(e);
	}

	@Override
	public void removeComponent(Element e) {
		elementList.remove(e);
	}

	@Override
	public void save(ObjectOutputStream op) {
		for (Element element : elementList) {
			element.save(op);
		}
	}
	@Override
	public Element load(ObjectInputStream ip) {
		for (Element element : elementList) {
			element.load(ip);
		}
		ArrayList<Element> loadComponents = new ArrayList<>();
		for (Element element : elementList) {
			loadComponents.add(element.load(ip));
		}
		elementList.clear();
		elementList.addAll(loadComponents);
		return null;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		for(Element element : elementList) {
			element.reset();
		}
	}
}
