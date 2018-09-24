/**
 *This class is used to draw the components on the screen */
package com.ui;

import java.awt.Graphics;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.log4j.Logger;
import com.controller.MainController;
import com.dimension.Coordinate;
import com.controller.DesignController;
import com.infrastruture.Constants;
import com.infrastruture.Element;

@SuppressWarnings("serial")
public class GUI extends JFrame implements Element{
	protected static Logger log = Logger.getLogger(GUI.class);	
	
	private ArrayList<Element> elementList;
	
	private MainController mainController;
	
	private GamePanel gamePanel;
	private MainPanel mainPanel;
	private DesignPanel designPanel;
	private ControlPanel controlPanel;
	private boolean toggleLayout;
	
	private FileReader fileReader;
	private JFileChooser jFileChooser;
	private FileWriter fileWriter;
	private String filePath;
	
	// Removed Timer panel
	public GUI(MainPanel mainPanel,DesignPanel designPanel, GamePanel gamePanel, ControlPanel controlPanel) {
		super("Breakout Game");
		this.mainPanel = mainPanel; // The panel everything is placed on
		this.gamePanel = gamePanel; // The panel user drops items on
		this.designPanel = designPanel; // The panel user creates objects
		this.controlPanel = controlPanel; // The panel user places control buttons
		toggleLayout = false;
		initializeUI();
		elementList = new ArrayList<>();
	}


	private void initializeUI() {
	   this.designPanel.setFrame(this);
	   setSize(Constants.FRAME_WIDTH,Constants.FRAME_HEIGHT);
	   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   setResizable(false);	
	}

	public void removeKeyListner() {
		mainPanel.removeKeyListener(mainController);
	}
	
	public void addDriver(MainController mainController){
		this.mainController = mainController;
		mainPanel.addKeyListener(mainController);
        controlPanel.createButtons(mainController);
        designPanel.createButtons(mainController);
	}

	public void changeFocus()
	{
		mainPanel.requestFocus();
	}


	@Override
	public void draw(Graphics g) {
		for(Element element : elementList) {
			element.draw(g);
		}	
	}


	@Override
	public void addComponent(Element e) {
		add((AbstractPanel)e);
		elementList.add(e);
	}

	@Override
	public void removeComponent(Element e) {
		elementList.remove(e);
	}


	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}	
	public FileReader getFileReader() {
		return fileReader;
	}

	public void setFileReader(FileReader fileReader) {
		this.fileReader = fileReader;
	}		
	
	public MainPanel getMainPanel() {
		return this.mainPanel;
	}

	public void modifyLayout() {
		toggleLayout = !toggleLayout;
		
//		if (toggleLayout) {
//			mainPanel.setLayoutBehavior(new BoxLayoutYAxisBehavior());
//			mainPanel.performUpdateLayout(mainPanel, Constants.MAIN_PANEL_HEIGHT, Constants.MAIN_PANEL_WIDTH);
//			
//			staticPanel.setLayoutBehavior(new BoxLayoutXAxisBehavior());
//			staticPanel.performUpdateLayout(staticPanel, Constants.BOARD_PANEL_HEIGHT,Constants.TIMER_PANEL_WIDTH);
//
//			timerPanel.setLayoutBehavior(new FlowLayoutBehavior());
//			timerPanel.performUpdateLayout(timerPanel, Constants.TIMER_PANEL_WIDTH,Constants.TIMER_PANEL_WIDTH);
//
//			controlPanel.setLayoutBehavior(new GridBagLayoutBehavior());
//			controlPanel.performUpdateLayout(controlPanel,Constants.TIMER_PANEL_HEIGHT-Constants.TIMER_PANEL_WIDTH, Constants.TIMER_PANEL_WIDTH);
//			
//			boardPanel.setLayoutBehavior(new FlowLayoutBehavior());
//			boardPanel.performUpdateLayout(boardPanel, Constants.BOARD_PANEL_WIDTH,Constants.BOARD_PANEL_HEIGHT);
//		} else {
//			mainPanel.setLayoutBehavior(new BoxLayoutXAxisBehavior());
//			mainPanel.performUpdateLayout(mainPanel, Constants.MAIN_PANEL_WIDTH,Constants.MAIN_PANEL_HEIGHT);
//
//			staticPanel.setLayoutBehavior(new BoxLayoutYAxisBehavior());
//			staticPanel.performUpdateLayout(staticPanel, Constants.TIMER_PANEL_WIDTH,Constants.TIMER_PANEL_HEIGHT);
//
//			timerPanel.setLayoutBehavior(new FlowLayoutBehavior());
//			timerPanel.performUpdateLayout(timerPanel, Constants.TIMER_PANEL_WIDTH,Constants.TIMER_PANEL_WIDTH);
//
//			controlPanel.setLayoutBehavior(new FlowLayoutBehavior());
//			controlPanel.performUpdateLayout(controlPanel, Constants.TIMER_PANEL_WIDTH,Constants.TIMER_PANEL_HEIGHT-Constants.TIMER_PANEL_WIDTH);
//
//			boardPanel.setLayoutBehavior(new FlowLayoutBehavior());
//			boardPanel.performUpdateLayout(boardPanel, Constants.BOARD_PANEL_WIDTH,Constants.BOARD_PANEL_HEIGHT);
//		}
		
		JPanel contentPane = (JPanel) getContentPane();
		contentPane.revalidate();
		this.pack();
	}
	
	public String showSaveDialog() {
		try {
			jFileChooser = new JFileChooser();
			jFileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
			jFileChooser.setFileFilter(new FileNameExtensionFilter("serialize file","ser"));
			int rVal = jFileChooser.showSaveDialog(this);
		      if (rVal == JFileChooser.APPROVE_OPTION) {
		        String name = jFileChooser.getSelectedFile().toString();
		    	if (!name.endsWith(".ser"))
		    	        name += ".ser";
		    	return name;
		      }
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	     return "";
	}
	
	public String showOpenDialog() {
		try {
			jFileChooser = new JFileChooser();
			jFileChooser.setFileFilter(new FileNameExtensionFilter("serialize file","ser"));
			int rVal = jFileChooser.showOpenDialog(gamePanel);
		    if (rVal == JFileChooser.APPROVE_OPTION) {
		    	String name = jFileChooser.getSelectedFile().toString();
		    	return name;
		    }
	    }catch(Exception e) {
	    	log.error(e.getMessage());
	    }
		return "";
	}
	
	
	@Override
	public void save(ObjectOutputStream op) {
		// TODO Auto-generated method stub
		for (Element element : elementList) {
			element.save(op);
		}
	}
	
	@Override
	public Element load(ObjectInputStream ip) {
		// TODO Auto-generated method stub
		for (Element element : elementList) {
			element.load(ip);
		}
		return null;
	}

	public GamePanel getGamePanel() {
		return gamePanel;
	}
	
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		for(Element element : elementList) {
			element.reset();
		}
	}
	
	public DesignPanel getDesignPanel() {
		return designPanel;
	}


	public void setDesignPanel(DesignPanel designPanel) {
		this.designPanel = designPanel;
	}


	public ControlPanel getControlPanel() {
		return controlPanel;
	}


	public void setControlPanel(ControlPanel controlPanel) {
		this.controlPanel = controlPanel;
	}

	public void setGamePanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
		
}
