package de.mb.rdw.swing.gui;

import javax.swing.JTextField;

import org.apache.log4j.Logger;

import de.mb.util.Utils;

public class TransparentTextField extends JTextField {
	final static Logger log = Logger.getLogger(TransparentTextField.class);
	public TransparentTextField() {
		super();
		initialize();
	}

	public TransparentTextField(String text) {
		super(text);
		initialize();
	}

	protected void initialize() {
		setOpaque(false);
	}

}
