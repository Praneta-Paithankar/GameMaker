/**
 *This class contains timer and control panel*/
package com.ui;

import java.awt.BorderLayout;
import com.components.*;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Color;
import com.dimension.*;
import com.helper.ActionType;
import com.helper.ButtonFile;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ItemEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.*;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.border.*;

import javax.swing.JLabel;

import org.apache.log4j.Logger;

import com.behavior.BoxLayoutXAxisBehavior;
import com.behavior.BoxLayoutYAxisBehavior;
import com.behavior.GridBagLayoutBehavior;
import com.behavior.FlowLayoutBehavior;
import com.components.Clock;
import com.controller.MainController;
import com.ui.AbstractPanel;
import com.infrastruture.Constants;
import com.infrastruture.Element;
import com.dimension.*;



@SuppressWarnings("serial")
public class DesignPanel extends AbstractPanel implements DocumentListener , Element, ItemListener, ActionListener {
	protected static Logger log = Logger.getLogger(DesignPanel.class);
	private JLabel score;
	private MainController driver;
	private JTabbedPane tabbedPane;
	private JPanel preview;
	private JPanel graphic;
	private JPanel control;
	private JPanel cards;
	private ArrayList<Element> elements;
	private String [] actionType = ActionType.getActionTypes();
	final static String CIRCLE = "Circle Shape";
    final static String SQUARE = "Square Shape";
    
    //control tag var
	private ButtonFile tendToAddButton;
	private JLabel tendToAddLabel;
	private JPanel buttonBuildPanel;
	private JPanel controlElementPanel;
	
	
	public DesignPanel() {
		setBorder("Design Center"); // Method call for setting the border
		setLayoutBehavior(new FlowLayoutBehavior());
		setBackground(Color.DARK_GRAY);
		
		// Build the Graphic Panel: used to create graphic objects, Control Panel: used to create control elements
		graphic = new JPanel();
		graphic.setBackground(Color.LIGHT_GRAY);
		
		control  = new JPanel();
		control.setBackground(Color.LIGHT_GRAY);
		control.setLayout(new BoxLayout(control,BoxLayout.Y_AXIS));
		
		// Tabbed pane holds the two different interfaces 
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Graphic", null, graphic, null);
		tabbedPane.addTab("Control", null, control, null);
		tabbedPane.setPreferredSize(new Dimension(Constants.DESIGN_PANEL_WIDTH, 500));
		this.add(tabbedPane);
		
		// Create Preview Panel, which show the preview of the element
		preview = new JPanel();
		Border redline = BorderFactory.createLineBorder(Color.red);
		TitledBorder border = BorderFactory.createTitledBorder(
                redline, "Preview Window");
	    border.setTitleJustification(TitledBorder.LEFT);
	    border.setTitlePosition(TitledBorder.BELOW_TOP);preview.setBorder(redline);
	    preview.setBorder(border);
		preview.setPreferredSize(new Dimension(Constants.DESIGN_PANEL_WIDTH, Constants.PREVIEW_PANEL_HEIGHT));
		this.add(preview);
		
		// Update Layout
		performUpdateLayout(this, Constants.DESIGN_PANEL_WIDTH,Constants.DESIGN_PANEL_HEIGHT);
        elements = new ArrayList<>();
        
        // Init creates Add Element button
        init();
	}
	
	
	public void init() {
		// This button adds a new combo box to select basic shape of the 		
		JButton addElementButton = new JButton("Add Element");

		addElementButton.addActionListener(driver);
		addElementButton.setActionCommand("addControlElement");
		addElementButton.setVisible(true);
		addElementButton.setAlignmentX(LEFT_ALIGNMENT);
		addElementButton.setAlignmentY(CENTER_ALIGNMENT);
		graphic.add(addElementButton);
		graphic.add(Box.createRigidArea(new Dimension(5,5)));
		
		
		//control variable
		tendToAddButton = new ButtonFile();
		
		buttonBuildPanel = new JPanel();
		buttonBuildPanel.setAlignmentX(LEFT_ALIGNMENT);
		
		controlElementPanel = new JPanel();
		controlElementPanel.setAlignmentX(LEFT_ALIGNMENT);
		controlElementPanel.setBackground(Color.LIGHT_GRAY);
		controlElementPanel.setLayout(new BoxLayout(controlElementPanel,BoxLayout.Y_AXIS));
		
		
		// for control tab
		JButton addControlElementButton = new JButton("AddControlElement");
		addControlElementButton.addActionListener(this);
		addControlElementButton.setActionCommand("AddControlElement");
		addControlElementButton.setVisible(true);
		addControlElementButton.setAlignmentX(LEFT_ALIGNMENT);
		addControlElementButton.setAlignmentY(BOTTOM_ALIGNMENT);
		controlElementPanel.add(addControlElementButton);
		controlElementPanel.add(Box.createRigidArea(new Dimension(5,5)));
			
		JButton controlElementButton = new JButton("Button");
		controlElementButton.setFont(new Font("Times", Font.PLAIN, 12));
		controlElementButton.addActionListener(this);
		controlElementButton.setActionCommand("ElementButton");
		controlElementButton.setVisible(true);
		controlElementButton.setAlignmentX(LEFT_ALIGNMENT);
		controlElementButton.setAlignmentY(BOTTOM_ALIGNMENT);
		controlElementPanel.add(controlElementButton);
		controlElementPanel.add(Box.createRigidArea(new Dimension(5,5)));
		
		JButton controlElementLabel = new JButton("Label");
		controlElementLabel.setFont(new Font("Times", Font.PLAIN, 12));
		controlElementLabel.addActionListener(this);
		controlElementLabel.setActionCommand("ElementLabel");
		controlElementLabel.setVisible(true);
		controlElementLabel.setAlignmentX(LEFT_ALIGNMENT);
		controlElementLabel.setAlignmentY(BOTTOM_ALIGNMENT);
		controlElementPanel.add(controlElementLabel);
		controlElementPanel.add(Box.createRigidArea(new Dimension(5,5)));
		
		control.add(controlElementPanel);
	}
	
