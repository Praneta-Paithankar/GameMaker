package com.commands;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.components.GameElement;
import com.infrastruture.KeyType;
import static org.mockito.Mockito.*;

class KeyMoveCommandTest {
	
	GameElement element;
	
	KeyType keytype;
	
	KeyMoveCommand keyMoveCommand;
	
	@BeforeEach
	void setup() throws Exception{
		element = mock(GameElement.class);
		KeyType keyType = KeyType.LEFT;
		keyMoveCommand = new KeyMoveCommand(element, keyType);
	}
	
	@Test
	void testExecute() {
		when(element.getX()).thenReturn(60);
		keyMoveCommand.execute();
		verify(element).setX(anyInt());
	}

	@Test
	void testUndo() {
		keyMoveCommand.undo();
		verify(element).setX(anyInt());
		verify(element).setY(anyInt());
	}

}
