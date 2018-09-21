package com.strategy;

import java.awt.Graphics;

import com.components.GameElement;
import com.dimension.Coordinate;
import com.dimension.Dimensions;
import com.infrastruture.Drawable;

public class DrawRectangularColorShape implements Drawable {

	@Override
	public void draw(GameElement element, Graphics g) {
		Dimensions dimension = element.getPosition();
		Coordinate coordinate = element.getCoordinate();
		
		g.setColor(element.getColor());
		g.drawOval(coordinate.getX(), coordinate.getY(), dimension.getWidth(), dimension.getHeight());
	}

}