	public ArrayList<Element> getElements(){
		return elements;
	}
	
	public void controlElementButtonSelect() {  
		preview.removeAll();
		refresh(preview);
		control.remove(buttonBuildPanel);
		buttonBuildPanel.removeAll();
		
		JLabel buttonNameLable = new JLabel("Button Name : ");
		JTextField buttonName = new JTextField("", 15);
		buttonName.setName("buttonNameField");
		buttonName.getDocument().addDocumentListener(this);
		buttonName.getDocument().putProperty("owner", buttonName);
		
		JLabel buttonWidthLable = new JLabel("Button Width : ");
		JTextField buttonWidth = new JTextField("", 15);
		buttonWidth.setName("buttonWidthField");
		buttonWidth.getDocument().addDocumentListener(this);
		buttonWidth.getDocument().putProperty("owner", buttonWidth);
		
		JLabel buttonHeightLable = new JLabel("Button Height : ");
		JTextField buttonHeight = new JTextField("", 15);
		buttonHeight.setName("buttonHeightField");
		buttonHeight.getDocument().addDocumentListener(this);
		buttonHeight.getDocument().putProperty("owner", buttonHeight);
		
		JLabel buttonActionLable = new JLabel("Button Action : ");
		JComboBox boxAction = new JComboBox(this.actionType);
		boxAction.setName("boxAction");
		boxAction.setActionCommand("boxActionChanged");
		boxAction.addActionListener(this);

		
		buttonBuildPanel.add(buttonNameLable);
		buttonBuildPanel.add(buttonName);
		buttonBuildPanel.add(buttonHeightLable);
		buttonBuildPanel.add(buttonHeight);
		buttonBuildPanel.add(buttonWidthLable);
		buttonBuildPanel.add(buttonWidth);
		buttonBuildPanel.add(buttonActionLable);
		buttonBuildPanel.add(boxAction);
		control.add(buttonBuildPanel);
		tendToAddButton.setAlignmentY(CENTER_ALIGNMENT);
		preview.add(tendToAddButton);
		this.validate();
	}
	
