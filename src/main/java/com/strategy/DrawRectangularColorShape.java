package com.strategy;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.components.GameElement;
import com.dimension.Coordinate;
import com.dimension.Dimensions;
import com.infrastruture.Drawable;

public class DrawRectangularColorShape implements Drawable {

	@Override
	public void draw(GameElement element, Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Dimensions dimension = element.getPosition();
		Coordinate coordinate = element.getCoordinate();
		
		g2d.setColor(element.getColor());
		g2d.fill(new Rectangle(coordinate.getX(), coordinate.getY(), dimension.getWidth(), dimension.getHeight()));
	}

}
