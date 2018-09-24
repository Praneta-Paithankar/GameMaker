package com.commands;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.components.Clock;
import com.components.GameElement;

class TimerCommandTest {
	
	@Mock private Clock clock;
	
	@InjectMocks private TimerCommand timerCommand;
	
	@BeforeEach
	void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	void shouldIncrementTimeOnExecute() {
		when(clock.getMilisecondsElapsed()).thenReturn((long) 10);
		timerCommand.execute();
		verify(clock).setMilisecondsElapsed(anyLong());
	}
	
	@Test
	void shouldDecrementTimeOnUndo() {
		when(clock.getMilisecondsElapsed()).thenReturn((long) 10);
		timerCommand.execute();
		verify(clock).setMilisecondsElapsed(anyLong());
	}
	
	
}
