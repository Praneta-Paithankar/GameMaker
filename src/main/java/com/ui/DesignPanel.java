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
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;
import javax.swing.border.*;

import javax.swing.JLabel;

import org.apache.log4j.Logger;

import com.behavior.BoxLayoutXAxisBehavior;
import com.behavior.BoxLayoutYAxisBehavior;
import com.behavior.GridBagLayoutBehavior;
import com.components.Clock;
import com.controller.MainController;
import com.ui.AbstractPanel;
import com.infrastruture.Constants;
import com.infrastruture.Element;
import com.dimension.Coordinate;



@SuppressWarnings("serial")
public class DesignPanel extends AbstractPanel implements Element{
	protected static Logger log = Logger.getLogger(DesignPanel.class);
	private JLabel score;
	private MainController driver;
	private ArrayList<Element> elements;
	
	public DesignPanel() {
		setBorder("Design Center"); // Method call for setting the border
		setLayoutBehavior(new BoxLayoutYAxisBehavior());
		setBackground(Color.DARK_GRAY);
		
		JPanel graphic = new JPanel();
		graphic.setBackground(Color.LIGHT_GRAY);
		JPanel control  = new JPanel();
		control.setBackground(Color.LIGHT_GRAY);
		
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Graphic", null, graphic, null);
		tabbedPane.addTab("Control", null, control, null);
		tabbedPane.setSize(new Dimension(100,100));
		this.add(tabbedPane);
		
		JPanel preview = new JPanel();
		Border redline = BorderFactory.createLineBorder(Color.red);
		preview.setBorder(redline);
		preview.setPreferredSize(new Dimension(400, 40));
		
		this.add(preview);
		performUpdateLayout(this, Constants.DESIGN_PANEL_WIDTH,Constants.DESIGN_PANEL_HEIGHT);
        elements = new ArrayList<>();
        
	}
	
	public void setBorder(String title) {
		Border blackline = BorderFactory.createLineBorder(Color.black);
		TitledBorder border = BorderFactory.createTitledBorder(
                blackline, title);
	    border.setTitleJustification(TitledBorder.LEFT);
	    border.setTitleColor(Color.white);
	    border.setTitlePosition(TitledBorder.BELOW_TOP);
	    this.setBorder(border);
		
	}
	
	public ArrayList<Element> getElements(){
		return elements;
	}
	
	public void createButtons(MainController driver)
	{
		this.driver = driver;
//	    createReplay();
//	    createUndo();
//	    createStart();
//	    createPause();
//	    createSave();
//	    createLoad();
//	    createLayout();
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
	public void resetCoor(Coordinate c) {
		for(Element element : elements) {
			element.resetCoor(c);
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
