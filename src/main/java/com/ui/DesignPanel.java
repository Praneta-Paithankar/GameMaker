/**
 *This class contains timer and control panel*/
package com.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import org.apache.log4j.Logger;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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
import com.strategy.DrawOvalImage;
import com.strategy.DrawRectangularColorShape;
import com.strategy.DrawRectangularImage;



@SuppressWarnings("serial")
public class DesignPanel extends AbstractPanel implements DocumentListener , Element, ItemListener, ActionListener {
	protected static Logger log = Logger.getLogger(DesignPanel.class);
	private JLabel score;
	private MainController driver;
	private JTabbedPane tabbedPane;
	private PreviewPanel preview;
	private DesignController designController;
	private JScrollPane scroller;
	private JPanel graphic;
	private JPanel control;
	private JPanel cards;
	private JPanel comboBoxPane;
	private JFileChooser jFileChooser;
	private boolean finished;
	private JFrame frame;
	private ArrayList<Element> elements;
	final static String CIRCLE = "Circle Shape";
    final static String RECTANGLE = "Rectangle Shape";
    private DesignPanel that = this;
    private String type;
    //control tag var
    
	private CustomButton tendToAddButton;
	private JLabel tendToAddLabel;
	private JPanel buttonBuildPanel;
	private JPanel controlElementPanel;
	private JButton addGraphicElementButton;
	private JButton finishedButton;
	private boolean pushedElement;
	
