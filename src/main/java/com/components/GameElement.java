package com.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;

import com.dimension.Coordinate;
import com.dimension.Dimension;
import com.infrastruture.Action;
import com.infrastruture.Drawable;
import com.infrastruture.Element;

public class GameElement implements Element{

	protected static Logger log = Logger.getLogger(GameElement.class);
	private Dimension position;
	private Coordinate coordinate;
	private Color color;
	private BufferedImage image;
	private Action action;
	private Drawable drawable;
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
		return drawable;
	}

	public void setDraw(Drawable draw) {
		this.drawable = draw;
	}

	@Override
	public void draw(Graphics g) {
		drawable.draw(this, g);
		
	}

	@Override
	public void reset(Coordinate c) {
		coordinate= c;
	}

	@Override
	public void addComponent(Element e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeComponent(Element e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(ObjectOutputStream op) {
		try {
			op.writeObject(this);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public Element load(ObjectInputStream ip) {
		try {
			GameElement obj = (GameElement)ip.readObject();
			return obj;
		} catch (ClassNotFoundException | IOException e) {
			log.error(e.getMessage());
		}
		return null;
	}
}
