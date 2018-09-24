/**
 *This class contains timer and control panel*/
package com.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
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
import java.util.List;

import com.infrastruture.EventType;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import org.apache.log4j.Logger;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import com.behavior.FlowLayoutBehavior;
import com.commands.GameOverEvent;
import com.commands.ScoreEvent;
import com.commands.SoundEvent;
import com.components.GameElement;
import com.controller.DesignController;
import com.controller.MainController;
import com.dimension.Coordinate;
import com.dimension.Dimensions;
import com.helper.Collider;
import com.helper.CollisionChecker;
//import com.helper.ActionType;
import com.infrastruture.ActionType;
import com.infrastruture.CollisionType;
import com.infrastruture.Command;
import com.infrastruture.Constants;
import com.infrastruture.Element;
import com.infrastruture.ElementListener;
import com.infrastruture.MoveType;
import com.strategy.DrawOvalColor;
import com.strategy.DrawOvalImage;
import com.strategy.DrawRectangularColorShape;
import com.strategy.DrawRectangularImage;
import com.ui.EndingConditions;
import com.helper.Collider;


@SuppressWarnings("serial")
public class DesignPanel extends AbstractPanel implements DocumentListener , Element, ItemListener, ActionListener {
	protected static Logger log = Logger.getLogger(DesignPanel.class);
	private JLabel score;
	private MainController driver;
	private JTabbedPane tabbedPane;
	private PreviewPanel preview;
	private DesignController designController;
	private JScrollPane scroller;
	private JScrollPane scroller2;
	private JPanel graphic;
	private JPanel control;
	private JPanel timeLine;
	private JPanel collider;
	private JPanel cards;
	private boolean firstTime;
	private JPanel comboBoxPane;
	private JFileChooser jFileChooser;
	private boolean finished;
	private JFrame frame;
	private ArrayList<Element> elements;
	private ArrayList<Collider> colliders;

	final static String CIRCLE = "Circle Shape";
    final static String RECTANGLE = "Rectangle Shape";
    private DesignPanel that = this;
    private String type;
    private MoveType moveState;
    
    //control tag var
    private JPanel designCard;
	private CustomButton tendToAddButton;
	private JLabel tendToAddLabel;
	
	//timeline tag
	JButton endingConfirm;
	
	private JPanel buttonBuildPanel;
	private JPanel controlElementPanel;
	private EndingConditions end;
	private JButton addGraphicElementButton;
	private JButton addColliderButton;
	private JButton finishedButton;
	private boolean pushedElement;
	private JTextField xVel;
	private JTextField yVel;
	private JTextField xVel2;
	private JTextField yVel2;
	private int eventListIndex;
	JButton colliderComfire;
	
	private JCheckBox chkScoreElement;
    private JComboBox listnerCombo;
	private MoveType moveState2;
	
	private JComboBox primaryBox;
	private JComboBox secondBox; 
	private JComboBox collisionTypes;
	private JComboBox collisionTypes2;
	private JCheckBox c1;
	private JCheckBox c2;
	private JCheckBox c3;
	CollisionChecker collisionChecker = new CollisionChecker();
	ArrayList<Command> eventList = new ArrayList<>();

	

