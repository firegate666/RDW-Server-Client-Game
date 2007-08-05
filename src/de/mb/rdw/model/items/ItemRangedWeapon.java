package de.mb.rdw.model.items;

public class ItemRangedWeapon extends ItemAbstractWeapon {

	protected int range;
	protected float speed;
	/**
	 * @return range
	 */
	public int getRange() {
		return range;
	}
	/**
	 * @param range Festzulegender range
	 */
	public void setRange(int range) {
		this.range = range;
	}
	/**
	 * @return speed
	 */
	public float getSpeed() {
		return speed;
	}
	/**
	 * @param speed Festzulegender speed
	 */
	public void setSpeed(float speed) {
		this.speed = speed;
	}

}
