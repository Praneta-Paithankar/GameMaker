package com.commands;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.components.GameElement;
import com.dimension.Coordinate;

public class ChangeVelXCommandTest {

	@Mock private GameElement element;
	
	@InjectMocks private ChangeVelXCommand changeVelXCommand;
	
	@BeforeEach
	void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	void ShouldChangeElementDirectionWhenExecuteCalled() {
		Coordinate cord = mock(Coordinate.class);
		when(element.getCoordinate()).thenReturn(cord);
		when(cord.getX()).thenReturn(40);
		
		changeVelXCommand.execute();
		
		verify(element, times(2)).getCoordinate();
	
	}
		
	void ShouldChangeBallDirectionWhenUndoCalled (){
		Coordinate cord = mock(Coordinate.class);
		when(element.getCoordinate()).thenReturn(cord);
		when(cord.getX()).thenReturn(40);
		
		changeVelXCommand.undo();
		
		verify(element, times(2)).getCoordinate();
		
	}
}
