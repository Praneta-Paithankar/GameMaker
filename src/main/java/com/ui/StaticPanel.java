/**
 *This class contains timer and control panel*/
package com.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JLabel;

import org.apache.log4j.Logger;

import com.behavior.BoxLayoutXAxisBehavior;
import com.behavior.BoxLayoutYAxisBehavior;
import com.behavior.GridBagLayoutBehavior;
import com.component.Clock;
import com.controller.GameController;
import com.infrastruture.AbstractPanel;
import com.infrastruture.Constants;
import com.infrastruture.Element;


@SuppressWarnings("serial")
public class StaticPanel extends AbstractPanel implements Element{
	protected static Logger log = Logger.getLogger(StaticPanel.class);
	private JLabel score;
	private GameController driver;
	private ArrayList<Element> elements;
	
	public StaticPanel() {
		setBorder(BorderFactory.createLoweredBevelBorder());
		setLayoutBehavior(new BoxLayoutYAxisBehavior());
		performUpdateLayout(this, Constants.TIMER_PANEL_WIDTH,Constants.TIMER_PANEL_HEIGHT);
        elements = new ArrayList<>();
	}
	public ArrayList<Element> getElements(){
		return elements;
	}
	
	public void createButtons(GameController driver)
	{
		this.driver = driver;
	    createReplay();
	    createUndo();
	    createStart();
	    createPause();
	    createSave();
	    createLoad();
	    createLayout();
	}
	
	
	public void createReplay() {
		JButton replayButton = new JButton("Replay");
		replayButton.setActionCommand("replay");
		replayButton.addActionListener(driver);
		replayButton.setVisible(true);
		replayButton.setAlignmentX(CENTER_ALIGNMENT);
		replayButton.setAlignmentY(CENTER_ALIGNMENT);

		this.add(Box.createRigidArea(new Dimension(30,30)));
		this.add(replayButton);
		this.add(Box.createRigidArea(new Dimension(5,5)));
	}
	
	public void createUndo() {
		JButton undoButton = new JButton("Undo");
		undoButton.setActionCommand("undo");
		undoButton.addActionListener(driver);
		undoButton.setVisible(true);
		undoButton.setAlignmentX(CENTER_ALIGNMENT);
		undoButton.setAlignmentY(CENTER_ALIGNMENT);

		this.add(Box.createRigidArea(new Dimension(5,5)));
		this.add(undoButton);
		this.add(Box.createRigidArea(new Dimension(5,5)));
	}
	
	public void createStart() {
		JButton startButton = new JButton("Start");
     	startButton.setActionCommand("start");
     	startButton.addActionListener(driver);
		startButton.setVisible(true);
		startButton.setAlignmentX(CENTER_ALIGNMENT);
		startButton.setAlignmentY(CENTER_ALIGNMENT);

		this.add(Box.createRigidArea(new Dimension(5,5)));
		this.add(startButton);
		this.add(Box.createRigidArea(new Dimension(5,5)));
	}
	
	public void createPause() {
		JButton startButton = new JButton("Pause");
     	startButton.setActionCommand("pause");
     	startButton.addActionListener(driver);
		startButton.setVisible(true);
		startButton.setAlignmentX(CENTER_ALIGNMENT);
		startButton.setAlignmentY(CENTER_ALIGNMENT);
		
		this.add(Box.createRigidArea(new Dimension(5,5)));
		this.add(startButton);
		this.add(Box.createRigidArea(new Dimension(5,5)));
	}
	
	public void createSave() {
		JButton saveButton = new JButton("Save");
		saveButton.setActionCommand("save");
		saveButton.addActionListener(driver);
		saveButton.setVisible(true);
		this.add(saveButton);
	}
	
	public void createLoad() {
		JButton loadButton = new JButton("Load");
		loadButton.setActionCommand("load");
		loadButton.addActionListener(driver);
		loadButton.setVisible(true);
		this.add(loadButton);
	}

	public void createLayout() {
		JButton layoutButton = new JButton("Layout");
		layoutButton.setActionCommand("layout");
		layoutButton.addActionListener(driver);
		layoutButton.setVisible(true);
		layoutButton.setAlignmentX(CENTER_ALIGNMENT);
		layoutButton.setAlignmentY(CENTER_ALIGNMENT);
		
		this.add(Box.createRigidArea(new Dimension(5,5)));
		this.add(layoutButton);
		this.add(Box.createRigidArea(new Dimension(5,5)));
	}

	
	
	public void addComponent(Element e) {
		this.add((AbstractPanel)e);
		elements.add(e);
	}

	@Override
	public void removeComponent(Element e) {
		elements.remove(e);
	}

	@Override
	public void draw(Graphics g) {

	for(Element component : elements) {
		component.draw(null);
	}
	}
	@Override
	public void reset() {
		for(Element element : elements) {
			element.reset();
		}
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
