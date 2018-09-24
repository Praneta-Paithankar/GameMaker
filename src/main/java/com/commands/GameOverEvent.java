package com.commands;

import java.io.Serializable;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.controller.MainController;
import com.infrastruture.Command;
import com.ui.GUI;

public class GameOverEvent implements Command {

	private MainController mainController;

	public GameOverEvent(MainController mainController) {
		this.mainController = mainController;
	}
	@Override
	public void execute() {
		mainController.gameOver();
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub

	}

}