	public DesignPanel() {
		setBorder("Design Center"); // Method call for setting the border
		setLayoutBehavior(new FlowLayoutBehavior());
		setBackground(Color.DARK_GRAY);
		this.type = "Oval";
		this.finished = true;
		// Build the Graphic Panel: used to create graphic objects, Control Panel: used to create control elements
		graphic = new JPanel();
		scroller = new JScrollPane(graphic,
	            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
	            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		graphic.setBackground(Color.LIGHT_GRAY);
		graphic.setLayout(new BoxLayout(graphic, BoxLayout.Y_AXIS));
		
		
		control  = new JPanel();
		control.setBackground(Color.LIGHT_GRAY);
		control.setLayout(new BoxLayout(control,BoxLayout.Y_AXIS));
		
		// Tabbed pane holds the two different interfaces 

		tabbedPane = new JTabbedPane(); 
		tabbedPane.addTab("Graphic", null, scroller, null);

//		
//		tabbedPane = new JTabbedPane();
//		tabbedPane.addTab("Graphic", null, graphic, null);

		tabbedPane.addTab("Control", null, control, null);
		tabbedPane.setPreferredSize(new Dimension(Constants.DESIGN_PANEL_WIDTH, 500));
		this.add(tabbedPane);
		
		// Create Preview Panel, which show the preview of the element
		preview = new PreviewPanel();
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
        init(null);
	}
	
	
	public void init(GameElement gameElement) {
		this.finished = gameElement== null;
		this.pushedElement = gameElement!= null;
		// This button adds a new combo box to select basic shape of the 	
		//for graphic tab
		JPanel baseButtons = new JPanel(new FlowLayout());
		addGraphicElementButton = new JButton("Add Element");
		addGraphicElementButton.setEnabled(this.finished);
		finishedButton = new JButton("Finished");
		finishedButton.setEnabled(!this.finished);
		
		
		finishedButton.addActionListener(this);
		finishedButton.setActionCommand("finishedElement");
		
		//graphic.add(Box.createRigidArea(new Dimension(5,5)));
		
		addGraphicElementButton.addActionListener(this);
		addGraphicElementButton.setActionCommand("addElement");
		addGraphicElementButton.setVisible(true);
		baseButtons.add(addGraphicElementButton);
		baseButtons.add(finishedButton);
		graphic.add(baseButtons);
		
		
		//control variable
		tendToAddButton = new CustomButton();
		
		buttonBuildPanel = new JPanel();
		buttonBuildPanel.setAlignmentX(LEFT_ALIGNMENT);
		
		controlElementPanel = new JPanel();
		controlElementPanel.setAlignmentX(LEFT_ALIGNMENT);
		controlElementPanel.setBackground(Color.LIGHT_GRAY);
		controlElementPanel.setLayout(new BoxLayout(controlElementPanel,BoxLayout.Y_AXIS));
		
		
		// for control tab			
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
		if(gameElement!= null) {
			this.addElementSelect(gameElement);
		}
	}
	
	public ArrayList<Element> getElements(){
		return elements;
	}
	
	public void controlElementButtonSelect() {  
		preview.removeAll();
		refresh(preview);
		control.remove(buttonBuildPanel);
		buttonBuildPanel.removeAll();
		ActionType action = ActionType.SAVE;
		tendToAddButton.setActionType(action);
		JLabel buttonNameLabel = new JLabel("Button Name : ");
		JTextField buttonName = new JTextField("", 15);
		buttonName.setName("buttonNameField");
		buttonName.getDocument().addDocumentListener(this);
		buttonName.getDocument().putProperty("owner", buttonName);
		
		JLabel buttonWidthLabel = new JLabel("Button Width : ");
		JTextField buttonWidth = new JTextField("", 15);
		buttonWidth.setName("buttonWidthField");
		buttonWidth.getDocument().addDocumentListener(this);
		buttonWidth.getDocument().putProperty("owner", buttonWidth);
		
		JLabel buttonHeightLabel = new JLabel("Button Height : ");
		JTextField buttonHeight = new JTextField("", 15);
		buttonHeight.setName("buttonHeightField");
		buttonHeight.getDocument().addDocumentListener(this);
		buttonHeight.getDocument().putProperty("owner", buttonHeight);
		
		JLabel buttonActionLabel = new JLabel("Button Action : ");
		JComboBox boxAction = new JComboBox(ActionType.values());
		boxAction.setName("boxAction");
		boxAction.setActionCommand("boxActionChanged");
		boxAction.addActionListener(this);

		
		buttonBuildPanel.add(buttonNameLabel);
		buttonBuildPanel.add(buttonName);
		buttonBuildPanel.add(buttonHeightLabel);
		buttonBuildPanel.add(buttonHeight);
		buttonBuildPanel.add(buttonWidthLabel);
		buttonBuildPanel.add(buttonWidth);
		buttonBuildPanel.add(buttonActionLabel);
		buttonBuildPanel.add(boxAction);
		control.add(buttonBuildPanel);
		tendToAddButton.setAlignmentY(CENTER_ALIGNMENT);
		preview.add(tendToAddButton);
		this.validate();
	}
	
	// This creates the separate views for circle and retangle
	public void addElementSelect(GameElement gameElement) {
		int index = 0;
		int xPos = 100;
		int yPos = 100;
		int width = 200;
		int height = 200;
		// Initial Shape
		GameElement g = new GameElement(new Dimensions(Constants.PREVIEW_RADIUS), new Coordinate(Constants.PREVIEW_X_START, Constants.PREVIEW_Y_START), "null", MoveType.LEFTRIGHT,20,0,"Oval");
        g.setColor(Color.BLACK);
        g.setDraw(new DrawOvalColor());
        g.setVisible(true);
		if(gameElement != null) {
			index = gameElement.getShapeType().equals("Oval") ? 0 : 1;
			g = gameElement;
		}
		
		//Where the components controlled by the CardLayout are initialized:
		this.finished = false; // Prevents user adding another element until finished
		this.addGraphicElementButton.setEnabled(this.finished);
		this.finishedButton.setEnabled(!this.finished);
		//Create the "cards".
		JPanel circleCard = new JPanel();
		createCircleCard(circleCard, gameElement);
		
		JPanel rectangleCard = new JPanel();
		createRectangleCard(rectangleCard, g);
		
		//Create the panel that contains the "cards".
		cards = new JPanel(new CardLayout());
		
		cards.setPreferredSize(new Dimension(250,200));
		cards.add(circleCard, CIRCLE);
		cards.add(rectangleCard, RECTANGLE);

		//Where the GUI is assembled:
		//Put the JComboBox in a JPanel to get a nicer look.
		comboBoxPane = new JPanel(); //use FlowLayout
		String comboBoxItems[] = { CIRCLE, RECTANGLE };
		JComboBox cb = new JComboBox(comboBoxItems);
		cb.setEditable(false);
		cb.addItemListener(this);
		cb.setSelectedIndex(index);
		comboBoxPane.add(cb);
		graphic.add(comboBoxPane, BorderLayout.PAGE_START);
		graphic.add(cards,  BorderLayout.CENTER);
		this.validate();
		
		
        this.preview.addGameElement(g);
	}
	
	// This creates the part of the design panel responsible for all other properties
	private void createGraphicCard(JPanel card, GameElement gameElement ) {
		String xLabel = "0";
		String yLabel = "0";
		int moveIndex = 0;
		if(this.pushedElement) {
			xLabel = String.valueOf(gameElement.getX());
			yLabel = String.valueOf(gameElement.getY());
			ArrayList<MoveType> temp = new ArrayList<>(Arrays.asList(MoveType.values()));
			moveIndex = temp.indexOf(gameElement.getMoveType());
		}
		card.add(new JLabel("X-Position: ", JLabel.LEFT));
		final JTextField xCoor = new JTextField(xLabel, 4);
		card.add(xCoor);
		card.add(new JLabel("Y-Position: ", JLabel.LEFT));
		final JTextField yCoor = new JTextField(yLabel, 4);
		card.add(yCoor);
		// Used for updating the coordinates
		xCoor.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
               
            }

            @Override
            public void focusLost(FocusEvent e) {
            	int tempX = Integer.parseInt(xCoor.getText());
            	GameElement tempElement =(GameElement) that.preview.getElements().get(0);
            	int tempY = tempElement.getActualCoordinate().getY();
            	tempElement.setActualCoordinate(new Coordinate(tempX, tempY));
            	that.preview.addGameElement(tempElement);
            }
        });
		
		yCoor.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
               
            }

            @Override
            public void focusLost(FocusEvent e) {
            	int tempX = Integer.parseInt(xCoor.getText());
            	GameElement tempElement =(GameElement) that.preview.getElements().get(0);
            	int tempY = tempElement.getActualCoordinate().getY();
            	tempElement.setActualCoordinate(new Coordinate(tempX, tempY));
            	that.preview.addGameElement(tempElement);	
            }
        });
		
		
		JButton btn1 = new JButton("Choose Color");
	
		card.add(btn1);
	
		btn1.setAlignmentY(BOTTOM_ALIGNMENT);
		
        btn1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	GameElement tempElement =(GameElement) that.preview.getElements().get(0);
                Color newColor = JColorChooser.showDialog(
                     frame,
                     "Choose Color",
                     frame.getBackground());
                if(newColor != null){
                    tempElement.setColor(newColor);
                    that.preview.addGameElement(tempElement);
                }
                
            }
        });
        
        JButton btnImage = new JButton("Choose Image");
        card.add(btnImage);
  
        btnImage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                JFileChooser fc = new JFileChooser();
                BufferedImage img = null;
                int result = fc.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    String sname = file.getAbsolutePath(); //THIS WAS THE PROBLEM
                    try {
	                    img = ImageIO.read(new File(sname));
	                    GameElement tempElement =(GameElement) that.preview.getElements().get(0);
	                    if(that.type.equals("Oval")) {
	                    	tempElement.setDraw(new DrawOvalImage());
	                    } else {
	                    	tempElement.setDraw(new DrawRectangularImage());
	                    }
	                    tempElement.setImage(img);
	                    that.preview.addGameElement(tempElement);
	                    System.out.println(img);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        card.add(new JLabel("Movement Type: ", JLabel.LEFT));
        JPanel comboBoxPane2 = new JPanel(); //use FlowLayout
        MoveType comboBoxItems[] = MoveType.values();
		JComboBox moveTypeBox = new JComboBox(comboBoxItems);
		moveTypeBox.setActionCommand("moveTypeChanged");
		moveTypeBox.setSelectedIndex(moveIndex);
		comboBoxPane2.add(moveTypeBox);
		card.add(comboBoxPane2);
        this.revalidate();
	    this.repaint();
	}
	// This creates the part of the design panel responsible for rectangle only properties
	private void createRectangleCard(JPanel card, GameElement gameElement ) {
		String nameLabel = "Rectangle"+elements.size();
		String widthLabel = "100";
		String heightLabel = "100";
		String xLabel = "0";
		String yLabel = "0";
		if(this.pushedElement) {
			nameLabel = gameElement.getName();
			widthLabel = String.valueOf(gameElement.getWidth());
			heightLabel = String.valueOf(gameElement.getHeight());
		}
		
		// TODO Auto-generated method stub
		card.add(new JLabel("Object Name: ", JLabel.LEFT));
		final JTextField name = new JTextField(nameLabel, 20);
		card.add(name);
		card.add(new JLabel("               "));
		card.add(new JLabel("Width: ", JLabel.LEFT));
		final JTextField width = new JTextField(widthLabel, 4);
		card.add(width);
		card.add(new JLabel("Height: ", JLabel.LEFT));
		final JTextField height = new JTextField(heightLabel, 4);
		card.add(height);
		// Used for updating the coordinates
		name.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
               
            }

            @Override
            public void focusLost(FocusEvent e) {
            	String tempName = name.getText();
            	GameElement tempElement =(GameElement) that.preview.getElements().get(0);
            	
            	tempElement.setName(tempName);
            	that.preview.addGameElement(tempElement);
            }
        });
		
		width.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
               
            }

            @Override
            public void focusLost(FocusEvent e) {
            	int tempWidth = Integer.parseInt(width.getText());
            	GameElement tempElement =(GameElement) that.preview.getElements().get(0);
            	int tempHeight = tempElement.getActualDimensions().getHeight();
            	tempElement.setActualDimension(new Dimensions(tempWidth, tempHeight),"Rectangle");
            	that.preview.addGameElement(tempElement);
            }
        });
		
		height.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
               
            }

            @Override
            public void focusLost(FocusEvent e) {
            	int tempHeight = Integer.parseInt(height.getText());
            	GameElement tempElement =(GameElement) that.preview.getElements().get(0);
            	int tempWidth = tempElement.getActualDimensions().getWidth();
            	tempElement.setActualDimension(new Dimensions(tempWidth, tempHeight),"Rectangle");
            	that.preview.addGameElement(tempElement);
            }
        });
		createGraphicCard(card, gameElement);
	}
	// This creates the part of the design panel responsible for circle only properties 
	private void createCircleCard(JPanel card, GameElement gameElement ) {
		// TODO Auto-generated method stub
		String nameLabel = "Circle"+elements.size();
		String radiusLabel = "100";
		if(this.pushedElement) {
			nameLabel = gameElement.getName();
			radiusLabel = String.valueOf(gameElement.getWidth()/2);
			
		}
		card.add(new JLabel("Object Name: ", JLabel.LEFT));
		final JTextField name = new JTextField(nameLabel, 20);
		card.add(name);
		card.add(new JLabel("Radius: ", JLabel.LEFT));
		final JTextField radius = new JTextField(radiusLabel, 20);
		radius.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
               
            }

            @Override
            public void focusLost(FocusEvent e) {
            	GameElement tempElement =(GameElement) that.preview.getElements().get(0);
            	tempElement.setActualDimension(new Dimensions(Integer.parseInt(radius.getText())), "Oval");
            	that.preview.addGameElement(tempElement);
            }
        });
		
		name.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
               
            }

            @Override
            public void focusLost(FocusEvent e) {
            	String tempName = name.getText();
            	GameElement tempElement =(GameElement) that.preview.getElements().get(0);
            	
            	tempElement.setName(tempName);
            	that.preview.addGameElement(tempElement);
            }
        });
	
		card.add(radius);
		
		createGraphicCard(card, gameElement);
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
	public void createButtons(MainController driver)
	{
		this.driver = driver;
		//get driver , add buttons for adding control element
		JButton addControlElementButton = new JButton("AddControlElement");
		addControlElementButton.addActionListener(driver);
		addControlElementButton.setActionCommand("AddControlElement");
		addControlElementButton.setVisible(true);
		addControlElementButton.setAlignmentX(LEFT_ALIGNMENT);
		addControlElementButton.setAlignmentY(BOTTOM_ALIGNMENT);
		controlElementPanel.add(addControlElementButton);
		controlElementPanel.add(Box.createRigidArea(new Dimension(5,5)));
//	    createReplay();
//	    createUndo();
//	    createStart();
//	    createPause();
//	    createSave();
//	    createLoad();
//	    createLayout();
	}
	

	// This changes the card shown for creating circle vs rectangle and changes preview window
	public void itemStateChanged(ItemEvent evt) {
	    
	    System.out.println(evt.getItem());
	    if(evt.getItem() == CIRCLE) {
	    	CardLayout cl = (CardLayout)(cards.getLayout());
		    cl.show(cards, (String)evt.getItem());
	    	this.preview.setElements(new ArrayList<Element>());
	    	GameElement g = new GameElement(new Dimensions(Constants.PREVIEW_RADIUS), new Coordinate(Constants.PREVIEW_X_START, Constants.PREVIEW_Y_START), "New", MoveType.LEFTRIGHT,20,0,"Oval");
	        g.setColor(Color.BLACK);
	        g.setDraw(new DrawOvalColor());
	        g.setVisible(true);
	        this.preview.addGameElement(g);
	        this.type = "Oval";
	    	
	    } else if(evt.getItem() == RECTANGLE) {
	    	CardLayout cl = (CardLayout)(cards.getLayout());
		    cl.show(cards, (String)evt.getItem());
	    	this.preview.setElements(new ArrayList<Element>());
	    	GameElement g = new GameElement(new Dimensions(Constants.PREVIEW_RADIUS*2, Constants.PREVIEW_RADIUS*2), new Coordinate(Constants.PREVIEW_X_START, Constants.PREVIEW_Y_START), "New", MoveType.LEFTRIGHT,20,0,"Rectangle");
	        g.setColor(Color.BLACK);
	        g.setDraw(new DrawRectangularColorShape());
	        g.setVisible(true);
	        this.preview.addGameElement(g);
	        this.type = "Rectangle";
	    } 
	    this.revalidate();
	    this.repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals("addElement")) {
			this.addElementSelect(null);
		}
		if (e.getActionCommand().equals("ElementButton")) {
			this.controlElementButtonSelect();
		}
		if (e.getActionCommand().equals("finishedElement")) {
			this.graphicElementFinished();
		}
		if(e.getActionCommand().equals("boxActionChanged")) {
			JComboBox boxAction = (JComboBox) e.getSource();
			tendToAddButton.setActionType(ActionType.valueOf(boxAction.getSelectedItem().toString()));
		}
		if(e.getActionCommand().equals("moveTypeChanged")) {
			JComboBox boxAction = (JComboBox) e.getSource();
			GameElement tempElement =(GameElement) that.preview.getElements().get(0);
			tempElement.setMoveType((MoveType)boxAction.getSelectedItem());
		}
		
	}

	
	private void graphicElementFinished() {
		this.finished = true;
		GameElement temp = (GameElement)this.preview.getElements().get(0);
		temp.pushToBoard();
		designController.addGameElement(temp);
		try {
			this.graphic.remove(cards);
			this.graphic.remove(comboBoxPane);
		} catch(Exception e) {
			// Do nothing
		}
		
		this.preview.setElements(new ArrayList<Element>());
		this.addGraphicElementButton.setEnabled(this.finished);
		this.finishedButton.setEnabled(!this.finished);
	}

	

	public void pushToPreview(GameElement temp) {
		try {
			this.graphic.removeAll();
		} catch(Exception e) {
			System.out.println("No cards/combo to remove");
		}
		this.preview.addGameElement(temp);
		init(temp);
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

	public JComponent getControlElement() {
		return tendToAddButton;
	}
	
	
	
	public void addComponent(Element e) {
		elements.add(e);
	}

	@Override
	public void removeComponent(Element e) {
		elements.remove(e);
	}

	public JPanel getPreview() {
		return this.preview;
	}
	
	public void setController(DesignController controller) {
		this.designController = controller;
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
		//repaint();
	}
	@Override
	public void reset() {
		for(Element element : elements) {
			element.reset();
		}
	}

	@Override
	public void save(ObjectOutputStream op) {
//		for (Element element : elements) {
//			element.save(op);
//		}
		
	}
	@Override
	public Element load(ObjectInputStream ip) {
//		for (Element element : elements) {
//			element.load(ip);
//		}
		return null;
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

				tendToAddButton.setPreferredSize(new Dimension( (int)tendToAddButton.getPreferredSize().getWidth() , height));
				tendToAddButton.revalidate();
		}
		if(owner.getName().equals("buttonWidthField")) {
			int width = 0;
	        try 
	        { 
	            // checking valid integer using parseInt() method 
				if(content.equals("")) {
					width = 0;
				}
				else{
					width = Integer.parseInt(content);
				} 
	        }  
	        catch (NumberFormatException notNumE)  
	        { 
	            System.out.println(" is not a valid integer number"); 
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
