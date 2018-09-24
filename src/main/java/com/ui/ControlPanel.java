/**
 *This class contains all the buttons*/
package com.ui;

import java.awt.Graphics;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import com.behavior.FlowLayoutBehavior;
import com.controller.MainController;
import com.ui.AbstractPanel;
import com.infrastruture.Constants;
import com.infrastruture.Element;

@SuppressWarnings("serial")
public class ControlPanel  extends AbstractPanel implements Element {

	private MainController driver;
	private ArrayList<Element> elementList;
	private ArrayList<CustomButton> buttons;
	private JLabel timer;
	private JLabel scoreBoard;
	
	public ControlPanel() {
		this.elementList = new ArrayList<>();
		setBorder("ControlPanel");
		timer = new JLabel();
		scoreBoard = new JLabel();
		elementList = new ArrayList<>();
		buttons = new ArrayList<>();
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
	}


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

	public ArrayList<Element> getElements() {
		return elementList;
	}

	public void setElements(ArrayList<Element> elementList) {
		this.elementList = elementList;
	}

	@Override
	public void save(ObjectOutputStream op) {
		for (Element element : elementList) {
			element.save(op);
		}
	}
	@Override
	public Element load(ObjectInputStream ip) {
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
		this.removeAll();
		for(Element element : elementList) {
			element.reset();
		}
	}

	public void addButtons(CustomButton button) {
		buttons.add(button);
		this.add(button);
	}
	
	public ArrayList<CustomButton> getButtons() {
		return buttons;
	}

	public void setButtons(ArrayList<CustomButton> buttons) {
		this.buttons = buttons;
	}

	public JLabel getTimer() {
		return timer;
	}

	public void setTimer(JLabel timer) {
		this.timer = timer;
	}
}
