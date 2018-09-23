package com.helper;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.apache.log4j.Logger;

import com.infrastruture.Event;

public class Sound implements Event{
	
	public static final Logger logger = Logger.getLogger(Sound.class);
	private String filePath;
	private Clip clip;
	
	public Sound(String filePath) {
		this.filePath = filePath;
	}
	
	public void loadClip() {
		try {
			URL url = this.getClass().getClassLoader().getResource(filePath);
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void executeEvent() {
		loadClip();
		if (clip.isRunning())
			clip.stop();
		clip.setFramePosition(0);
		clip.start();
	}
}
