/**
 *This is our controller class which manages the interaction between model(ball,brick,paddle,clock)
 *and view(frame and panels)*/
package com.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

//import com.gamemaker;
//import com.commands.BallChangeXDirectionCommand;
//import com.commands.BallChangeYDirectionCommand;
//import com.commands.BallEnactCommand;
//import com.commands.BrickEnactCommand;
//import com.commands.PaddleLeftMoveCommand;
//import com.commands.PaddleRightMoveCommand;
//import com.commands.TimerCommand;
//import com.component.Ball;
//import com.component.Brick;
//import com.component.Clock;
//import com.component.Paddle;
//import com.helper.CollisionChecker;
import com.infrastruture.Command;
import com.infrastruture.Constants;
import com.infrastruture.Direction;
import com.infrastruture.Element;
import com.infrastruture.Observer;
import com.timer.GameTimer;
import com.ui.GUI;

public class MainControllerTemp implements Observer, KeyListener,ActionListener{
	protected static Logger log = Logger.getLogger(MainController.class);	
    private GUI gui;
    private GameTimer observable;
    private boolean isGamePaused ;
    //private CollisionChecker collisionChecker;
   
    private Deque<Command> commandQueue;
//    private BallChangeXDirectionCommand ballChangeXDirectionCommand;
//    private BallChangeYDirectionCommand ballChangeYDirectionCommand;
//    private PaddleLeftMoveCommand paddleLeftMoveCommand;
//    private PaddleRightMoveCommand paddleRightMoveCommand;
    private FileWriter file;
    

  
    
	public MainControllerTemp(GUI gui,GameTimer observable) { // Might keep CollisionChecker collisionChecker) {
		
		
		this.gui = gui;
		this.observable = observable;
		// this.collisionChecker = collisionChecker;
		isGamePaused = false;
		commandQueue = new ArrayDeque<Command>();
		//timerCommand = new TimerCommand(clock);	
		initCommands();
    }
	private void initCommands()
	{
//		int i=0;
//		for(Brick b : bricks)
//		{
//			brickActCommands[i] = new BrickEnactCommand(b);
//			i++;
//		}
//		timerCommand = new TimerCommand(clock);
//		ballActCommand = new BallEnactCommand(ball);
//		ballChangeXDirectionCommand = new BallChangeXDirectionCommand(ball);
//		ballChangeYDirectionCommand = new BallChangeYDirectionCommand(ball);
		
	}

	@Override
	public void update() {
		//initCommands();
//		timerCommand.execute();
//		ballActCommand.execute();
//		commandQueue.addLast(timerCommand);
//		commandQueue.addLast(ballActCommand);		
//		Direction result = collisionChecker.checkCollisionBetweenBallAndWall(ball);
//		changeBallDirectionCommand(result);
//		int i= 0;
//		
//		for(Brick b : bricks) {
//			if(b.isVisible())
//			{
//				result = collisionChecker.checkCollisionBetweenCircleAndRectangle(ball.getCircle(), b.getRectangle());
//				if(result != Direction.NONE){
//					brickActCommands[i].execute();
//					commandQueue.addLast(brickActCommands[i]);
//					changeBallDirectionCommand(result);
//					noOfBricks--;
//				}
//			}
//			i++;
//		}
//		if(noOfBricks == 0)
//		{   
//			// Stopping the observable
//			observable.stopTimer();
//			gui.removeKeyListner();
//			gui.draw(null);
//  			SwingUtilities.invokeLater(
//  					new Runnable() {
//
//  						@Override
//  						public void run() {
//  						
//  							gameOver();	
//  						}
//			});
//  			return;
//		}
//		//Check collision between ball and paddle
//		result = collisionChecker.checkCollisionBetweenCircleAndRectangle(ball.getCircle(), paddle.getRectangle());
//		changeBallDirectionCommand(result);
		gui.draw(null);
	}

//	private void changeBallDirectionCommand(Direction result) {
//		
//		if(result == Direction.X) {
//			ballChangeXDirectionCommand.execute();
//			commandQueue.addLast(ballChangeXDirectionCommand);
//		}else if(result == Direction.Y) {
//			ballChangeYDirectionCommand.execute();
//			commandQueue.addLast(ballChangeYDirectionCommand);
//		}else if(result == Direction.BOTH) {
//			ballChangeXDirectionCommand.execute();
//			ballChangeYDirectionCommand.execute();
//			commandQueue.addLast(ballChangeXDirectionCommand);
//			commandQueue.addLast(ballChangeYDirectionCommand);
//		}
//	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
//		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
//			setPaddleLeftMoveCommand(new PaddleLeftMoveCommand(paddle));
//			paddleLeftMoveCommand.execute();
//			commandQueue.addLast(paddleLeftMoveCommand);
//			collisionChecker.checkCollisionBetweenCircleAndRectangle(ball.getCircle(),paddle.getRectangle());
//		}else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
//			setPaddleRightMoveCommand(new PaddleRightMoveCommand(paddle));
//			paddleRightMoveCommand.execute();
//			commandQueue.addLast(paddleRightMoveCommand);
//			collisionChecker.checkCollisionBetweenCircleAndRectangle(ball.getCircle(),paddle.getRectangle());
//		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	private void undoAction() {

