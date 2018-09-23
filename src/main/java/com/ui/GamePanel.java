/**
 *This panel holds all the graphic objects like brick, ball and paddle*/
package com.ui;

import java.awt.Color;
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
import com.dimension.Coordinate;
import com.ui.AbstractPanel;
import com.infrastruture.Constants;
import com.infrastruture.Element;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;


@SuppressWarnings("serial")
public class GamePanel extends AbstractPanel implements Element {
	protected static Logger log = Logger.getLogger(GamePanel.class);
	private BufferedImage image;
	private ArrayList<Element> elementList;
	

	public GamePanel()
	{
		setBorder("Game Board");
	    elementList = new ArrayList<Element>();
        try {
            image = ImageIO.read(new File("./src/com/image/nature.jpg"));
            image = resize(image, Constants.GAME_PANEL_HEIGHT, Constants.GAME_PANEL_WIDTH);
        } catch (IOException e) {
            // TODO Auto-generated catch block
        	log.error(e.getMessage());
        }
        setLayout();
	}
	public void setBorder(String title) {
		Border raisedbevel = BorderFactory.createRaisedBevelBorder();
		this.setBorder(raisedbevel);
		TitledBorder titledBorder = BorderFactory.createTitledBorder(title);
	    titledBorder.setTitleJustification(TitledBorder.CENTER);
	    
	    titledBorder.setTitlePosition(TitledBorder.BELOW_TOP);
	    Border compound = BorderFactory.createCompoundBorder(
                raisedbevel, titledBorder);
	    this.setBorder(compound);
	}
	
	
	public void setLayout() {
		setLayoutBehavior(new FlowLayoutBehavior());
		performUpdateLayout(this, Constants.GAME_PANEL_WIDTH,Constants.GAME_PANEL_HEIGHT);
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
		return elementList;
	}
	
	public void setElement(ArrayList<Element> elementList) {
		this.elementList = elementList;
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if (image != null) {
	        g.drawImage(image, 0, 0, this);
	    }
		for(Element element : elementList)
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
		for(Element element : elementList) {
			element.reset();
		}
	}

	public void addComponent(Element e) {
		elementList.add(e);
	}
	

	@Override
	public void removeComponent(Element e) {
		elementList.remove(e);
	}



	@Override
	public void save(ObjectOutputStream op) {
		for (Element element : elementList) {
			element.save(op);
		}
	}

	@Override
	public Element load(ObjectInputStream ip) {
		ArrayList<Element> loadComponents = new ArrayList<>();
		for (Element element : elementList) {
			loadComponents.add(element.load(ip));
		}
		elementList.clear();
		elementList.addAll(loadComponents);
		return null;
	}
}
