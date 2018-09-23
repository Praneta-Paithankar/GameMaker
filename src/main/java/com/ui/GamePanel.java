/**
 *This panel holds all the graphic objects like brick, ball and paddle*/
package com.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
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
import com.components.GameElement;
import com.controller.DesignController;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;


@SuppressWarnings("serial")
public class GamePanel extends AbstractPanel implements Element {
	protected static Logger log = Logger.getLogger(GamePanel.class);
	private BufferedImage image;
	private ArrayList<Element> elementList;
	private DesignController designController;
	private GamePanel that;

	public GamePanel()
	{
		this.that = this;
		this.designController = null;
		setBorder("Game Board");
	    elementList = new ArrayList<Element>();
        try {
            image = ImageIO.read(new File("./src/com/image/nature.jpg"));
            image = resize(image, Constants.GAME_PANEL_HEIGHT, Constants.GAME_PANEL_WIDTH);
        } catch (IOException e) {
            // TODO Auto-generated catch block
        	log.error(e.getMessage());
        }
        
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                super.mouseClicked(me);
                boolean flag = false;
                for (Element s: elementList) {
                	GameElement temp = (GameElement) s;
                	flag = temp.getRectBounds().contains(me.getPoint());
            	
                    if (flag) {//check if mouse is clicked within shape
                    	that.designController.pushToPreview(temp);
                        //we can either just print out the object class name
                        System.out.println("Clicked a "+s.getClass().getName());
                    }
                }
            }
        });
        
        
        
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
	public void setDesignController(DesignController controller){
		// TODO Auto-generated method stub
		this.designController = controller;
	}
}
