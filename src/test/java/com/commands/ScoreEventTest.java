package com.commands;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.components.ScoreBoard;

class ScoreEventTest {
	@Mock ScoreBoard scoreBoard;
	
	@InjectMocks  ScoreEvent  scoreEvent;
	
	@BeforeEach
	void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	void testExecute() {
		when(scoreBoard.getScore()).thenReturn(1);
		scoreEvent.execute();
		verify(scoreBoard).setScore(anyInt());
	}

	@Test
	void testUndo() {
		 scoreEvent.undo();
		 verify(scoreBoard).setScore(anyInt());
	}

}
