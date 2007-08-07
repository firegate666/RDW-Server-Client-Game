package de.mb.rdw.swing;

import javax.swing.JLabel;

import org.apache.log4j.Logger;

import de.mb.util.Utils;

public class TransparentLabel extends JLabel {
	final static Logger log = Logger.getLogger(TransparentLabel.class);
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
