package de.mb.engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import com.sun.j3d.utils.applet.JMainFrame;

/**
 * @author Jonathan S. Harbour
 * @author Marco Behnke
 */
public class RdwGameEngine extends Game {

	public final static int SPRITE_AVATAR = 0;

	public final static int SPRITE_STATIC_OBJECT = 1;

	/**
	 * show applet in Swing Frame
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		RdwGameEngine rdw = new RdwGameEngine(800, 600);
		JMainFrame frame = new JMainFrame(rdw, 800, 600);
	}

	public RdwGameEngine(int width, int height) {
		super(60, width, height);
	}

	/**
	 * backgroundmap
	 */
	protected ImageEntity background;

	/**
	 * avatar position x
	 */
	protected int avatar_x = 0;

	/**
	 * avatar position y
	 */
	protected int avatar_y = 0;

	/**
	 * position offset x for scrolling background
	 */
	protected double offset_x = 0;

	/**
	 * position offset y for scrolling background
	 */
	protected double offset_y = 0;

	/**
	 * default walking speed
	 */
	protected int default_speed = 2;

	/**
	 * is key event "key up"?
	 */
	protected boolean keyup = false;

	/**
	 * is key event "key down"?
	 */
	protected boolean keydown = false;

	/**
	 * is key event "key left"?
	 */
	protected boolean keyleft = false;

	/**
	 * is key event "key right"?
	 */
	protected boolean keyright = false;

	/**
	 * is key event "key ctrl"?
	 */
	protected boolean keyctrl = false;

	/**
	 * check key events
	 * 
	 */
	protected void checkkeys() {
		int speed = default_speed;
		if (keyctrl)
			speed = speed * 2;

		if (keyup)
			walk(-speed);
		else if (keydown)
			walk(speed);
		else
			walk(0);

		if (keyleft)
			turn(5);
		if (keyright)
			turn(-5);
	}

	/**
	 * @see Game#gameKeyDown(int)
	 */
	public void gameKeyDown(int keyCode) {
		if (keyCode == KeyEvent.VK_UP)
			keyup = true;
		if (keyCode == KeyEvent.VK_DOWN)
			keydown = true;
		if (keyCode == KeyEvent.VK_LEFT)
			keyleft = true;
		if (keyCode == KeyEvent.VK_RIGHT)
			keyright = true;
		if (keyCode == KeyEvent.VK_CONTROL)
			keyctrl = true;

	}

	/**
	 * @see Game#gameKeyUp(int)
	 */
	public void gameKeyUp(int keyCode) {
		if (keyCode == KeyEvent.VK_UP)
			keyup = false;
		if (keyCode == KeyEvent.VK_DOWN)
			keydown = false;

		if (keyCode == KeyEvent.VK_LEFT)
			keyleft = false;
		if (keyCode == KeyEvent.VK_RIGHT)
			keyright = false;

		if (keyCode == KeyEvent.VK_CONTROL)
			keyctrl = false;

	}

	/**
	 * @see Game#gameMouseDown()
	 */
	public void gameMouseDown() {
		// TODO Automatisch erstellter Methoden-Stub

	}

	/**
	 * @see Game#gameMouseMove()
	 */
	public void gameMouseMove() {
		// TODO Automatisch erstellter Methoden-Stub

	}

	/**
	 * @see Game#gameMouseUp()
	 */
	public void gameMouseUp() {
		// TODO Automatisch erstellter Methoden-Stub

	}

	/**
	 * @see Game#gameRefreshScreen()
	 */
	public void gameRefreshScreen() {
		Graphics2D g2d = graphics();

		// draw the background
		g2d.drawImage(background.getImage(), 0, 0, getScreenWidth() - 1,
				getScreenHeight() - 1, 0 + (int) offset_x, 0 + (int) offset_y,
				getScreenWidth() - 1 + (int) offset_x, getScreenHeight() - 1
						+ (int) offset_y, this);

		g2d.setFont(new Font("Verdana", Font.BOLD, 16));
		g2d.setColor(Color.BLACK);
		g2d.drawString("Aktuelle Position: " + avatar_x + ":" + avatar_y, 10,
				16);
		g2d.setFont(new Font("Verdana", Font.BOLD, 10));
		g2d.setColor(Color.WHITE);
		g2d.drawString(
				"Steuerung mit Pfeiltasten (vorwärts, rückwärts, drehen)", 10,
				getScreenHeight() - 30);
		g2d.drawString("Rennen mit STRG", 10, getScreenHeight() - 20);
		g2d.drawString("Slide: " + offset_x + ":" + offset_y, 10,
				getScreenHeight() - 10);
	}

	/**
	 * turn avatar
	 * 
	 * @param angle
	 */
	public void turn(int angle) {
		AnimatedSprite avatar = (AnimatedSprite) sprites().get(0);
		avatar.setFaceAngle(avatar.faceAngle() - angle);
		if (avatar.faceAngle() < 0)
			avatar.setFaceAngle(360 - angle);

	}

