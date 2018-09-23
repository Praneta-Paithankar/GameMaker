package com.strategy;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import com.components.GameElement;
import com.dimension.Coordinate;
import com.dimension.Dimensions;
import com.infrastruture.Drawable;

public class DrawRectangularImage implements Drawable,Serializable{
	
	
	@Override
	public void draw(GameElement element, Graphics g) {
		Dimensions dimension = element.getPosition();
		Coordinate coordinate = element.getCoordinate();
		
		g.drawImage(element.getImage(), coordinate.getX(), coordinate.getY(),dimension.getWidth(), dimension.getHeight(), null); 
	}	
}
