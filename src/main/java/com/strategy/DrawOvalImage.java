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

public class DrawOvalImage implements Drawable,Serializable{
	
	@Override
	public void draw(GameElement element, Graphics g) {
		Dimensions dimension = element.getPosition();
		Coordinate coordinate = element.getCoordinate();
		
		Image tmp = element.getImage();
		tmp.getScaledInstance(dimension.getWidth(), dimension.getHeight(), Image.SCALE_SMOOTH);
        
		BufferedImage resized = new BufferedImage(dimension.getWidth(), dimension.getHeight(), BufferedImage.TYPE_INT_ARGB);
        
        Graphics2D g2d = (Graphics2D) g;
        g2d = resized.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN));
        g2d.drawImage(tmp, 0, 0, null);
	}
}
