package com.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.verify;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.components.GameElement;

public class ExplodeCommandTest {

	@Mock 
	GameElement element;
	
	@InjectMocks 
	ExplodeCommand explodeCommand;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	void ShouldSetVisibilityToFalse() {
		explodeCommand.execute();
		verify(element).setVisible(false);
	}
	
	@Test
	void ShouldSetVisibilityToTrue() {
		explodeCommand.undo();
		verify(element).setVisible(true);
	}
}
