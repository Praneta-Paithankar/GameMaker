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
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.commands.ChangeVelXCommand;
import com.commands.ChangeVelYCommand;
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
import com.infrastruture.Observer;
import com.timer.GameTimer;
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
		if(commandText.equals("create")) {
			designController.addGameElement();
			designController.addControlElement();
		}
		if(commandText.equals("AddControlElement")) {
			designController.addControlElement();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		List<GameElement> elements= designController.getKeyboardElementsBasedKeys(e.getKeyCode());
		if(elements != null) {
			
			for(GameElement element: elements) {
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
			for(Collider collider: designController.getColliders()) {
				collider.execute(this);
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
		Clock clock = designController.getClock();
		TimerCommand timerCommand = new TimerCommand(designController.getClock());
		timerCommand.execute();
		addCommand(timerCommand);
		for(GameElement element : designController.getGraphicsElements()) {
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
		}
		for(Collider collider: designController.getColliders()) {
			collider.execute(this);
		}
		List<GameElement> graphicsElements = designController.getTimerElements();
		for(GameElement element: graphicsElements) {
			Command command = createCommand(element);
			command.execute();
			addCommand(command);
		}
	}	
	public void start() {
		if(isGamePaused) {
			unPause();
		}
		gui.dispose();
		gui.revalidate();
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
			
			gui.save(out);
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
			
				gui.load(in);
			
				//List<GameElement> graphicsElements = gui.getGamePanel().getElements();
				//designController.setGraphicsElements(graphicsElements);
			
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
		gui.draw(null);
	}
	
	public Command createCommand(GameElement element) {
		return new MoveCommand(element);
	}
	public void  addCommand(Command command) {
		commandQueue.add(command);
	}
	
}
