package de.mb.rdw.swing;

import javax.swing.JButton;

import org.apache.log4j.Logger;

import de.mb.util.Utils;

public class TransparentButton extends JButton {
	final static Logger log = Logger.getLogger(TransparentButton.class);
	public TransparentButton(String text) {
		super(text);
		initialize();
	}

	public TransparentButton() {
		this("");
	}

	protected void initialize() {
		setContentAreaFilled(false);
		setBorder(null);
	}

}
