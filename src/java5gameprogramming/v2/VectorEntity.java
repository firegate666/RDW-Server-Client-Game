package java5gameprogramming.v2;

import java.awt.Shape;

public abstract class VectorEntity extends BaseGameEntity {

	protected Shape shape;

	public VectorEntity() {
		setShape(null);
	}

	/**
	 * @return shape
	 */
	public Shape getShape() {
		return shape;
	}

	/**
	 * @param shape Festzulegender shape
	 */
	public void setShape(Shape shape) {
		this.shape = shape;
	}

}
