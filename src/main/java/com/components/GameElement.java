package com.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.apache.log4j.Logger;

import com.dimension.Coordinate;
import com.dimension.Dimensions;
import com.infrastruture.Action;
import com.infrastruture.Drawable;
import com.infrastruture.Element;
import com.infrastruture.MoveType;

public class GameElement implements Element,Serializable{

	protected static Logger log = Logger.getLogger(GameElement.class);
	private String name;
	private Dimensions dimension;
	private Coordinate coordinate;
	private Coordinate startingPosition;
	private Color color;
	private BufferedImage image;
	private Action action;
	private Drawable drawable;
	private boolean isVisible; 
	private int velX;
	private int velY;
	private MoveType moveType;

	public GameElement(Dimensions dimension, Coordinate coordinate, String name, MoveType moveType) {
		this.dimension = dimension;
		this.coordinate = coordinate;
		this.color = Color.BLACK;
		this.moveType = moveType;
		this.name = name;
	}
	
	public MoveType getMoveType() {
		return moveType;
	}

	public void setMoveType(MoveType moveType) {
		this.moveType = moveType;
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
	
	public int getWidth() {
		return dimension.getWidth();
	}
	
	public void setWidth(int width) {
		this.dimension.setWidth(width);
	}
	
	public int getHeight() {
		return dimension.getHeight();
	}
	
	public void setHeight(int height) {
		this.dimension.setHeight(height);
	}
	
	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	
	public Dimensions getPosition() {
		return dimension;
	}

	public void setPosition(Dimensions position) {
		this.dimension = position;
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
		if(isVisible)
			drawable.draw(this, g);
	}

	@Override
	public void reset() {
		coordinate= startingPosition;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
