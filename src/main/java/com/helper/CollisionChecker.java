package com.helper;

import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RectangularShape;
import java.io.Serializable;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.components.GameElement;
import com.dimension.Circle;
import com.dimension.Coordinate;
import com.infrastruture.Constants;
import com.infrastruture.Direction;
import com.infrastruture.GameElementShape;

public class CollisionChecker implements Serializable {
	
	public static final Logger logger = Logger.getLogger(CollisionChecker.class);
	
	public CollisionChecker() {
		
	}
	
	public boolean checkIntersectionBetweenElements(GameElement element1, GameElement element2) {

		RectangularShape shape1 = getDims(element1);
		RectangularShape shape2 = getDims(element2);		
		 
		Area areaA = new Area(shape1);
		areaA.intersect(new Area(shape2));
		return !areaA.isEmpty();
	}
	
	public RectangularShape getDims(GameElement element) {
		if(element.getGameElementShape() == GameElementShape.RECTANGLE)
			return new Rectangle(element.getX(), element.getY(), element.getWidth(), element.getHeight());
		else
			return new Ellipse2D.Double(element.getX(), element.getY(), element.getWidth(), element.getHeight());
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

		ElementCoordinates e1 = getElementCoordinates(element1);
		ElementCoordinates e2 = getElementCoordinates(element2);
		
		if(element1.getGameElementShape() == GameElementShape.RECTANGLE) {
			if((element1.getX() <= e2.getTopRightX()) || (e1.getTopRightX() >= element2.getX()))
				return Direction.X;
			if((element1.getY() <= e2.getBottomLeftY()) || (e1.getBottomLeftY() >= element2.getY()))
				return Direction.Y;
		}
		
		//Approaching from top right and going towards 
		if((e1.getBottomRightX() >= element2.getX() && e1.getBottomRightY() <= element2.getY()) ||
				(e1.getBottomLeftX() <= e2.getTopRightX() && e1.getBottomLeftY() <= e2.getTopRightY())) {
			return Direction.Y;
		}
		else if((e1.getBottomRightX() <= element2.getX() && e1.getBottomRightY() <= element2.getY()) || 
				(e1.getTopRightX() <= element2.getX() && e1.getTopRightY() <= e2.getBottomLeftY())) {
			return Direction.X;
		}
		else if((e1.getTopRightX() >= e2.getBottomLeftX() && e1.getTopRightY() >= e2.getBottomLeftY()) ||
				(element1.getX() <= e2.getBottomRightX() && element1.getY() >= e2.getBottomRightY())) {
			return Direction.Y;
		}
		else if((element1.getX() >= e2.getBottomRightX() && element1.getY() <= e2.getBottomRightY()) || 
				(e1.getBottomLeftX() >= e2.getTopRightX() && e1.getBottomLeftY() >= e2.getTopRightY())) {
			return Direction.X;
		}
		return Direction.BOTH;
	}
	
	public ElementCoordinates getElementCoordinates(GameElement element){
		ElementCoordinates elementCoordinates = new ElementCoordinates(element);
		elementCoordinates.setBottomLeftX(element.getX() + element.getHeight());
		elementCoordinates.setBottomLeftY(element.getY() + element.getHeight());
		elementCoordinates.setBottomRightX(element.getX() + element.getWidth() + element.getHeight());
		elementCoordinates.setBottomRightY(element.getY() + element.getHeight() + element.getWidth());
		elementCoordinates.setTopRightX(element.getX() + element.getWidth());
		elementCoordinates.setTopRightY(element.getY() + element.getX() + element.getWidth());
		return elementCoordinates;
	}
	
	public class ElementCoordinates{
		int bottomLeftY;
		int bottomLeftX;
		int bottomRightY;
		int bottomRightX;
		int topRightX;
		int topRightY;
		private GameElement element;
		public ElementCoordinates(GameElement element) {
			this.element = element;
		}
		public int getBottomLeftY() {
			return bottomLeftY;
		}
		public void setBottomLeftY(int bottomLeftY) {
			this.bottomLeftY = bottomLeftY;
		}
		public int getBottomLeftX() {
			return bottomLeftX;
		}
		public void setBottomLeftX(int bottomLeftX) {
			this.bottomLeftX = bottomLeftX;
		}
		public int getBottomRightY() {
			return bottomRightY;
		}
		public void setBottomRightY(int bottomRightY) {
			this.bottomRightY = bottomRightY;
		}
		public int getBottomRightX() {
			return bottomRightX;
		}
		public void setBottomRightX(int bottomRightX) {
			this.bottomRightX = bottomRightX;
		}
		public int getTopRightX() {
			return topRightX;
		}
		public void setTopRightX(int topRightX) {
			this.topRightX = topRightX;
		}
		public int getTopRightY() {
			return topRightY;
		}
		public void setTopRightY(int topRightY) {
			this.topRightY = topRightY;
		}
		public GameElement getElement() {
			return element;
		}
		public void setElement(GameElement element) {
			this.element = element;
		}
		
		
		
	}
}

