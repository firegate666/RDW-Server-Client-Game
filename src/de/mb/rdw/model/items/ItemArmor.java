package de.mb.rdw.model.items;

import de.mb.rdw.model.exceptions.IllegalTypeException;

public class ItemArmor extends ItemHelm {
	public static final int TYPE_NK = 1;

	public static final int TYPE_WK = 2;

	public static final int TYPE_LR = 3;
	public static final int TYPE_KH = 4;
	public static final int TYPE_SP = 4;
	public static final int TYPE_PP = 4;
	public static final int TYPE_VH = 4;

	protected int bbe;

	protected int b_kap;

	protected int type;
	/**
	 * @return b_kap
	 */
	public int getB_kap() {
		return b_kap;
	}

	/**
	 * @param b_kap
	 *            Festzulegender b_kap
	 */
	public void setB_kap(int b_kap) {
		this.b_kap = b_kap;
	}

	/**
	 * @return bbe
	 */
	public int getBbe() {
		return bbe;
	}

	/**
	 * @param bbe
	 *            Festzulegender bbe
	 */
	public void setBbe(int bbe) {
		this.bbe = bbe;
	}

	/**
	 * @return type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type Festzulegender type
	 */
	public void setType(int type)  throws IllegalTypeException {
		this.type = type;
	}

}
