package de.mb.rdw.swing.gui;

import javax.swing.JLabel;

public class TransparentLabel extends JLabel {

	public TransparentLabel() {
		super();
		initialize();
	}

	public TransparentLabel(String text) {
		super(text);
		initialize();
	}

	protected void initialize() {
		setOpaque(false);
	}

}
