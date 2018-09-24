package com.strategy;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import com.components.GameElement;
import com.dimension.Coordinate;
import com.dimension.Dimensions;
import com.infrastruture.Drawable;

public class DrawOvalImage implements Drawable,Serializable{
	
	@Override
	public void draw(GameElement element, Graphics g) {
		Dimensions dimension = element.getPosition();
		Coordinate coordinate = element.getCoordinate();
	
		Graphics2D g2d = (Graphics2D) g.create();
		Image dimg = element.getImage().getScaledInstance(dimension.getWidth(), dimension.getHeight(),
		        Image.SCALE_SMOOTH);
		g2d.setClip(new Ellipse2D.Float(coordinate.getX(), coordinate.getY(), dimension.getWidth(), dimension.getWidth()));
		
		g2d.drawImage(dimg, coordinate.getX(), coordinate.getY(),dimension.getWidth(), dimension.getHeight(), null); 
		g2d.dispose();
	}
}