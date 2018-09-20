package com.components;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.dimension.Coordinate;
import com.dimension.Dimension;
import com.infrastruture.Action;
import com.infrastruture.Drawable;

public class GameElement {

	private Dimension position;
	private Coordinate coordinate;
	private Color color;
	private BufferedImage image;
	private Action action;
	private Drawable draw;
	private boolean isVisible; 
	private int velX;
	private int velY;
	
	public GameElement() {
		
	}
	
	public int getVelX() {
		return velX;
	}

	public void setVelX(int velX) {
		this.velX = velX;
	}

	public int getVelY() {
		return velY;
	}

	public void setVelY(int velY) {
		this.velY = velY;
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}
	
	public int getX() {
		return coordinate.getX();
	}
	
	public void setX(int x) {
		this.coordinate.setX(x);
	}
	
	public int getY() {
		return coordinate.getY();
	}
	
	public void setY(int y) {
		this.coordinate.setY(y);
	}
	
	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	
	public Dimension getPosition() {
		return position;
	}

	public void setPosition(Dimension position) {
		this.position = position;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public Drawable getDraw() {
		return draw;
	}

	public void setDraw(Drawable draw) {
		this.draw = draw;
	}
}
