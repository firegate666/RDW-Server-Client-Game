package de.mb.rdw.model;

import java.io.Serializable;

/**
 * type
 *
 * @author mbehnke
 *
 */
public class GameCharacterType implements Serializable {

	/**
	 * get available classes for character
	 *
	 * @param character
	 * @return
	 */
	public static String[] getAvailableCharacters(GameCharacter character) {
		if (!character.hasMainAttributesSet())
			return new String[] {};
		return new String[] { "Alchimist", "Amazone", "Barbar", "Barde",
				"Diabolist", "Druide", "Elf", "Erendar", "Gladiator",
				"Glaubenskämpfer", "Händler", "Halbork", "Hasardeur", "Heiler",
				"Herr über den träumenden Geist", "Hexe", "Illusionist",
				"Kopfgeldjäger", "Magier", "Pilger", "Priester", "Ritter",
				"Schwertmeister", "Söldner", "Warlord", "Wanderer",
				"Wolfsjäger", "Zwerg", };
	}

}