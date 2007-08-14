package de.mb.rdw.swing;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import de.mb.util.Utils;

/**
 * map view
 * 
 * @author mbehnke
 *
 */
public class ChildMapFrame extends ChildFrame {
	final static Logger log = Logger.getLogger(ChildMapFrame.class);
	public ChildMapFrame(String title) {
		super(title, "/resource/images/map.png");
		setBounds(800, 600, 312, 220);
	}
	
}
