package java5gameprogramming.v1;

import java.awt.Rectangle;
import java.awt.Shape;

public abstract class BaseVectorShape {
	protected Shape shape;

	protected boolean alive;

	protected double x, y;

	protected double velX, velY;

	protected double moveAngle, faceAngle;

	/**
	 * @return alive
	 */
	public boolean isAlive() {
		return alive;
	}

	/**
	 * @param alive
	 *            Festzulegender alive
	 */
	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	/**
	 * @return faceAngle
	 */
	public double getFaceAngle() {
		return faceAngle;
	}

	/**
	 * @param faceAngle
	 *            Festzulegender faceAngle
	 */
	public void setFaceAngle(double faceAngle) {
		this.faceAngle = faceAngle;
	}

	/**
	 * @return moveAngle
	 */
	public double getMoveAngle() {
		return moveAngle;
	}

	/**
	 * @param moveAngle
	 *            Festzulegender moveAngle
	 */
	public void setMoveAngle(double moveAngle) {
		this.moveAngle = moveAngle;
	}

	/**
	 * @return shape
	 */
	public Shape getShape() {
		return shape;
	}

	/**
	 * @param shape
	 *            Festzulegender shape
	 */
	public void setShape(Shape shape) {
		this.shape = shape;
	}

	/**
	 * @return velX
	 */
	public double getVelX() {
		return velX;
	}

	/**
	 * @param velX
	 *            Festzulegender velX
	 */
	public void setVelX(double velX) {
		this.velX = velX;
	}

	/**
	 * @return velY
	 */
	public double getVelY() {
		return velY;
	}

	/**
	 * @param velY
	 *            Festzulegender velY
	 */
	public void setVelY(double velY) {
		this.velY = velY;
	}

	/**
	 * @return x
	 */
	public double getX() {
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
	public double getY() {
		return y;
	}

	/**
	 * @param y
	 *            Festzulegender y
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 *
	 * @param i
	 */
	public void incX(double i) {
		x += i;
	}

	/**
	 *
	 * @param i
	 */
	public void incY(double i) {
		y += i;
	}

	/**
	 *
	 * @param i
	 */
	public void incVelX(double i) {
		velX += i;
	}

	/**
	 *
	 * @param i
	 */
	public void incVelY(double i) {
		velY += i;
	}

	/**
	 *
	 * @param i
	 */
	public void incFaceAngle(double i) {
		faceAngle += i;
	}

	/**
	 *
	 * @param i
	 */
	public void incMoveAngle(double i) {
		moveAngle += i;
	}

	/**
	 *
	 *
	 */
	public BaseVectorShape() {
		setShape(null);
		setAlive(false);
		setX(0.0);
		setY(0.0);
		setVelX(0.0);
		setVelY(0.0);
		setMoveAngle(0.0);
		setFaceAngle(0.0);
	}

	abstract Rectangle getBounds();
}
