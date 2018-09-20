package com.strategy;

import java.awt.Graphics;

import com.components.GameElement;
import com.dimension.Coordinate;
import com.dimension.Dimension;
import com.infrastruture.Drawable;

public class DrawOvalColor implements Drawable{

	@Override
	public void draw(GameElement element, Graphics g) {
		Dimension dimension = element.getPosition();
		Coordinate coordinate = element.getCoordinate();
		
		g.setColor(element.getColor());
		g.drawOval(coordinate.getX(), coordinate.getY(), dimension.getWidth(), dimension.getHeight());
	}

}
