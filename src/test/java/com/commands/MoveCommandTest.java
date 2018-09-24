package com.commands;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import com.components.GameElement;
import com.infrastruture.MoveType;

class MoveCommandTest {

	@Mock GameElement element;
	
	@InjectMocks MoveCommand moveCommand;
	
	@BeforeEach
	void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	void testExecute() {
		when(element.getMoveType()).thenReturn(MoveType.FREE);
		moveCommand.execute();
		verify(element).setX(anyInt());
		verify(element).setY(anyInt());
	}
	@Test
	void testUndo() {
		moveCommand.undo();
		verify(element).setX(anyInt());
		verify(element).setY(anyInt());
	}

}
