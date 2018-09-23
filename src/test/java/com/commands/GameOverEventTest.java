package com.commands;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.components.Clock;
import com.controller.MainController;

class GameOverEventTest {

	@Mock private MainController mainController;
	
	@InjectMocks private GameOverEvent gameOverEvent;
	
	@BeforeEach
	void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
	}
	@Test
	void testExecute() {
		gameOverEvent.execute();
		verify(mainController).gameOver();;
	}

}
