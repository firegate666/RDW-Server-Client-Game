package de.mb.rdw.swing.implementations;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

import org.apache.log4j.Logger;

import de.mb.rdw.model.GameCharacter;
import de.mb.rdw.swing.TransparentTextField;
import de.mb.rdw.swing.interfaces.ICharacterAttributeComponent;

public class TransparentAttributeTextField extends TransparentTextField implements ICharacterAttributeComponent {
	final static Logger log = Logger
			.getLogger(TransparentAttributeTextField.class);

	protected final GameCharacter character;

	protected final String key;

	protected final int maxlength;

	/**
	 *
	 * @param c
	 * @param k
	 * @param l
	 */
	public TransparentAttributeTextField(GameCharacter c, String k, int l) {
		this(c, k, l, JTextField.RIGHT);
	}

	/**
	 *
	 * @param c
	 * @param k
	 */
	public TransparentAttributeTextField(GameCharacter c, String k) {
		this(c, k, -1, JTextField.RIGHT);
	}

	/**
	 *
	 * @param c
	 * @param k
	 * @param l
	 * @param align
	 */
	public TransparentAttributeTextField(GameCharacter c, String k, int l,
			int align) {
		super();
		setHorizontalAlignment(align);
		character = c;
		key = k;
		maxlength = l;
		setText(character.getAttribute(key));
		this.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
				if ((maxlength > 0) && (getText().length() > maxlength))
					setText(character.getAttribute(key));
				else
					character.setAttribute(key, getText());
			}

			public void keyTyped(KeyEvent e) {
			}

		});
	}

}