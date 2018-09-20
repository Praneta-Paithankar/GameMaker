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

import com.behavior.BoxLayoutXAxisBehavior;
import com.behavior.BoxLayoutYAxisBehavior;
import com.behavior.FlowLayoutBehavior;
import com.behavior.GridBagLayoutBehavior;
import com.controller.GameController;
import com.infrastruture.AbstractPanel;
import com.infrastruture.Constants;
import com.infrastruture.Element;

@SuppressWarnings("serial")
public class GUI extends JFrame implements Element{
	protected static Logger log = Logger.getLogger(GUI.class);	
	private GamePanel boardPanel;
	private ArrayList<Element> elementList;

	private GameController driver;
	private MainPanel mainPanel;
	private JFileChooser c;
	private FileWriter fileWriter;
	private String filePath;
	private FileReader fileReader;

	private StaticPanel staticPanel;
	private TimerPanel timerPanel;
	private ControlPanel controlPanel;
	private boolean toggleLayout;
	
	public GUI(MainPanel mainPanel, GamePanel boardPanel, StaticPanel staticPanel, TimerPanel timerPanel, ControlPanel controlPanel) {
		super("Breakout Game");
		this.mainPanel = mainPanel;
		this.boardPanel = boardPanel;
		this.staticPanel = staticPanel;
		this.timerPanel = timerPanel;
		this.controlPanel = controlPanel;
		toggleLayout = false;
		initializeUI();
		elementList = new ArrayList<>();
	}

	private void initializeUI() {
		
	   setSize(Constants.FRAME_WIDTH,Constants.FRAME_HEIGHT);
	   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   setResizable(false);	
	}

	public void removeKeyListner() {
		mainPanel.removeKeyListener(driver);
	}
	public void addDriver(GameController driver){
		this.driver = driver;
		mainPanel.addKeyListener(driver);
        controlPanel.createButtons(driver);
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
	public void reset() {
		for(Element element : elementList) {
			element.reset();
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

	public void modifyLayout() {
		toggleLayout = !toggleLayout;
		
		if (toggleLayout) {
			mainPanel.setLayoutBehavior(new BoxLayoutYAxisBehavior());
			mainPanel.performUpdateLayout(mainPanel, Constants.MAIN_PANEL_HEIGHT, Constants.MAIN_PANEL_WIDTH);
			
			staticPanel.setLayoutBehavior(new BoxLayoutXAxisBehavior());
			staticPanel.performUpdateLayout(staticPanel, Constants.BOARD_PANEL_HEIGHT,Constants.TIMER_PANEL_WIDTH);

			timerPanel.setLayoutBehavior(new FlowLayoutBehavior());
			timerPanel.performUpdateLayout(timerPanel, Constants.TIMER_PANEL_WIDTH,Constants.TIMER_PANEL_WIDTH);

			controlPanel.setLayoutBehavior(new GridBagLayoutBehavior());
			controlPanel.performUpdateLayout(controlPanel,Constants.TIMER_PANEL_HEIGHT-Constants.TIMER_PANEL_WIDTH, Constants.TIMER_PANEL_WIDTH);
			
			boardPanel.setLayoutBehavior(new FlowLayoutBehavior());
			boardPanel.performUpdateLayout(boardPanel, Constants.BOARD_PANEL_WIDTH,Constants.BOARD_PANEL_HEIGHT);
		} else {
			mainPanel.setLayoutBehavior(new BoxLayoutXAxisBehavior());
			mainPanel.performUpdateLayout(mainPanel, Constants.MAIN_PANEL_WIDTH,Constants.MAIN_PANEL_HEIGHT);

			staticPanel.setLayoutBehavior(new BoxLayoutYAxisBehavior());
			staticPanel.performUpdateLayout(staticPanel, Constants.TIMER_PANEL_WIDTH,Constants.TIMER_PANEL_HEIGHT);

			timerPanel.setLayoutBehavior(new FlowLayoutBehavior());
			timerPanel.performUpdateLayout(timerPanel, Constants.TIMER_PANEL_WIDTH,Constants.TIMER_PANEL_WIDTH);

			controlPanel.setLayoutBehavior(new FlowLayoutBehavior());
			controlPanel.performUpdateLayout(controlPanel, Constants.TIMER_PANEL_WIDTH,Constants.TIMER_PANEL_HEIGHT-Constants.TIMER_PANEL_WIDTH);

			boardPanel.setLayoutBehavior(new FlowLayoutBehavior());
			boardPanel.performUpdateLayout(boardPanel, Constants.BOARD_PANEL_WIDTH,Constants.BOARD_PANEL_HEIGHT);
		}
		
		JPanel contentPane = (JPanel) getContentPane();
		contentPane.revalidate();
		this.pack();
	}
	
	public String showSaveDialog() {
		try {
			c = new JFileChooser();
			c.setDialogType(JFileChooser.SAVE_DIALOG);
			c.setFileFilter(new FileNameExtensionFilter("serialize file","ser"));
			int rVal = c.showSaveDialog(this);
		      if (rVal == JFileChooser.APPROVE_OPTION) {
		        String name = c.getSelectedFile().toString();
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
		c = new JFileChooser();
		c.setFileFilter(new FileNameExtensionFilter("serialize file","ser"));
		int rVal = c.showOpenDialog(boardPanel);
	    if (rVal == JFileChooser.APPROVE_OPTION) {
	    	String name = c.getSelectedFile().toString();
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

	public GamePanel getBoardPanel() {
		return boardPanel;
	}
	
	public TimerPanel getTimerPanel() {
		return timerPanel;
	}

		
}
