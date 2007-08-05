package de.mb.rdw.swing;

import java.awt.BorderLayout;

import javax.swing.JLabel;

import org.apache.log4j.Logger;

import de.mb.rdw.HelpProperties;

public class ChildHelperFrame extends ChildFrame {
	final static Logger log = Logger.getLogger(ChildHelperFrame.class);
	public ChildHelperFrame(String title) {
		super(title);
		banner.setLayout(null);
		JLabel text = new JLabel();
		text = new JLabel(HelpProperties.getString("Helper.start")); //$NON-NLS-1$
		text.setOpaque(false);
		text.setBounds(50, 50, getWidth()-100, getHeight()-100);
		text.setVerticalAlignment(JLabel.TOP);
		text.setHorizontalAlignment(JLabel.LEFT);
		addComp(text, BorderLayout.CENTER);
	}

}
