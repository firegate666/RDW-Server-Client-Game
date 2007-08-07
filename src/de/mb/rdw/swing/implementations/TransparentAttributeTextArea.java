package de.mb.rdw.swing.implementations;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

import org.apache.log4j.Logger;

import de.mb.rdw.model.GameCharacter;
import de.mb.rdw.swing.TransparentTextArea;
import de.mb.rdw.swing.interfaces.ICharacterAttributeComponent;

public class TransparentAttributeTextArea extends TransparentTextArea implements ICharacterAttributeComponent {
	final static Logger log = Logger.getLogger(TransparentAttributeTextArea.class);

	protected final GameCharacter character;

	protected final String key;

	public TransparentAttributeTextArea(GameCharacter c, String k) {
		super();
		character = c;
		key = k;
		setText(character.getAttribute(key));
		this.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
				character.setAttribute(key, getText());
			}

			public void keyTyped(KeyEvent e) {
			}

		});
	}
}
