package com.components;

import java.awt.Font;
import java.awt.Graphics;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.apache.log4j.Logger;

import com.dimension.Coordinate;
import com.infrastruture.Element;

public class ScoreBoard implements Element, Serializable{

	protected static final Logger logger = Logger.getLogger(ScoreBoard.class);
	private int score;
	private Coordinate position;
	
	public ScoreBoard(Coordinate position) {
		this.position = position;
	}
	
	@Override
	public void draw(Graphics g) {
		g.setFont(new Font("Arial", Font.BOLD, 40));
		g.drawString(String.valueOf(score), position.getX(), position.getY());
	}

	@Override
	public void reset() {
		this.score = 0;
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
			logger.error(e.getLocalizedMessage());
		}
	}

	@Override
	public Element load(ObjectInputStream ip) {
		try {
			ScoreBoard obj = (ScoreBoard)ip.readObject();
			return obj;
		} catch (ClassNotFoundException | IOException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
