package com.commands;

import java.io.File;
import java.io.Serializable;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.apache.log4j.Logger;

import com.infrastruture.Command;
import com.infrastruture.Event;

public class SoundEvent implements Command{
	
	public static final Logger logger = Logger.getLogger(SoundEvent.class);
	private String filePath;
	private Clip clip;
	
	public SoundEvent(String filePath) {
		this.filePath = filePath;
	}
	

	@Override
	public void execute() {
		try {
			File f = new File(filePath);
			if(f.exists() && !f.isDirectory()) { 
				URL url = this.getClass().getClassLoader().getResource(filePath);
				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
				clip = AudioSystem.getClip();
				clip.open(audioInputStream);
				if (clip.isRunning())
					clip.stop();
				clip.setFramePosition(0);
				clip.start();
			}
			else {
				logger.error("Sound file is missing");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
	}

	@Override
	public void undo() {
		
	}
}
