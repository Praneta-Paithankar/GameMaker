package com.dimension;

import java.io.Serializable;

public class Dimensions implements Serializable{
	
	private int width;
	private int height;
	
	public Dimensions(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public Dimensions(int radius) {
		this.height = radius * 2;
		this.width = radius * 2;
	}
	
	public int getWidth() {
		return width;
	}
  
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}