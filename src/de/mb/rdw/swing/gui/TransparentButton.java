package de.mb.rdw.swing.gui;

import javax.swing.JButton;

public class TransparentButton extends JButton {

	public TransparentButton() {
		super();
		initialize();
	}

	protected void initialize() {
		setContentAreaFilled(false);
		setBorder(null);
	}

}
