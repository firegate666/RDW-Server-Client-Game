package java5gameprogramming;

public class Point2D {

	protected double x, y;

	public Point2D(int x, int y) {
		setX(x);
		setY(y);
	}

	public Point2D(float x, float y) {
		setX(x);
		setY(y);
	}

	public Point2D(double x, double y) {
		setX(x);
		setY(y);
	}

	/**
	 * @return x
	 */
	public double X() {
		return x;
	}

	/**
	 * @param x
	 *            Festzulegender x
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * @return y
	 */
	public double Y() {
		return y;
	}

	/**
	 * @param y
	 *            Festzulegender y
	 */
	public void setY(double y) {
		this.y = y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setX(float x) {
		this.x = x;
	}
}
