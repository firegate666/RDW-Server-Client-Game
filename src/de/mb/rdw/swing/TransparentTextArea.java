package de.mb.rdw.swing;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

import org.apache.log4j.Logger;

public class TransparentTextArea extends JTextArea {
	final static Logger log = Logger.getLogger(TransparentTextArea.class);

	protected JScrollPane scrollPane;

	public TransparentTextArea() {
		super();
		initialize();
	}

	public TransparentTextArea(String text) {
		super(text);
		initialize();
	}

	protected void initialize() {
		setOpaque(false);
		scrollPane = new JScrollPane();
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.getViewport().setView(this);
		scrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		setLineWrap(true);
		setWrapStyleWord(true); // only wrap word if line wrap
		setBorder(new EtchedBorder());
		setCaretPosition(0);
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

}
