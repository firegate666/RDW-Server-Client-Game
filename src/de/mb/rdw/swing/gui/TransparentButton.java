package de.mb.rdw.swing.gui;

import javax.swing.JButton;

import org.apache.log4j.Logger;

import de.mb.util.Utils;

public class TransparentButton extends JButton {
	final static Logger log = Logger.getLogger(TransparentButton.class);
	public TransparentButton() {
		super();
		initialize();
	}

	protected void initialize() {
		setContentAreaFilled(false);
		setBorder(null);
	}

}
