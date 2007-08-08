package java5gameprogramming;

import java.awt.Rectangle;

public class Bullet extends BaseVectorShape {

	/**
	 *
	 * @return
	 */
	public Rectangle getBounds() {
		Rectangle r;
		r = new Rectangle((int) getX(), (int) getY(), 1, 1);
		return r;
	}

	/**
	 *
	 *
	 */
	public Bullet() {
		setShape(new Rectangle(0, 0, 1, 1));
		setAlive(false);
	}

}
