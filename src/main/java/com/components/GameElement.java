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
	
	public GameElement() {
		
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
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
