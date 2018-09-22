package com.commands;

import org.apache.log4j.Logger;

import com.components.Clock;
import com.infrastruture.Command;
import com.infrastruture.Constants;

public class TimerCommand implements Command {
	
	protected static Logger log = Logger.getLogger(TimerCommand.class);
	private Clock clock;
	
	public TimerCommand(Clock clock) {
		this.clock = clock;
		
	}

	@Override
	public void execute() {
		clock.setMilisecondsElapsed(clock.getMilisecondsElapsed()+Constants.TIMER_COUNT);
	
	}

	@Override
	public void undo() {
		clock.setMilisecondsElapsed(clock.getMilisecondsElapsed()-Constants.TIMER_COUNT);
		
	}
}
