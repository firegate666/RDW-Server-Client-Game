package de.mb.rdw.swing;

import java.awt.Frame;

import org.apache.log4j.Logger;

/**
 * map view
 *
 * @author mbehnke
 *
 */
public class ChildMapFrame extends ChildFrame {
	final static Logger log = Logger.getLogger(ChildMapFrame.class);
	public ChildMapFrame(Frame owner, String title) {
		super(owner, title, "/resource/images/map.png");
		setBounds(800, 600, 312, 220);
	}

}
