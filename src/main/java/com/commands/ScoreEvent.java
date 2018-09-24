package com.commands;

import org.apache.log4j.Logger;

import com.components.ScoreBoard;
import com.infrastruture.Command;
import com.infrastruture.Event;

public class ScoreEvent implements Command{

	protected static final Logger logger = Logger.getLogger(ScoreEvent.class);
	private ScoreBoard scoreBoard;
	private int prevScore;
	
	public ScoreEvent(ScoreBoard scoreBoard) {
		this.scoreBoard = scoreBoard;
	}
	
	@Override
	public void execute() {
		prevScore = scoreBoard.getScore();
		scoreBoard.setScore(scoreBoard.getScore()+1);
	}
	
	@Override
	public void undo() {
		scoreBoard.setScore(prevScore);
	}

}
