package de.mb.rdw.swing.gui;

import javax.swing.JComboBox;

public class TransparentComboBox extends JComboBox {

	public TransparentComboBox() {
		super();
		initialize();
	}

	public TransparentComboBox(Object[] items) {
		super(items);
		initialize();
	}

	protected void initialize() {
		setOpaque(false);
	}

}
