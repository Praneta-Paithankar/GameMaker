package com.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import com.infrastruture.Action;
import com.infrastruture.Drawable;

public class GameElement {
	private int width;
	private int height;
	private Dimension position;
	private Color color;
	private BufferedImage image;
	private Action action;
	private Drawable draw;
	private boolean isVisible; 
	
	public GameElement() {
		
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
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
