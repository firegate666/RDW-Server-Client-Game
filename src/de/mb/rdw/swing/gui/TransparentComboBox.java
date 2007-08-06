package de.mb.rdw.swing.gui;

import javax.swing.JComboBox;

import org.apache.log4j.Logger;

import de.mb.util.Utils;

public class TransparentComboBox extends JComboBox {
	final static Logger log = Logger.getLogger(TransparentComboBox.class);
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