	public DesignPanel() {
		this.firstTime = true;
		this.eventListIndex = 0;
		this.colliders = new ArrayList<>();
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
		
		collider = new JPanel();
		scroller2 = new JScrollPane(collider,
	            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
	            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		collider.setBackground(Color.LIGHT_GRAY);
		collider.setLayout(new BoxLayout(collider,BoxLayout.Y_AXIS));
		
		control  = new JPanel();
		control.setBackground(Color.LIGHT_GRAY);
		control.setLayout(new BoxLayout(control,BoxLayout.Y_AXIS));
		
		timeLine = new JPanel();
		timeLine.setBackground(Color.LIGHT_GRAY);
		timeLine.setLayout(new BoxLayout(timeLine,BoxLayout.Y_AXIS));
		
		// Tabbed pane holds the two different interfaces 

		tabbedPane = new JTabbedPane(); 
		tabbedPane.addTab("Graphic", null, scroller, null);
		tabbedPane.addTab("Control", null, control, null);
		tabbedPane.addTab("TimeLine", null, timeLine, null);
		tabbedPane.addTab("Colliders", null, scroller2, null);
		tabbedPane.setPreferredSize(new Dimension(Constants.DESIGN_PANEL_WIDTH, 500));
		ChangeListener changeListenerFortag = new ChangeListener() {
		      public void stateChanged(ChangeEvent changeEvent) {
		        JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
		        preview.removeAll();
		        preview.setElements(new ArrayList<Element>());
		        refresh(preview);
		      }
		    };
		tabbedPane.addChangeListener(changeListenerFortag);
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
		this.moveState = gameElement == null ? MoveType.FREE : gameElement.getMoveType();
		this.moveState2 = gameElement == null ? MoveType.FREE : gameElement.getMoveType();
		// This button adds a new combo box to select basic shape of the 	
		//for graphic tab
		JPanel baseButtons = new JPanel(new FlowLayout());
		addGraphicElementButton = new JButton("Add Element");
		addGraphicElementButton.setEnabled(this.finished);
		addGraphicElementButton.addActionListener(this);
		addGraphicElementButton.setActionCommand("addElement");
		
		finishedButton = new JButton("Finished");
		finishedButton.setEnabled(!this.finished);
		finishedButton.addActionListener(this);
		finishedButton.setActionCommand("finishedElement");
		
		//graphic.add(Box.createRigidArea(new Dimension(5,5)));
		
		
		baseButtons.add(addGraphicElementButton);
		baseButtons.add(finishedButton);
		graphic.add(baseButtons);
		
		if(firstTime) {
			this.firstTime = false;
			JPanel colliderButtons = new JPanel(new FlowLayout());
			addColliderButton = new JButton("Add Collider");
			addColliderButton.setEnabled(this.finished);
			addColliderButton.addActionListener(this);
			addColliderButton.setActionCommand("addCollider");
			colliderButtons.add(addColliderButton);
			collider.add(colliderButtons);
			
			
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
			
			control.add(controlElementPanel);
			
			//for collider
			colliderComfire = new JButton("colliderComfire");
			colliderComfire.setActionCommand("colliderComfire");
			colliderComfire.setVisible(true);
			colliderComfire.setAlignmentY(TOP_ALIGNMENT);
			colliderComfire.addActionListener(this);
			collider.add(colliderComfire);
			end = new EndingConditions();
			JPanel buildConditionPanel = new JPanel();
			iniBuildConditionPanel(buildConditionPanel);
			timeLine.add(buildConditionPanel);
		}
		if(gameElement!= null) {
			this.addElementSelect(gameElement);
		}
		
	}
	
	public void iniBuildConditionPanel(JPanel buildConditionPanel) {
		buildConditionPanel.setLayout(new BoxLayout(buildConditionPanel, BoxLayout.Y_AXIS));
		JCheckBox c1 = new JCheckBox("Timer");
		JCheckBox c2 = new JCheckBox("Score");
		JCheckBox c3 = new JCheckBox("Collision");
		c1.setActionCommand("timerCondition");
		c1.addActionListener(this);
		c2.setActionCommand("scoreCondition");
		c2.addActionListener(this);
		c3.setActionCommand("collisionCondition");
		c3.addActionListener(this);
		c1.setAlignmentY(LEFT_ALIGNMENT);
		c2.setAlignmentY(LEFT_ALIGNMENT);
		c3.setAlignmentY(LEFT_ALIGNMENT);
		
		JLabel timerlabel = new JLabel("Timer reachs : ");
		JTextField timerField = new JTextField("", 10);
		timerField.setName("timerField");
		timerField.getDocument().addDocumentListener(this);
		timerField.getDocument().putProperty("owner", timerField);
		
		JLabel scorelabel = new JLabel("Score reachs : ");
		JTextField scoreField = new JTextField("",10);
		scoreField.setName("scoreField");
		scoreField.getDocument().addDocumentListener(this);
		scoreField.getDocument().putProperty("owner", scoreField);

		endingConfirm = new JButton("That is ending condition!");
		endingConfirm.setActionCommand("endingConfirm");
		endingConfirm.setVisible(true);
		JPanel timerFieldHolder = new JPanel();
		timerFieldHolder.add(c1);
		timerFieldHolder.add(timerlabel);
		timerFieldHolder.add(timerField);
		JPanel scoreFieldHolder = new JPanel();
		scoreFieldHolder.add(c2);
		scoreFieldHolder.add(scorelabel);
		scoreFieldHolder.add(scoreField);
		buildConditionPanel.add(endingConfirm);
		//buildConditionPanel.add(scoreFieldHolder);
		buildConditionPanel.add(timerFieldHolder);
		//buildConditionPanel.add(c3);
		control.add(controlElementPanel);
	}
	// Adds collider
	private void addCollider() {
		this.eventListIndex ++;
		// TODO Auto-generated method stub
		List<GameElement> gameElements = new ArrayList<>();
		gameElements = designController.getGraphicsElements();
		JPanel card = this.collider;
		
	
        JPanel colliderCard = new JPanel(); //use FlowLayout
        colliderCard.setLayout(new BoxLayout(colliderCard,BoxLayout.Y_AXIS));
        colliderCard.setMaximumSize(new Dimension(Constants.DESIGN_PANEL_WIDTH, 125));
        
        ArrayList<String> names = new ArrayList<>();
        for(GameElement e: gameElements) {
        	names.add(e.getName());
        }
        
        JPanel primary = new JPanel(); //use FlowLayout
        primary.add(new JLabel("Primary Object: ", JLabel.LEFT));
        primaryBox = new JComboBox(names.toArray());
		//String name = names.get(nameIndex);
		primary.add(primaryBox);
		colliderCard.add(primary);
		
        JPanel secondary = new JPanel(); //use FlowLayout
		secondary.add(new JLabel("Secondary Object: ", JLabel.LEFT));
        //names.remove(nameIndex);
		secondBox = new JComboBox(names.toArray());
		secondary.add(secondBox);
		colliderCard.add(secondary);
		
		
        JPanel collisionType = new JPanel(); //use FlowLayout
        collisionType.add(new JLabel("Primary Collision Type: ", JLabel.LEFT));
		collisionTypes = new JComboBox(CollisionType.values());
		collisionType.add(collisionTypes);
		colliderCard.add(collisionType);
		
        JPanel collisionType2 = new JPanel(); //use FlowLayout
        collisionType2.add(new JLabel("Primary Collision Type: ", JLabel.LEFT));
		collisionTypes2 = new JComboBox(CollisionType.values());
		collisionType2.add(collisionTypes2);
		colliderCard.add(collisionType2);
	
		c1 = new JCheckBox("Sound");
		c2 = new JCheckBox("Score");
		c3 = new JCheckBox("GameOver");
		c1.setActionCommand("soundEvent");
		c1.addActionListener(this);
		c2.setActionCommand("scoreEvent");
		c2.addActionListener(this);
		c3.setActionCommand("gameOverEvent");
		c3.addActionListener(this);
		c1.setAlignmentY(LEFT_ALIGNMENT);
		c2.setAlignmentY(LEFT_ALIGNMENT);
		c3.setAlignmentY(LEFT_ALIGNMENT);
		colliderCard.add(c1);
		colliderCard.add(c2);
		colliderCard.add(c3);
		
//		colliders.add(new Collider(gameElements.get(nameIndex), gameElements.get(secondIndex),
//				(CollisionType) collisionTypes.getSelectedItem(), (CollisionType) collisionTypes2.getSelectedItem(), 
//				collisionChecker, colliderEventLists.get(eventListIndex - 1))); 
//		

		Border redline = BorderFactory.createLineBorder(Color.gray);
		TitledBorder border = BorderFactory.createTitledBorder(
                redline, "Collier " + (colliders.size() + 1));
	    border.setTitleJustification(TitledBorder.LEFT);
	    border.setTitlePosition(TitledBorder.BELOW_TOP);preview.setBorder(redline);
	    colliderCard.setBorder(border);
		card.add(colliderCard);
		
		this.revalidate();
	    this.repaint();
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
		this.preview.addGameElement(g);
		//Where the components controlled by the CardLayout are initialized:
		this.finished = false; // Prevents user adding another element until finished
		this.addGraphicElementButton.setEnabled(this.finished);
		this.finishedButton.setEnabled(!this.finished);
		
		//Create the "cards".
		JPanel circleCard = new JPanel();
		createCircleCard(circleCard, g);
		
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
		String comboBoxItems[] = {  CIRCLE ,RECTANGLE};
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
		String xVelLabel = "0";
		String yVelLabel = "0";
		this.moveState = MoveType.FREE; 
		int moveIndex = 0;
		if(this.pushedElement) {
			xLabel = String.valueOf(gameElement.getX());
			yLabel = String.valueOf(gameElement.getY());
			xVelLabel = String.valueOf(gameElement.getVelX());
			yVelLabel = String.valueOf(gameElement.getVelY());
			ArrayList<MoveType> temp = new ArrayList<>(Arrays.asList(MoveType.values()));
			
			moveIndex = temp.indexOf(gameElement.getMoveType());
			System.out.println(temp + " " + gameElement.getMoveType() + " mIn" + moveIndex);
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
		moveTypeBox.addActionListener(this);
		moveTypeBox.setActionCommand("moveTypeChanged");
		moveTypeBox.setSelectedIndex(moveIndex);
		comboBoxPane2.add(moveTypeBox);
		card.add(comboBoxPane2);
		
		card.add(new JLabel("   X-Velocity: ", JLabel.LEFT));
		xVel = new JTextField(xVelLabel, 4);
		card.add(xVel);
		card.add(new JLabel("Y-Velocity: ", JLabel.LEFT));
		yVel = new JTextField(yVelLabel, 4);
		card.add(yVel);
		xVel.setEnabled(this.moveState == MoveType.FOURWAY || this.moveState == MoveType.FREE || this.moveState == MoveType.LEFTRIGHT);
		yVel.setEnabled(this.moveState == MoveType.FOURWAY || this.moveState == MoveType.FREE || this.moveState == MoveType.UPDOWN);
		
		xVel.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
               
            }

            @Override
            public void focusLost(FocusEvent e) {
            	int tempXVel = Integer.parseInt(xVel.getText());
            	GameElement tempElement =(GameElement) that.preview.getElements().get(0);
            	tempElement.setVelX(tempXVel);
            	that.preview.addGameElement(tempElement);
            }
        });
		
