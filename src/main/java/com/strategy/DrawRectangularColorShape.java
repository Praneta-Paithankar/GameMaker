package com.strategy;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import com.components.GameElement;
import com.dimension.Coordinate;
import com.dimension.Dimensions;
import com.infrastruture.Drawable;

public class DrawRectangularColorShape implements Drawable,Serializable {

	@Override
	public void draw(GameElement element, Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Dimensions dimension = element.getPosition();
		Coordinate coordinate = element.getCoordinate();	
		g2d.setColor(element.getColor());
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Rectangle2D shape = new Rectangle2D.Float();
		shape.setFrame(coordinate.getX(), coordinate.getY(), dimension.getWidth(), dimension.getHeight());
		g2d.fill(shape);
	}

}
