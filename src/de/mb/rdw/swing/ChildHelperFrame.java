package de.mb.rdw.swing;

import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.JLabel;

import org.apache.log4j.Logger;

import de.mb.rdw.HelpProperties;

/**
 * help view
 * @author mbehnke
 *
 */
public class ChildHelperFrame extends ChildFrame {
	final static Logger log = Logger.getLogger(ChildHelperFrame.class);
	public ChildHelperFrame(Frame owner, String title) {
		super(owner, title, "/resource/images/pergament.jpg");
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
