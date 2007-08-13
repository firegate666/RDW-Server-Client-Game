package java5gameprogramming.v2;

import java.awt.Polygon;
import java.awt.Rectangle;

public class Asteroid extends VectorEntity {

	protected int[] astx = { -20, -13, 0, 20, 22, 20, 12, 2, -10, -22, -16 };

	protected int[] asty = { 20, 23, 17, 20, 16, -20, -22, -14, -17, -20, -5 };

	protected double rotVal;

	/**
	 * @return rotVal
	 */
	public double getRotVal() {
		return rotVal;
	}

	/**
	 * @param rotVal
	 *            Festzulegender rotVal
	 */
	public void setRotVal(double rotVal) {
		this.rotVal = rotVal;
	}

	/**
	 *
	 */
	public Rectangle getBounds() {
		Rectangle r;
		r = new Rectangle((int) getX() - 20, (int) getY() - 20, 40, 40);
		return r;
	}

	public Asteroid(){
		setShape(new Polygon(astx, asty, astx.length));
		setAlive(true);
		setRotVal(0.0);
	}
}
