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

public class ChangeVelYCommandTest {
	
	@Mock private GameElement element;
	
	@InjectMocks private ChangeVelYCommand changeVelYCommand;
	
	@BeforeEach
	void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	void ShouldChangeElementDirectionWhenExecuteCalled() {
		Coordinate cord = mock(Coordinate.class);
		when(element.getCoordinate()).thenReturn(cord);
		when(cord.getY()).thenReturn(40);
		
		changeVelYCommand.execute();
		
		verify(element, times(2)).getCoordinate();
	
	}
		
	void ShouldChangeBallDirectionWhenUndoCalled (){
		Coordinate cord = mock(Coordinate.class);
		when(element.getCoordinate()).thenReturn(cord);
		when(cord.getY()).thenReturn(40);
		
		changeVelYCommand.undo();
		
		verify(element, times(2)).getCoordinate();
		
	}
}
