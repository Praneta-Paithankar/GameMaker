package com.helper;

import java.awt.Rectangle;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.components.GameElement;
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
		int currentBallPosX = element.getX();
		int currentBallPosY = element.getY();
		int leftWallPosition = 1;
		int rightWallPosition = 2;
		int topWallPosition = 3;
		int bottomWallPosition = 4;
		// check for hit on the wall
		boolean hitLeftWall = currentBallPosX + element.getVelX() < leftWallPosition;
		boolean hitRightWall = currentBallPosX +  element.getWidth()+ element.getVelX() > rightWallPosition;
		boolean hitTopWall = currentBallPosY + element.getVelY() < topWallPosition;
		boolean hitBottomWall = currentBallPosY + element.getHeight() + element.getVelY() > bottomWallPosition;
		
		// if ball hits one of the horizontal sides of wall
		if (hitLeftWall || hitRightWall) {
			return Direction.X;
		}
		// if ball hits one of the vertical sides of wall
		if (hitTopWall) {
			return Direction.Y;
		}

		if (hitBottomWall) {
			JOptionPane.showMessageDialog(null, "Game Over");
			System.exit(0);
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
		
		//Approaching from 
		if(element1BottomRightX > element2.getX() && element1BottomRightY < element2.getY()) {
			return Direction.Y;
		}
		else if(element1BottomRightX < element2.getX() && element1BottomRightY < element2.getY()) {
			return Direction.X;
		}
		
		return Direction.NONE;
	}
}
