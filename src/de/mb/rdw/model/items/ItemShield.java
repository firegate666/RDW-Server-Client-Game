package de.mb.rdw.model.items;

import de.mb.rdw.model.exceptions.IllegalTypeException;

/**
 * shield
 * 
 * @author mbehnke
 *
 */
public class ItemShield extends Item {
	public static final int TYPE_IRON = 1;

	public static final int TYPE_LEATHER = 2;

	public static final int TYPE_WOOD = 3;

	protected int kap_k_ze;

	protected int vw_bonus;

	protected int vw_malus;

	protected int type;

	/**
	 * @return material
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param material
	 *            Festzulegender material
	 */
	public void setType(int material) throws IllegalTypeException {
		this.type = material;
	}

	/**
	 * @return kap_k_ze
	 */
	public int getKap_k_ze() {
		return kap_k_ze;
	}

	/**
	 * @param kap_k_ze
	 *            Festzulegender kap_k_ze
	 */
	public void setKap_k_ze(int kap_k_ze) {
		this.kap_k_ze = kap_k_ze;
	}

	/**
	 * @return vw_bonus
	 */
	public int getVw_bonus() {
		return vw_bonus;
	}

	/**
	 * @param vw_bonus
	 *            Festzulegender vw_bonus
	 */
	public void setVw_bonus(int vw_bonus) {
		this.vw_bonus = vw_bonus;
	}

	/**
	 * @return vw_malus
	 */
	public int getVw_malus() {
		return vw_malus;
	}

	/**
	 * @param vw_malus
	 *            Festzulegender vw_malus
	 */
	public void setVw_malus(int vw_malus) {
		this.vw_malus = vw_malus;
	}
}
