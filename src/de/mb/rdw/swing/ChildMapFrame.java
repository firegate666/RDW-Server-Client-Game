package de.mb.rdw.swing;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.mb.util.Utils;

public class ChildMapFrame extends ChildFrame {

	public ChildMapFrame(String title) {
		super(title, "/resource/images/map.png");
		setBounds(10, 10, 312, 220);
	}
	
}