	public void addElementSelect() {
		//Where the components controlled by the CardLayout are initialized:
		
		 //Create the "cards".
       JPanel card1 = new JPanel();
       card1.add(new JButton("Button 1"));
       card1.add(new JButton("Button 2"));
       card1.add(new JButton("Button 3"));
		JPanel card2 = new JPanel();
		card2.add(new JTextField("TextField", 20));

		//Create the pael that contains the "cards".
		cards = new JPanel(new CardLayout());
		
		cards.setPreferredSize(new Dimension(250,200));
		cards.add(card1, CIRCLE);
       cards.add(card2, SQUARE);
		//Where the GUI is assembled:
		//Put the JComboBox in a JPanel to get a nicer look.
		JPanel comboBoxPane = new JPanel(); //use FlowLayout
		String comboBoxItems[] = { CIRCLE, SQUARE };
		JComboBox cb = new JComboBox(comboBoxItems);
		cb.setEditable(false);
		cb.addItemListener(this);
		comboBoxPane.add(cb);
		graphic.add(comboBoxPane, BorderLayout.PAGE_START);
		graphic.add(cards,  BorderLayout.CENTER);
		this.validate();
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
	public void itemStateChanged(ItemEvent evt) {
	    CardLayout cl = (CardLayout)(cards.getLayout());
	    cl.show(cards, (String)evt.getItem());
	    if(evt.getItem() == CIRCLE) {
	    	//elements.add(new GameElement(new Dimensions(50,50), new Coordinate(30,30), new Coordinate(30,30)));
	    	System.out.println(elements);
	    }
	    this.revalidate();
	    this.repaint();
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals("addElement")) {
			this.addElementSelect();
		}
		if (e.getActionCommand().equals("ElementButton")) {
			this.controlElementButtonSelect();
		}
		if(e.getActionCommand().equals("boxActionChanged")) {
			JComboBox boxAction = (JComboBox)e.getSource();
			tendToAddButton.setActionType(ActionType.valueOf(boxAction.getSelectedItem().toString()));
			//System.out.println(tendToAddButton.getActionType().toString());
		}
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		//System.out.println("changed");
	}
	
	@Override
	public void removeUpdate(DocumentEvent e) {
		documentExeute(e);
	}
	@Override
	public void insertUpdate(DocumentEvent e) {
		documentExeute(e);
	}
	
	public void documentExeute(DocumentEvent e) {
		JComponent owner = (JComponent)e.getDocument().getProperty("owner");
		Document d = e.getDocument();
		String content = "";
		try {
			content = d.getText(0, d.getLength());
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(owner.getName().equals("buttonNameField")) {
				tendToAddButton.setText(content);
		}
		if(owner.getName().equals("buttonHeightField")) {
				int height = 0;
		        try 
		        { 
		            // checking valid integer using parseInt() method 
					if(content.equals("")) {
						height = 0;
					}
					else{
						height = Integer.parseInt(content);
					} 
		        }  
		        catch (NumberFormatException notNumE)  
		        { 
		            System.out.println(" is not a valid integer number"); 
		        } 

				Dimension dim = tendToAddButton.getPreferredSize();
				tendToAddButton.setPreferredSize(new Dimension( (int)tendToAddButton.getPreferredSize().getWidth() , height));
				tendToAddButton.revalidate();
		}
		if(owner.getName().equals("buttonWidthField")) {
			int width;
			if(content.equals("")) {
				width = 0;
			}
			else{
				width = Integer.parseInt(content);
			}
			tendToAddButton.setPreferredSize(new Dimension(width , (int)tendToAddButton.getPreferredSize().getHeight()));
			tendToAddButton.revalidate();
		}
		this.validate();
	}
	
	public void refresh(JComponent j) {
		j.revalidate();
		j.repaint();
		
	}
}
