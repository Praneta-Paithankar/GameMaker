/**
 *This panel holds all the graphic objects like brick, ball and paddle*/
package com.ui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.behavior.FlowLayoutBehavior;
import com.infrastruture.AbstractPanel;
import com.infrastruture.Constants;
import com.infrastruture.Element;


@SuppressWarnings("serial")
public class GamePanel extends AbstractPanel implements Element {
	protected static Logger log = Logger.getLogger(GamePanel.class);
	private BufferedImage image;
	private ArrayList<Element> elements;
	
	public GamePanel()
	{

	    elements = new ArrayList<Element>();
        try {
            image = ImageIO.read(new File("./src/com/image/nature.jpg"));
            image = resize(image, Constants.BOARD_PANEL_HEIGHT, Constants.BOARD_PANEL_WIDTH);
        } catch (IOException e) {
            // TODO Auto-generated catch block
        	log.error(e.getMessage());
        }
        setLayout();
	}

	
	
	public void setLayout() {
		setLayoutBehavior(new FlowLayoutBehavior());
		performUpdateLayout(this, Constants.BOARD_PANEL_WIDTH,Constants.BOARD_PANEL_HEIGHT);
	}
	
	private BufferedImage resize(BufferedImage img, int width, int height) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }
	
	public ArrayList<Element> getElements(){
		return elements;
	}

	
	@Override
	public void paintComponent(Graphics g){

		super.paintComponent(g);
		if (image != null) {
	        g.drawImage(image, 0, 0, this);
	    }
		for(Element element : elements)
		{
			element.draw(g);
		}
	}

	

	@Override
	public void draw(Graphics g) {
		repaint();
	}

	@Override
	public void reset() {
		for(Element element : elements) {
			element.reset();
		}
	}

	public void addComponent(Element e) {
		//this.add((Component)e);
		elements.add(e);
	}
	

	@Override
	public void removeComponent(Element e) {
		elements.remove(e);
	}



	@Override
	public void save(ObjectOutputStream op) {
		// TODO Auto-generated method stub
		for (Element element : elements) {
			element.save(op);
		}
	}

	@Override
	public Element load(ObjectInputStream ip) {
		// TODO Auto-generated method stub
		ArrayList<Element> loadComponents = new ArrayList<>();
		for (Element element : elements) {
			loadComponents.add(element.load(ip));
		}
		elements.clear();
		elements.addAll(loadComponents);
		return null;
	}
}
