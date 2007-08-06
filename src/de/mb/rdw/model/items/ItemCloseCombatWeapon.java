package de.mb.rdw.model.items;

import de.mb.rdw.model.exceptions.IllegalTypeException;

/**
 * close combat weapon
 * 
 * @author mbehnke
 *
 */
public class ItemCloseCombatWeapon extends ItemAbstractWeapon {

	public static final int TYPE_SW = 1;

	public static final int TYPE_S = 2;

	public static final int TYPE_LSK = 3;

	public static final int TYPE_SSK = 4;

	public static final int TYPE_ZH = 5;

	public static final int TYPE_STG = 6;

	public static final int TYPE_SPECIAL = 7;

	protected int type;

	/**
	 * @return type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            Festzulegender type
	 */
	public void setType(int type) throws IllegalTypeException {
		this.type = type;
	}
}