	/**
	 * walk avatar
	 * 
	 * @param speed
	 */
	public void walk(int speed) {

		// avatar is always the first sprite in the linked list
		AnimatedSprite avatar = (AnimatedSprite) sprites().get(0);

		if (speed == 0) { // stop animation if stop walking
			avatar.setVelocity(new Point2D(0, 0));
			avatar.setAnimationDirection(0);
		} else if (speed > 0)
			avatar.setAnimationDirection(1);
		else
			avatar.setAnimationDirection(-1);

		// up arrow adds thrust to ship (1/10 normal speed)
		avatar.setMoveAngle(avatar.faceAngle() - 90);

		// calculate the X and Y velocity based on angle
		double velx = calcAngleMoveX(avatar.moveAngle()) * speed;
		double vely = calcAngleMoveY(avatar.moveAngle()) * speed;
		avatar.setVelocity(new Point2D(velx, vely));

	}

	/**
	 * @see Game#gameShutdown()
	 */
	public void gameShutdown() {
		// TODO Automatisch erstellter Methoden-Stub

	}

	/**
	 * @see Game#gameStartup()
	 */
	public void gameStartup() {
		background = new ImageEntity(this);
		background.load("/resource/sprites/tanaris.jpg");

		ImageEntity shipImage = new ImageEntity(this);
		shipImage.load("/resource/sprites/avatar.png");

		ImageEntity castleImage = new ImageEntity(this);
		castleImage.load("/resource/sprites/castle.gif");

		AnimatedSprite ship = new AnimatedSprite(this, graphics());
		ship.setSpriteType(RdwGameEngine.SPRITE_AVATAR);
		ship.setAnimImage(shipImage.getImage());
		ship.setFrameWidth(48);
		ship.setFrameHeight(48);
		ship.setTotalFrames(1);
		ship.setColumns(1);
		ship.setPosition(new Point2D(getScreenWidth() / 2,
				getScreenHeight() / 2));
		ship.setAlive(true);
		ship.setFrameDelay(4);
		sprites().add(ship);

		AnimatedSprite castle = new AnimatedSprite(this, graphics());
		castle.setSpriteType(RdwGameEngine.SPRITE_STATIC_OBJECT);
		castle.setImage(castleImage.getImage());
		castle.setPosition(new Point2D(738 * 2, 888 * 2));
		castle.setAlive(true);
		sprites().add(castle);
	}

	/**
	 * @see Game#gameTimedUpdate()
	 */
	public void gameTimedUpdate() {
		checkkeys();
	}

	/**
	 * @see Game#spriteCollision(AnimatedSprite, AnimatedSprite)
	 */
	public void spriteCollision(AnimatedSprite spr1, AnimatedSprite spr2) {
	}

	/**
	 * @see Game#spriteDraw(AnimatedSprite)
	 */
	public void spriteDraw(AnimatedSprite sprite) {
	}

	/**
	 * @see Game#spriteDying(AnimatedSprite)
	 */
	public void spriteDying(AnimatedSprite sprite) {
		// TODO Automatisch erstellter Methoden-Stub

	}

	/**
	 * @see Game#spriteUpdate(AnimatedSprite)
	 */
	public void spriteUpdate(AnimatedSprite sprite) {
		warp(sprite);
	}

	/**
	 * handle sprite repositioning and offset due to scrolling
	 * 
	 * @param spr
	 */
	public void warp(AnimatedSprite spr) {

		// player av
		if (spr.spriteType() == RdwGameEngine.SPRITE_AVATAR) {
			// create some shortcut variables
			int w = spr.frameWidth() - 1;
			int h = spr.frameHeight() - 1;

			// stop sprite at screen edge
			if (spr.position().X() < 100)
				spr.position().setX(100);
			if (spr.position().Y() < 100)
				spr.position().setY(100);

			if (spr.position().X() > background.width() - 100) {
				spr.position().setX(background.width() - 100);
			} else if (spr.position().X() > getScreenWidth() - 100) {
				offset_x = spr.position().X() - (getScreenWidth() - 100);
			}
			if (spr.position().Y() > background.height() - 100) {
				spr.position().setY(background.height() - 100);
			} else if (spr.position().Y() > getScreenHeight() - 100) {
				offset_y = spr.position().Y() - (getScreenHeight() - 100);
			}
		}

		// if is avatar store avatar position
		if (spr.spriteType() == RdwGameEngine.SPRITE_AVATAR) {
			avatar_x = (int) spr.position().X();
			avatar_y = (int) spr.position().Y();
		}

		spr.setDrawposition(new Point2D((int) (spr.position().X() - offset_x),
				(int) (spr.position().Y() - offset_y)));
	}
}
