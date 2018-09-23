package com.events;

import org.apache.log4j.Logger;

import com.components.ScoreBoard;
import com.infrastruture.Event;

public class ScoreEvent implements Event{

	protected static final Logger logger = Logger.getLogger(ScoreEvent.class);
	private ScoreBoard scoreBoard;
	
	public ScoreEvent(ScoreBoard scoreBoard) {
		this.scoreBoard = scoreBoard;
	}
	@Override
	public void executeEvent() {
		scoreBoard.setScore(scoreBoard.getScore()+1);
	}

}
