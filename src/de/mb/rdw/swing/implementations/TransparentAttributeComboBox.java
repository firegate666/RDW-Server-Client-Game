package de.mb.rdw.swing.implementations;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import org.apache.log4j.Logger;

import de.mb.rdw.model.GameCharacter;
import de.mb.rdw.swing.TransparentComboBox;
import de.mb.rdw.swing.interfaces.ICharacterAttributeComponent;

public class TransparentAttributeComboBox extends TransparentComboBox implements ICharacterAttributeComponent {
	final static Logger log = Logger
			.getLogger(TransparentAttributeComboBox.class);

	protected final GameCharacter character;

	protected final String key;

	/**
	 *
	 * @param items
	 * @param c
	 * @param k
	 */
	public TransparentAttributeComboBox(Object[] items, GameCharacter c,
			String k) {
		super(items);
		character = c;
		key = k;
		this.setSelectedItem(character.getAttribute(key));
		this.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				String value = (String) e.getItem();
				character.setAttribute(key, value);
			}

		});

	}
}