		int count = 0;
//		while(count != Constants.TIMER_COUNT) {
//			Command val=commandQueue.pollLast();
//			if(val == null)
//				break;
//			if(val instanceof TimerCommand)
//			{
//				count++;
//			}
//			if(val instanceof BrickEnactCommand)
//			{
//				noOfBricks++;
//			}
//			val.undo();
//		}
		
	}
	
	private void replayAction() {
		// TODO Auto-generated method stub
		
//		Iterator<Command> itr = commandQueue.iterator();
//		int brickCount = noOfBricks;
//		
//		new Thread(){
//			public void run(){
//				while(itr.hasNext()){
//					try {
//						SwingUtilities.invokeAndWait(new Runnable(){
//							Command val = (Command) itr.next();
//							@Override
//							public void run() {
//								// TODO Auto-generated method stub
//								val.execute();
//								gui.draw(null);
//								try {
//									currentThread();
//									Thread.sleep(10);
//								} catch (InterruptedException e) {
//									// TODO Auto-generated catch block
//									log.error(e.getMessage());
//								}
//							}
//						});
//					} catch (InvocationTargetException | InterruptedException e) {
//						// TODO Auto-generated catch block
//						log.error(e.getMessage());
//					}	
//				}
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				noOfBricks = brickCount;
//			}
//		}.start();
//		
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
			out.close();
			fileOut.close();
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	
	}


	

	public void load() {
//		pause();
//		commandQueue.clear();
//		try {
//			int brickNum = 0;
//			String fileName = gui.showOpenDialog();
//			if(!fileName.isEmpty()) {
//			FileInputStream fileIn =new FileInputStream(fileName);
//
//			ObjectInputStream in = new ObjectInputStream(fileIn);
//			
//			gui.load(in);
//			
//			this.clock = (Clock) gui.getTimerPanel().getElements().get(0);
//			this.ball = (Ball) gui.getBoardPanel().getElements().get(0);
//			this.paddle = (Paddle) gui.getBoardPanel().getElements().get(1);
//			this.bricks.clear();
//			for (int i = 2; i < gui.getBoardPanel().getElements().size(); i++) {
//				Brick brick = (Brick)gui.getBoardPanel().getElements().get(i);
//				this.bricks.add(brick);
//				if(brick.isVisible()) {
//					brickNum ++ ;
//				}
//			}
//			this.noOfBricks = brickNum;
//			
//			commandQueue.clear();
//			Deque<Command> loadCmdQueue = (Deque<Command>) in.readObject();
//			commandQueue.addAll(loadCmdQueue);
//			initCommands();
//			in.close();
//			fileIn.close();
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//			log.error(e.getMessage());
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//			log.error(e.getMessage());
//		}
		gui.draw(null);
	}
	
	//Switch between actions when a button is pressed
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String commandText= e.getActionCommand();
		if(commandText.equals("undo")) {
			if(!isGamePaused) {
				pause();
				undoAction();
				unPause();
			} else {
				undoAction();
			}
			gui.changeFocus();
			gui.draw(null);

		}else if(commandText.equals("replay")) {
			replay();
		}else if(commandText.equals("start")) {
			if(isGamePaused) {
				unPause();
				gui.changeFocus();
				gui.draw(null);
			}else {
				gui.dispose();
				gui.revalidate();
				// ! - look at this later Breakout.startGame(true);
			}
		}else if(commandText.equals("pause")) {
			pause();
			gui.changeFocus();
			gui.draw(null);
		}else if(commandText.equals("save")) {
			save();
			gui.changeFocus();
		}else if(commandText.equals("load")) {
			load();
			gui.changeFocus();
			gui.draw(null);;
		}else if(commandText.equals("layout")) {
			pause();
			gui.modifyLayout();
			gui.changeFocus();
			gui.draw(null);
			unPause();
		}
	}
	
	public void replay() {
		pause();
		gameReset();
		replayAction();
		gui.changeFocus();
	}
	public void gameReset() {
		
//		ball.reset();
//		paddle.reset();
//		clock.reset();
//		noOfBricks = Constants.BRICK_NO;
//		for (Brick b : bricks) {
//			b.reset();	
//		}
	}
	
	public void gameOver() {
//		pause();
//		Object[] options = {  "Reset", "Exit", "Replay"}; 
//		String outputMsg = new String();
//		outputMsg = "Your Score is " + Integer.toString(clock.getMinutes()*60 + clock.getSeconds());
//		int a = JOptionPane.showOptionDialog(gui.getBoardPanel(), outputMsg, "Game Over", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
//				, null, options, null);
//		
//		if(a == JOptionPane.YES_OPTION) {
//			gui.dispose();
//			gui.revalidate();
//			Breakout.startGame(true);			
//		}
//		else if(a == JOptionPane.CANCEL_OPTION) {
//			replay();
//		}
//		else
//			System.exit(0);
//		isGamePaused = false;
	}
	
	
	public FileWriter getFile() {
		return file;
	}
	public void setFile(FileWriter file) {
		this.file = file;
	}
