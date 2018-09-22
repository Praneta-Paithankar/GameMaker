package com.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.JButton;

import org.apache.log4j.Logger;

import com.components.Clock;
import com.controller.MainController;
import com.infrastruture.*;
import com.infrastruture.Element;

public class CustomButton extends JButton implements Element,Serializable{

	protected static Logger log = Logger.getLogger(Clock.class);
	ActionType actionType;
	public CustomButton() {
		
	}
	public CustomButton(String name, String command, int width, int height, MainController controller) {
		setText(name);
		setActionCommand(command);
		addActionListener(controller);
		setVisible(true);
		setAlignmentX(CENTER_ALIGNMENT);
		setAlignmentY(CENTER_ALIGNMENT);
		setMinimumSize(new Dimension(width, height));
		setPreferredSize(new Dimension(width, height));
		setMaximumSize(new Dimension(width, height));
	}
	public ActionType getActionType() {
		return actionType;
	}

	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}
	
	@Override
	public void draw(Graphics g) {
		setVisible(true);
		
	}
	@Override
	public void reset() {
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
		try {
			op.writeObject(this);
		} catch (IOException e) {
			log.error(e.getLocalizedMessage());
		}
	}

	@Override
	public Element load(ObjectInputStream ip){
		try {
			CustomButton obj = (CustomButton)ip.readObject();
			return obj;
		} catch (ClassNotFoundException | IOException e) {
			log.error(e.getMessage());
		}
		return null;
	}
}
