package com.helper;

import java.awt.Rectangle;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.components.GameElement;
import com.dimension.Coordinate;
import com.infrastruture.Constants;
import com.infrastruture.Direction;

public class CollisionChecker {
	
	public static final Logger logger = Logger.getLogger(CollisionChecker.class);
	
	public CollisionChecker() {
		
	}
	
	public boolean checkIntersectionBetweenElements(GameElement element1, GameElement element2) {
		Rectangle element1Rect = new Rectangle(element1.getX(), element1.getY(), element1.getWidth(), element1.getHeight());
		Rectangle element2Rect = new Rectangle(element2.getX(), element2.getY(), element2.getWidth(), element2.getHeight());
		
		if(element1Rect.intersects(element2Rect)) {
			return true;
		}
		return false;
	}
	
	public Direction checkCollisionBetweenGameElementAndBounds(GameElement element) {

		Coordinate delta = element.getCoordinate();
		
 		//get current position of ball
 		int left =  element.getX();
 		int right = element.getX() + element.getWidth();
 		int top = element.getY();
 		int bottom = element.getY() + element.getHeight();
 		
 		
 		if((left <=0) && (delta.getX() < 0))
 		{
 		    return Direction.X;
 		}
 		if((right >= Constants.GAME_PANEL_WIDTH) && (delta.getX() > 0))
 		{
 			return Direction.X;
 		}
 		if((top <=0) && (delta.getY() < 0))
 		{
 			return Direction.Y;
 		}
 		if((bottom >= Constants.GAME_PANEL_HEIGHT) && (delta.getY() > 0))
 		{
 			return Direction.Y;
 		}
 	
		return Direction.NONE;
 	
	}
	
	
	public Direction checkCollisionBetweenGameElements(GameElement element1, GameElement element2) {
		int element1PrevOffsetX = element1.getX() - element1.getVelX();
		int element1PrevOffsetY = element1.getVelY() - element1.getVelY(); 
		
		int element1BottomLeftY = element1.getY() + element1.getHeight();
		int element1BottomLeftX =  element1.getX() + element1.getHeight();
		int element1BottomRightY = element1.getY() + element1.getHeight() + element1.getWidth();
		int element1BottomRightX = element1.getX() + element1.getWidth() + element1.getHeight();
		int element1TopRightX = element1.getX() + element1.getWidth();
		int element1TopRightY = element1.getY() + element1.getX() + element1.getWidth();
		
		int element2BottomLeftY = element2.getY() + element2.getHeight();
		int element2BottomLeftX =  element2.getX() + element2.getHeight();
		int element2BottomRightY = element2.getY() + element2.getHeight() + element2.getWidth();
		int element2BottomRightX = element2.getX() + element2.getWidth() + element2.getHeight();
		int element2TopRightX = element2.getX() + element2.getWidth();
		int element2TopRightY = element2.getY() + element2.getX() + element2.getWidth();
		
		//Approaching from top right and going towards 
		if((element1BottomRightX >= element2.getX() && element1BottomRightY <= element2.getY()) ||
				(element1BottomLeftX <= element2TopRightX && element1BottomLeftY <= element2TopRightY)) {
			return Direction.Y;
		}
		else if((element1BottomRightX <= element2.getX() && element1BottomRightY <= element2.getY()) || 
				(element1TopRightX <= element2.getX() && element1TopRightY <= element2BottomLeftY)) {
			return Direction.X;
		}
		else if((element1TopRightX >= element2BottomLeftX && element1TopRightY >= element2BottomLeftY) ||
				(element1.getX() <= element2BottomRightX && element1.getY() >= element2BottomRightY)) {
			return Direction.Y;
		}
		else if((element1.getX() >= element2BottomRightX && element1.getY() <= element2BottomRightY) || 
				(element1BottomLeftX >= element2TopRightX && element1BottomLeftY >= element2TopRightY)) {
			return Direction.X;
		}
		return Direction.BOTH;
	}
}
