package com.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import com.components.GameElement;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.*;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import org.apache.log4j.Logger;

import com.behavior.FlowLayoutBehavior;
import com.components.GameElement;
import com.controller.DesignController;
import com.controller.MainController;
import com.dimension.Coordinate;
import com.dimension.Dimensions;
//import com.helper.ActionType;
import com.infrastruture.ActionType;
import com.infrastruture.Constants;
import com.infrastruture.Element;
import com.infrastruture.MoveType;
import com.strategy.DrawOvalColor;



@SuppressWarnings("serial")
public class PreviewPanel extends AbstractPanel implements DocumentListener , Element, ItemListener, ActionListener {
	protected static Logger log = Logger.getLogger(DesignPanel.class);
	private JLabel score;
	private MainController driver;
	private JTabbedPane tabbedPane;
	private JPanel preview;
	private DesignController designController;
	private JScrollPane scroller;
	private JPanel graphic;
	private JPanel control;
	private JPanel cards;
	private JButton addGraphicElementButton;
	private boolean finished;
	private ArrayList<Element> elements;
	final static String CIRCLE = "Circle Shape";
    final static String SQUARE = "Square Shape";
    
    //control tag var
    
	private CustomButton tendToAddButton;
	private JLabel tendToAddLabel;
	private JPanel buttonBuildPanel;
	private JPanel controlElementPanel;
	
	
	public PreviewPanel() {
		 elements = new ArrayList<>();
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void itemStateChanged(ItemEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		for(Element element : elements)
		{
			element.draw(g);
		}
	}

	@Override
	public void draw(Graphics g) {
		repaint();
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
	public void changedUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void insertUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void removeUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	public void addGameElement(GameElement g) {
		// TODO Auto-generated method stub
		if(!elements.isEmpty()) {
			GameElement temp = (GameElement) elements.get(0);
			temp.pushToBoard();
		}
		this.elements = new ArrayList<Element>();
		this.elements.add(g);
		this.revalidate();
		this.repaint();
	}
	public void setElements(ArrayList<Element> a) {
		this.elements = a;
	}
	public ArrayList<Element> getElements() {
		return this.elements;
	}


	@Override
	public void addComponent(Element e) {
		// TODO Auto-generated method stub
		
	}



}

