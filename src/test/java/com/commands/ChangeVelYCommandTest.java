package com.commands;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;

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
		changeVelYCommand.execute();
		
		verify(element, times(1)).setVelY(anyInt());
	
	}
		
	void ShouldChangeBallDirectionWhenUndoCalled (){
		changeVelYCommand.undo();
		
		verify(element, times(1)).setVelY(anyInt());
		
	}
}
