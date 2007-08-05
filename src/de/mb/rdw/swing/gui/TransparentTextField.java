package de.mb.rdw.swing.gui;

import javax.swing.JTextField;

public class TransparentTextField extends JTextField {

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
