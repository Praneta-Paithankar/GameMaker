/**
 *This class contains two panels*/
package com.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Panel;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import javax.swing.BorderFactory;

import com.behavior.BoxLayoutXAxisBehavior;
import com.behavior.BoxLayoutYAxisBehavior;
import com.infrastruture.AbstractPanel;
import com.infrastruture.Constants;
import com.infrastruture.Element;

@SuppressWarnings("serial")
public class MainPanel extends AbstractPanel implements Element {

	private ArrayList<Element> elements;
	protected static Logger log = Logger.getLogger(MainPanel.class);

	
	public MainPanel() {

        elements = new ArrayList<>();
		setLayoutBehavior(new BoxLayoutXAxisBehavior());
		performUpdateLayout(this, Constants.MAIN_PANEL_WIDTH,Constants.MAIN_PANEL_HEIGHT);
		setFocusable(true);
		requestFocusInWindow();
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		for(Element component : elements)
		{
			component.draw(g);
		}
	}
	
	@Override
	public void draw(Graphics g) {
		repaint();
	}

	@Override
	public void reset() {
		for(Element element : elements) {
			element.reset();
		}
	}

	@Override
	public void addComponent(Element e) {
		elements.add(e);
		this.add((AbstractPanel)e);
	}

	@Override
	public void removeComponent(Element e) {
		elements.remove(e);
	}
	
	@Override
	public void save(ObjectOutputStream op) {
		for (Element element : elements) {
			element.save(op);
		}
	}

	@Override
	public Element load(ObjectInputStream ip) {
		for (Element element : elements) {
			element.load(ip);
		}
		return null;
	}
}