//	public TimerCommand getTimerCommand() {
//		return timerCommand;
//	}
//	public void setTimerCommand(TimerCommand timerCommand) {
//		this.timerCommand = timerCommand;
//	}
//	public PaddleLeftMoveCommand getPaddleLeftMoveCommand() {
//		return paddleLeftMoveCommand;
//	}
//	public void setPaddleLeftMoveCommand(PaddleLeftMoveCommand paddleLeftMoveCommand) {
//		this.paddleLeftMoveCommand = paddleLeftMoveCommand;
//	}
//	public PaddleRightMoveCommand getPaddleRightMoveCommand() {
//		return paddleRightMoveCommand;
//	}
//	public void setPaddleRightMoveCommand(PaddleRightMoveCommand paddleRightMoveCommand) {
//		this.paddleRightMoveCommand = paddleRightMoveCommand;
//	}
//	public BallEnactCommand getBallActCommand() {
//		return ballActCommand;
//	}
//	public void setBallActCommand(BallEnactCommand ballActCommand) {
//		this.ballActCommand = ballActCommand;
//	}
//	public BallChangeXDirectionCommand getBallChangeXDirectionCommand() {
//		return ballChangeXDirectionCommand;
//	}
//	public void setBallChangeXDirectionCommand(BallChangeXDirectionCommand ballChangeXDirectionCommand) {
//		this.ballChangeXDirectionCommand = ballChangeXDirectionCommand;
//	}
//	public BallChangeYDirectionCommand getBallChangeYDirectionCommand() {
//		return ballChangeYDirectionCommand;
//	}
//	public void setBallChangeYDirectionCommand(BallChangeYDirectionCommand ballChangeYDirectionCommand) {
//		this.ballChangeYDirectionCommand = ballChangeYDirectionCommand;
//	}
//	public BrickEnactCommand[] getBrickActCommands() {
//			return brickActCommands;
//    }
//	public void setBrickActCommands(BrickEnactCommand[] brickActCommands) {
//			this.brickActCommands = brickActCommands;
//	}
		
}