		yVel.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
               
            }

            @Override
            public void focusLost(FocusEvent e) {
            	int tempYVel = Integer.parseInt(yVel.getText());
            	GameElement tempElement =(GameElement) that.preview.getElements().get(0);
            	tempElement.setVelY(tempYVel);
            	that.preview.addGameElement(tempElement);	
            }
        });
		this.designCard = card;
		JButton btnCollider = new JButton("Add Collider");
		
		
	
		btnCollider.setAlignmentY(BOTTOM_ALIGNMENT);
		
        btnCollider.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
            	JPanel collider = new JPanel();
        		System.out.println(that.designCard);
        		collider.setLayout(new GridLayout());
        		collider.add(new JButton("   X-Velocity: "));
        		that.designCard.add(collider);
        		that.revalidate();
        	    that.repaint();
            }
        });
        
        chkScoreElement = new JCheckBox("Score Element");
        chkScoreElement.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				chkScoreElement.setSelected(true);
			}
		});
        that.designCard.add(chkScoreElement);
        
        listnerCombo = new JComboBox(ElementListener.values());
        that.designCard.add(listnerCombo);
      
        this.designCard.add(btnCollider);
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
		} else {
			gameElement.setName("Rectangle"+elements.size());
		}
		
		// TODO Auto-generated method stub
		card.add(new JLabel("Object Name: ", JLabel.LEFT));
		final JTextField name2 = new JTextField(nameLabel, 20);
		card.add(name2);
		card.add(new JLabel("               "));
		card.add(new JLabel("Width: ", JLabel.LEFT));
		final JTextField width2 = new JTextField(widthLabel, 4);
		card.add(width2);
		card.add(new JLabel("Height: ", JLabel.LEFT));
		final JTextField height2 = new JTextField(heightLabel, 4);
		card.add(height2);
		// Used for updating the coordinates
		name2.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
               
            }

            @Override
            public void focusLost(FocusEvent e) {
            	String tempName = name2.getText();
            	GameElement tempElement =(GameElement) that.preview.getElements().get(0);
            	
            	tempElement.setName(tempName);
            	that.preview.addGameElement(tempElement);
            }
        });
		
		width2.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
               
            }

            @Override
            public void focusLost(FocusEvent e) {
            	int tempWidth = Integer.parseInt(width2.getText());
            	GameElement tempElement =(GameElement) that.preview.getElements().get(0);
            	tempElement.setWidth(tempWidth);
            	that.preview.addGameElement(tempElement);
            }
        });
		
		height2.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
               
            }

            @Override
            public void focusLost(FocusEvent e) {
            	int tempHeight = Integer.parseInt(height2.getText());
            	GameElement tempElement =(GameElement) that.preview.getElements().get(0);
            	tempElement.setHeight(tempHeight);
            	that.preview.addGameElement(tempElement);
            }
        });
		//createGraphicCard(card, gameElement);
		String xVelLabel = "0";
		String yVelLabel = "0";
		this.moveState2 = MoveType.FREE; 
		int moveIndex2 = 0;
		if(this.pushedElement) {
			xLabel = String.valueOf(gameElement.getX());
			yLabel = String.valueOf(gameElement.getY());
			xVelLabel = String.valueOf(gameElement.getVelX());
			yVelLabel = String.valueOf(gameElement.getVelY());
			ArrayList<MoveType> temp = new ArrayList<>(Arrays.asList(MoveType.values()));
			
			moveIndex2 = temp.indexOf(gameElement.getMoveType());
			System.out.println(temp + " " + gameElement.getMoveType() + " mIn" + moveIndex2);
		}
		card.add(new JLabel("X-Position: ", JLabel.LEFT));
		final JTextField xCoor2 = new JTextField(xLabel, 4);
		card.add(xCoor2);
		card.add(new JLabel("Y-Position: ", JLabel.LEFT));
		final JTextField yCoor2 = new JTextField(yLabel, 4);
		card.add(yCoor2);
		// Used for updating the coordinates
		xCoor2.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
               
            }

            @Override
            public void focusLost(FocusEvent e) {
            	int tempX = Integer.parseInt(xCoor2.getText());
            	GameElement tempElement =(GameElement) that.preview.getElements().get(0);
            	tempElement.setX(tempX);
            	that.preview.addGameElement(tempElement);
            }
        });
		
		yCoor2.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
               
            }

            @Override
            public void focusLost(FocusEvent e) {
            	int tempY = Integer.parseInt(yCoor2.getText());
            	GameElement tempElement =(GameElement) that.preview.getElements().get(0);
            	tempElement.setY(tempY);
            	that.preview.addGameElement(tempElement);	
            }
        });
		
		
		JButton btn12 = new JButton("Choose Color");
	
		card.add(btn12);
	
		btn12.setAlignmentY(BOTTOM_ALIGNMENT);
		
        btn12.addActionListener(new ActionListener() {
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
        
        JButton btnImage2 = new JButton("Choose Image");
        card.add(btnImage2);
  
        btnImage2.addActionListener(new ActionListener() {
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
		moveTypeBox.addActionListener(this);
		moveTypeBox.setActionCommand("moveTypeChanged");
		moveTypeBox.setSelectedIndex(moveIndex2);
		comboBoxPane2.add(moveTypeBox);
		card.add(comboBoxPane2);
		
		card.add(new JLabel("   X-Velocity: ", JLabel.LEFT));
		xVel2 = new JTextField(xVelLabel, 4);
		card.add(xVel2);
		card.add(new JLabel("Y-Velocity: ", JLabel.LEFT));
		yVel2 = new JTextField(yVelLabel, 4);
		card.add(yVel2);
		xVel2.setEnabled(this.moveState == MoveType.FOURWAY || this.moveState == MoveType.FREE || this.moveState == MoveType.LEFTRIGHT);
		yVel2.setEnabled(this.moveState == MoveType.FOURWAY || this.moveState == MoveType.FREE || this.moveState == MoveType.UPDOWN);
		
		xVel2.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
               
            }

            @Override
            public void focusLost(FocusEvent e) {
            	int tempXVel = Integer.parseInt(xVel2.getText());
            	GameElement tempElement =(GameElement) that.preview.getElements().get(0);
            	tempElement.setVelX(tempXVel);
            	that.preview.addGameElement(tempElement);
            }
        });
		
		yVel2.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
               
            }

            @Override
            public void focusLost(FocusEvent e) {
            	int tempYVel = Integer.parseInt(yVel2.getText());
            	GameElement tempElement =(GameElement) that.preview.getElements().get(0);
            	tempElement.setVelY(tempYVel);
            	that.preview.addGameElement(tempElement);	
            }
        });
		this.designCard = card;

	
        chkScoreElement = new JCheckBox("Score Element");
        chkScoreElement.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				chkScoreElement.setSelected(true);
			}
		});
        that.designCard.add(chkScoreElement);
        
        listnerCombo = new JComboBox(ElementListener.values());
        that.designCard.add(listnerCombo);
      
        this.revalidate();
	    this.repaint();
	}	
	
	// This creates the part of the design panel responsible for circle only properties 
	private void createCircleCard(JPanel card, GameElement gameElement ) {
		// TODO Auto-generated method stub
		String nameLabel = "Circle"+elements.size();
		String radiusLabel = "100";
		if(this.pushedElement) {
			nameLabel = gameElement.getName();
			radiusLabel = String.valueOf(gameElement.getWidth()/2);
			
		} else {
			gameElement.setName("Circle"+elements.size());
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
		
		//createGraphicCard(card, gameElement);
		String xLabel = "0";
		String yLabel = "0";
		String xVelLabel = "0";
		String yVelLabel = "0";
		this.moveState = MoveType.FREE; 
		int moveIndex = 0;
		if(this.pushedElement) {
			xLabel = String.valueOf(gameElement.getX());
			yLabel = String.valueOf(gameElement.getY());
			xVelLabel = String.valueOf(gameElement.getVelX());
			yVelLabel = String.valueOf(gameElement.getVelY());
			ArrayList<MoveType> temp = new ArrayList<>(Arrays.asList(MoveType.values()));
			
			moveIndex = temp.indexOf(gameElement.getMoveType());
			System.out.println(temp + " " + gameElement.getMoveType() + " mIn" + moveIndex);
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
            	tempElement.setX(tempX);
            	that.preview.addGameElement(tempElement);
            }
        });
		
		yCoor.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
               
            }

            @Override
            public void focusLost(FocusEvent e) {
            	int tempY = Integer.parseInt(yCoor.getText());
            	GameElement tempElement =(GameElement) that.preview.getElements().get(0);
            	tempElement.setY(tempY);
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
		moveTypeBox.addActionListener(this);
		moveTypeBox.setActionCommand("moveTypeChanged");
		moveTypeBox.setSelectedIndex(moveIndex);
		comboBoxPane2.add(moveTypeBox);
		card.add(comboBoxPane2);
		
		card.add(new JLabel("   X-Velocity: ", JLabel.LEFT));
		xVel = new JTextField(xVelLabel, 4);
		card.add(xVel);
		card.add(new JLabel("Y-Velocity: ", JLabel.LEFT));
		yVel = new JTextField(yVelLabel, 4);
		card.add(yVel);
		xVel.setEnabled(this.moveState == MoveType.FOURWAY || this.moveState == MoveType.FREE || this.moveState == MoveType.LEFTRIGHT);
		yVel.setEnabled(this.moveState == MoveType.FOURWAY || this.moveState == MoveType.FREE || this.moveState == MoveType.UPDOWN);
		
		xVel.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
               
            }

            @Override
            public void focusLost(FocusEvent e) {
            	int tempXVel = Integer.parseInt(xVel.getText());
            	GameElement tempElement =(GameElement) that.preview.getElements().get(0);
            	tempElement.setVelX(tempXVel);
            	that.preview.addGameElement(tempElement);
            }
        });
		
		yVel.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
               
            }

            @Override
            public void focusLost(FocusEvent e) {
            	int tempYVel = Integer.parseInt(yVel.getText());
            	GameElement tempElement =(GameElement) that.preview.getElements().get(0);
            	tempElement.setVelY(tempYVel);
            	that.preview.addGameElement(tempElement);	
            }
        });
		this.designCard = card;
		JButton btnCollider = new JButton("Add Collider");
		
		
	
		btnCollider.setAlignmentY(BOTTOM_ALIGNMENT);
		
        btnCollider.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
            	JPanel collider = new JPanel();
        		System.out.println(that.designCard);
        		collider.setLayout(new GridLayout());
        		collider.add(new JButton("   X-Velocity: "));
        		that.designCard.add(collider);
        		that.revalidate();
        	    that.repaint();
            }
        });
        
        chkScoreElement = new JCheckBox("Score Element");
        chkScoreElement.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				chkScoreElement.setSelected(true);
			}
		});
        that.designCard.add(chkScoreElement);
        
        listnerCombo = new JComboBox(ElementListener.values());
        that.designCard.add(listnerCombo);
      
        this.designCard.add(btnCollider);
        this.revalidate();
	    this.repaint();
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
	public void createButtons(MainController driver)
	{
		this.driver = driver;
		//get driver , add buttons for adding control element
		
		JButton controlElementTimer = new JButton("Add Timer");
		controlElementTimer.setFont(new Font("Times", Font.PLAIN, 12));
		controlElementTimer.addActionListener(driver);
		controlElementTimer.setActionCommand("AddTimer");
		controlElementTimer.setVisible(true);
		controlElementTimer.setAlignmentX(LEFT_ALIGNMENT);
		controlElementTimer.setAlignmentY(BOTTOM_ALIGNMENT);
		controlElementPanel.add(controlElementTimer);
		controlElementPanel.add(Box.createRigidArea(new Dimension(5,5)));
		
		JButton controlElementScore = new JButton("Add Score");
		controlElementScore.setFont(new Font("Times", Font.PLAIN, 12));
		controlElementScore.addActionListener(driver);
		controlElementScore.setActionCommand("AddScore");
		controlElementScore.setVisible(true);
		controlElementScore.setAlignmentX(LEFT_ALIGNMENT);
		controlElementScore.setAlignmentY(BOTTOM_ALIGNMENT);
		controlElementPanel.add(controlElementScore);
		controlElementPanel.add(Box.createRigidArea(new Dimension(5,5)));
		

		
		JButton addControlElementButton = new JButton("AddControlElement");
		addControlElementButton.addActionListener(driver);
		addControlElementButton.setActionCommand("AddControlElement");
		addControlElementButton.setVisible(true);
		addControlElementButton.setAlignmentX(LEFT_ALIGNMENT);
		addControlElementButton.setAlignmentY(BOTTOM_ALIGNMENT);
		controlElementPanel.add(addControlElementButton);
		controlElementPanel.add(Box.createRigidArea(new Dimension(5,5)));
		endingConfirm.addActionListener(driver);
	
	}
	

	// This changes the card shown for creating circle vs rectangle and changes preview window
	public void itemStateChanged(ItemEvent evt) {
	    
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
			try {
				GameElement tempElement =(GameElement) that.preview.getElements().get(0);
				tempElement.setMoveType((MoveType)boxAction.getSelectedItem());
				this.moveState = (MoveType)boxAction.getSelectedItem();
				xVel.setEnabled(this.moveState == MoveType.FOURWAY || this.moveState == MoveType.FREE || this.moveState == MoveType.LEFTRIGHT);
				yVel.setEnabled(this.moveState == MoveType.FOURWAY || this.moveState == MoveType.FREE || this.moveState == MoveType.UPDOWN);
				this.revalidate();
				this.repaint();
			} catch (Exception e1) {
				// do nothing
			}
		}
		if(e.getActionCommand().equals("addCollider")) {
			this.addCollider();
		}
		if(e.getActionCommand().equals("timerCondition")) {
			JCheckBox check = (JCheckBox)e.getSource();
			end.setC1(check.isSelected());
		}
		if(e.getActionCommand().equals("scoreCondiiton")) {
			JCheckBox check = (JCheckBox)e.getSource();
			end.setC2(check.isSelected());
		}
		if(e.getActionCommand().equals("collisionCondition")) {
			JCheckBox check = (JCheckBox)e.getSource();
			end.setC3(check.isSelected());
		}
		if(e.getActionCommand().equals("scoreEvent")) {
			JCheckBox check = (JCheckBox)e.getSource();
			Command scoreTemp = new ScoreEvent(designController.getScoreBoard());
			eventList.add(scoreTemp);
		}
		if(e.getActionCommand().equals("soundEvent")) {
			JCheckBox check = (JCheckBox)e.getSource();
			JFileChooser fc = new JFileChooser();
            BufferedImage img = null;
            int result = fc.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                String sname = file.getAbsolutePath(); //THIS WAS THE PROBLEM
            }
            Command soundTemp = new SoundEvent("explosion.wav");
            eventList.add(soundTemp);
		}
		if(e.getActionCommand().equals("gameOverEvent")) {
			JCheckBox check = (JCheckBox)e.getSource();
			Command GameOver = new GameOverEvent(driver);
			eventList.add(GameOver);
		}
		if(e.getActionCommand().equals("colliderComfire")) {
			this.collidersFinished();
		}
		
		
	}

	private void collidersFinished(){
		List<GameElement> gameElements = new ArrayList<>();
		gameElements = designController.getGraphicsElements();
		
		designController.addGameColliders(new Collider(gameElements.get(primaryBox.getSelectedIndex()), gameElements.get(secondBox.getSelectedIndex()),
				(CollisionType) collisionTypes.getSelectedItem(), (CollisionType) collisionTypes2.getSelectedItem(), 
				collisionChecker, eventList)); 
	}

	private void graphicElementFinished() {
		this.finished = true;
		this.firstTime = false;
		GameElement temp = (GameElement)this.preview.getElements().get(0);
		temp.pushToBoard();
		designController.addGameElement(temp, chkScoreElement.isSelected(), ElementListener.valueOf(listnerCombo.getSelectedItem().toString()));
		try {
			this.graphic.removeAll();
		} catch(Exception e) {
			// Do nothing
		}
		
		this.preview.setElements(new ArrayList<Element>());
		this.addGraphicElementButton.setEnabled(this.finished);
		this.finishedButton.setEnabled(!this.finished);
		this.init(null);
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
		if(owner.getName().equals("timerField")) {
			int time = 0;
	        try 
	        { 
	            // checking valid integer using parseInt() method 
				if(content.equals("")) {
					time = 0;
				}
				else{
					time = Integer.parseInt(content);
				} 
	        }  
	        catch (NumberFormatException notNumE)  
	        { 
	            System.out.println(" is not a valid integer number"); 
	        } 
			end.setTimeCount(time);		
		}
		
		if(owner.getName().equals("scoreField")) {
			int score = 0;
	        try 
	        { 
	            // checking valid integer using parseInt() method 
				if(content.equals("")) {
					score = 0;
				}
				else{
					score = Integer.parseInt(content);
				} 
	        }  
	        catch (NumberFormatException notNumE)  
	        { 
	            System.out.println(" is not a valid integer number"); 
	        } 
			end.setScoreCount(score);
		}
		this.validate();
	}
	
	public void refresh(JComponent j) {
		j.revalidate();
		j.repaint();
		
	}

	public ArrayList<Collider> getColliders() {
		return colliders;
	}

	public EndingConditions getEnd() {
		return end;
	}

	public void setEnd(EndingConditions end) {
		this.end = end;
	}

  public void setColliders(ArrayList<Collider> colliders) {
		this.colliders = colliders;
	}
}
