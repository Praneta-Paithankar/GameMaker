
package com.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.commands.ChangeVelXCommand;
import com.commands.ChangeVelYCommand;
import com.commands.KeyMoveCommand;
import com.commands.MoveCommand;
import com.commands.TimerCommand;
import com.components.Clock;
import com.components.GameElement;
import com.helper.Collider;
import com.helper.CollisionChecker;
import com.infrastruture.ActionType;
import com.infrastruture.Command;
import com.infrastruture.Constants;
import com.infrastruture.Direction;
import com.infrastruture.Element;
import com.infrastruture.KeyType;
import com.infrastruture.Observer;
import com.timer.GameTimer;
import com.ui.CustomButton;
import com.ui.GUI;

public class MainController implements Observer, KeyListener, ActionListener{
	protected static Logger log = Logger.getLogger(MainController.class);
	private GUI gui;
    private GameTimer observable;
    private boolean isGamePaused;
    private Deque<Command> commandQueue;
	private DesignController designController;
	private CollisionChecker collisionChecker ;

	public MainController(GUI gui,GameTimer observable,DesignController designController, CollisionChecker collisionChecker) { 
		this.gui = gui;
		this.observable = observable;

		this.designController = designController;
		this.collisionChecker = collisionChecker;
		isGamePaused = false;
		commandQueue = new ArrayDeque<Command>();
    }

	
	@Override
	public void actionPerformed(ActionEvent event) {
		String commandText= event.getActionCommand();
		if(commandText.equals("AddControlElement")) {
			designController.addControlElement();
			gui.changeFocus();
		}
		if(commandText.equals("AddTimer")) {
			designController.addTimer();
			gui.changeFocus();
		}
		if(commandText.equals("AddScore")) {
			designController.addScore();
			gui.changeFocus();
		}
		ActionType actionType = designController.getActionTypeBasedOnButtonCommand(commandText);
		if(actionType != null) {
			if(actionType == ActionType.PLAY) {
				start();
			}else if(actionType == ActionType.PAUSE) {
				pause();
			}else if(actionType == ActionType.REPLAY) {
				replay();
			}else if(actionType == ActionType.SAVE) {
				save();
			}else if(actionType == ActionType.LOAD) {
				load();
			}else if(actionType == ActionType.UNDO) {
				undo();
			}else if (actionType == ActionType.RESET) {
				gameReset();
			}
			
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		List<GameElement> elements= designController.getKeyboardElementsBasedKeys(e.getKeyCode());
		if(elements != null) {
			KeyType key = null ;
			if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				key = KeyType.LEFT;
			}else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
				key = KeyType.RIGHT;
			}else if(e.getKeyCode() == KeyEvent.VK_UP) {
				key = KeyType.UP;
			}else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				key = KeyType.DOWN;
			}
			for(GameElement element: elements) {
				Command keyMoveCommand = new KeyMoveCommand(element,key);
				keyMoveCommand.execute();
				addCommand(keyMoveCommand);
			}
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		TimerCommand timerCommand = new TimerCommand(designController.getClock());
		timerCommand.execute();
		addCommand(timerCommand);
		for(Collider collider: designController.getColliders()) {
			collider.execute(this);
		}
		List<GameElement> graphicsElements = designController.getTimerElements();
		for(GameElement element: graphicsElements) {
			Direction direction = collisionChecker.checkCollisionBetweenGameElementAndBounds(element);
			if(direction == direction.X) {
				Command command = new ChangeVelXCommand(element);
				command.execute();
				addCommand(command);
			}
			else if(direction == direction.Y) {
				 Command command = new ChangeVelYCommand(element);
				 command.execute();
				 addCommand(command);
			}
			Command command = createCommand(element);
			command.execute();
			addCommand(command);
		}
		List<GameElement> scoreElements = designController.getScoreElementList();
		for(GameElement element : scoreElements) {
			if(element.isVisible())
				break;
			SwingUtilities.invokeLater(
  					new Runnable() {

  						@Override
  						public void run() {
  						
  							gameOver();	
  						}
			});
		}
		if(designController.isTimerConstraintSpecified())
		{
			Clock clock = designController.getClock();
			if(clock.getMilisecondsElapsed() >= designController.getTimeConstraintinmilliSeconds()) {
				SwingUtilities.invokeLater(
	  					new Runnable() {

	  						@Override
	  						public void run() {
	  						
	  							gameOver();	
	  						}
				});
			}
		}
		gui.draw(null);
	}	


	public void start() {
		if(isGamePaused) {
			unPause();
		}
		gui.changeFocus();
		observable.registerObserver(this);
	}
	
	public void undo() {
		if(!isGamePaused) {
			pause();
			undoAction();
			unPause();
		} else {
			undoAction();
		}
		gui.changeFocus();
	}
	private void undoAction() {
		int count = 0;
		while(count != Constants.TIMER_COUNT) {
			Command val=commandQueue.pollLast();
			if(val == null)
				break;
			if(val instanceof TimerCommand)
			{
				count++;
			}
			val.undo();
		}
		gui.revalidate();
		gui.draw(null);
	}
	
	private void replayAction() {
		// TODO Auto-generated method stub
	    final Iterator<Command> itr = commandQueue.iterator();
		new Thread(){
			public void run(){
				while(itr.hasNext()){
					try {
						SwingUtilities.invokeAndWait(new Runnable(){
							Command val = (Command) itr.next();
							@Override
							public void run() {
								// TODO Auto-generated method stub
								val.execute();
								gui.draw(null);
								try {
									currentThread();
									Thread.sleep(10);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									log.error(e.getMessage());
								}
							}
						});
					} catch (InvocationTargetException | InterruptedException e) {
						// TODO Auto-generated catch block
						log.error(e.getMessage());
					}	
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
	}
	public void replay() {
		pause();
		gameReset();
		replayAction();
		gui.changeFocus();
	}
	
	public void gameReset() {
		for(GameElement gameElement: designController.getGraphicsElements()) {
			gameElement.reset();
		}
		Clock clock = designController.getClock();
		clock.reset();
	}
	
	public void pause() {
		isGamePaused = true;
		if(!observable.isObserverListEmpty()) {
			observable.removeObserver(this);
		}
	}
	public void unPause() {
		isGamePaused = false;
		observable.registerObserver(this);
	}

	public void save() {
		pause();
		try {
			String fileName = gui.showSaveDialog();
			if(!fileName.isEmpty()) {
			FileOutputStream fileOut = new FileOutputStream(fileName);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			List<Element> list = gui.getGamePanel().getElements();
			out.writeObject(list);
			list = gui.getControlPanel().getElements();
			for(Element ele : list) {
				if(ele instanceof Clock) {
					designController.setClock((Clock)ele);
					break;
				}
			}
			out.writeObject(list);
			List<CustomButton> buttons  = gui.getControlPanel().getButtons();
			out.writeObject(buttons);
			//gui.add(out)
			out.writeObject(commandQueue);
			out.writeObject(designController.getColliders());
			out.writeObject(designController.getControlElements());
			out.writeObject(designController.getTimerElements());
			out.writeObject(designController.getKeyboardElements());
			out.close();
			fileOut.close();
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	
	}
	public void load() {
		pause();
		commandQueue.clear();
		try {
			String fileName = gui.showOpenDialog();
			if(!fileName.isEmpty()) {
				FileInputStream fileIn =new FileInputStream(fileName);
				ObjectInputStream in = new ObjectInputStream(fileIn);
			
				ArrayList<Element> gameElements = (ArrayList<Element>) in.readObject();
				ArrayList<Element> controlpanelElements = (ArrayList<Element>) in.readObject();
				ArrayList<CustomButton>  controlpanelButtons = (ArrayList<CustomButton>) in.readObject();
		
				gui.getGamePanel().setElement(gameElements);
				gui.getControlPanel().reset();
				gui.getControlPanel().setElements(controlpanelElements);
				gui.getControlPanel().setButtons(new ArrayList<CustomButton>());
				for(CustomButton button: controlpanelButtons) {
					button.addController(this);
					gui.getControlPanel().addButtons(button);
				}
				
				
				//gui.load(in);
			
				List<GameElement>elements = new ArrayList<>();
			    for(Element e :gameElements) {
			    	elements.add((GameElement) e);
			    }
			    designController.setGraphicsElements(elements);
			    
				commandQueue.clear();
				Deque<Command> loadCmdQueue = (Deque<Command>) in.readObject();
				commandQueue.addAll(loadCmdQueue);
			
				List<Collider> colliders = (List<Collider>) in.readObject();
				HashMap <String,ActionType> controlElements = (HashMap <String,ActionType>) in.readObject();
				List<GameElement> timerElements =  (List<GameElement>) in.readObject();
				HashMap<Integer,List<GameElement>> keyboardElements =( HashMap<Integer,List<GameElement>>) in.readObject();
		    
				designController.setColliders(colliders);
				designController.setControlElements(controlElements);
				designController.setKeyboardElements(keyboardElements);
				designController.setTimerElements(timerElements);
		    
				in.close();
				fileIn.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		gui.revalidate();
		gui.draw(null);
		gui.changeFocus();

	}
	public void gameOver() {
		pause();
		Object[] options = { "Exit"}; 
		String outputMsg = new String();
		outputMsg = "Your Score is " + designController.getScoreBoard().getScore();
		int a = JOptionPane.showOptionDialog(gui.getGamePanel(), outputMsg, "Game Over", JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE
				, null, options, null);
		
		if(a == JOptionPane.OK_OPTION) {
			System.exit(0);
		}
	}
	
	public Command createCommand(GameElement element) {
		return new MoveCommand(element);
	}
	public void  addCommand(Command command) {
		commandQueue.add(command);
	}
	
}
