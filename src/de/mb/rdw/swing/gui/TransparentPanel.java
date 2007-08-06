package de.mb.rdw.swing.gui;

import java.awt.Component;
import java.awt.LayoutManager;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

import de.mb.util.Utils;

public class TransparentPanel extends JPanel {
	final static Logger log = Logger.getLogger(TransparentPanel.class);
	public TransparentPanel(LayoutManager layout) {
		super(layout);
		initialize();
	}

	public TransparentPanel() {
		super();
		initialize();
	}

	protected void initialize() {
		setOpaque(false);
	}

	/**
	 * add component and set component bounds
	 * 
	 * @param comp
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void add(Component comp, int x, int y, int width, int height) {
		comp.setBounds(x, y, width, height);
		add(comp);
	}

}
