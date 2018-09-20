/**
 *This interface is implements by all the components of our  view(UI)*/
package com.infrastruture;

import java.awt.Graphics;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public interface Element extends Serializable {
	 void draw(Graphics g);
	 void reset();  
	 void addComponent(Element e);
	 void removeComponent(Element e);
	 void save(ObjectOutputStream op);
	 Element load(ObjectInputStream ip);
}
