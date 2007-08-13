package java5gameprogramming.v2;

import java.applet.Applet;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class ImageEntity extends BaseGameEntity {

	protected Image image;
	protected Applet applet;

	protected AffineTransform at;
	protected Graphics2D g2d;

	public Graphics2D getGraphics() {
		return g2d;
	}

	public ImageEntity(Applet a) {
		applet = a;
		setImage(null);
		setAlive(true);
	}

	/**
	 * @return image
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * @param image Festzulegender image
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	public int width() {
		return getImage().getWidth(applet);
	}

	public int height() {
		return getImage().getHeight(applet);
	}

	public double getCenterX() {
		return getX() + width() / 2;
	}
	public double getCenterY() {
		return getY() + height() / 2;
	}
	public void setGraphics(Graphics2D g) {
		g2d = g;
	}
	protected URL getURL(String filename) {
		URL url = null;
		try {
			url = this.getClass().getResource(filename);
		} catch (Exception e) {}
		return url;
	}

	public void load(String filename) {
		setImage(applet.getImage(getURL(filename)));
		while(getImage().getWidth(applet)<= 0);
		double x = applet.getWidth()/2 - width()/2;
		double y = applet.getHeight()/2 - height()/2;
		at = AffineTransform.getTranslateInstance(x, y);
	}

	public void transform() {
		at.setToIdentity();
		at.translate((int)getX()+width()/2,(int)getY()+height()/2);
		at.rotate(Math.toRadians(getFaceAngle()));
		at.translate(-width()/2,-height()/2);
	}

	public void draw() {
		g2d.drawImage(getImage(), at, applet);
	}

	public Rectangle getBounds() {
		return new Rectangle((int)getX(),(int)getY(),width(),height());
	}

	/**
	 * @return applet
	 */
	public Applet getApplet() {
		return applet;
	}

	/**
	 * @return at
	 */
	public AffineTransform getAt() {
		return at;
	}

}
