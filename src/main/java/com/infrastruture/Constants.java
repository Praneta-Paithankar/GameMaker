/**
 *This class is used to define all the constants used in our application*/
package com.infrastruture;

import java.awt.Color;

public class Constants {
	public final static int FRAME_WIDTH = 900;
	public final static int  FRAME_HEIGHT = 900;
	public final static int MAIN_PANEL_WIDTH =  900;
	public final static int MAIN_PANEL_HEIGHT =  750;
	public final static int TIMER_PANEL_WIDTH =  150;
	public final static int TIMER_PANEL_HEIGHT =  750;
	public final static int BOARD_PANEL_WIDTH =  750;
	public final static int BOARD_PANEL_HEIGHT =  750;

	public final static int TIMER_COUNT = 25;
	
	public final static int BRICK_NO = 4;
	public final static int BRICK_WIDTH = 75;
	public final static int BRICK_HEIGHT = 30;
	public final static int BRICK_START_Y = 100;
	public final static int BRICK_START_X = 0;
	public final static int BRICK_DISTANCE_X = 75;
	public final static int BRICK_DISTANCE_Y = 50;
	public final static Color BRICK_COLOR =  new Color(78,104,130);
	public final static Color BRICK_BORDER = new Color(62,83,104);	
	
	public final static int BALL_POS_X = 15;
	public final static int  BALL_DELTA_X = 5;
	public final static int BALL_DELTA_Y = 5;
	public final static int BALL_POS_Y = 200;
	public final static int BALL_RADIUS = 15;
	public final static Color BALL_COLOR = new Color(155,94,155);
	
	public final static int PADDLE_POS_X = 350; 
	public final static int PADDLE_POS_Y = 600;
	public final static int PADDLE_WIDTH = 200 ;
	public final static int PADDLE_HEIGHT = 40;
	public final static int PADDLE_DELTA_X = 20;
	public final static Color PADDLE_COLOR = new Color(91,33,91);
	
}
