package de.mb.engine;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.KeyEvent;

import javax.swing.DefaultDesktopManager;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;

import com.sun.j3d.utils.applet.JMainFrame;

import de.mb.rdw.swing.ChildCharacterFrame;
import de.mb.rdw.swing.ChildFrame;
import de.mb.rdw.swing.ChildMapFrame;

/**
 * @author Jonathan S. Harbour
 * @author Marco Behnke
 */
public class RdwGameEngine extends Game {

	public final static int SPRITE_AVATAR = 0;

	public final static int SPRITE_STATIC_OBJECT = 1;

    protected ImageEntity[] barImage = new ImageEntity[2];
    protected ImageEntity barFrame;

    protected JMainFrame parentWindow;

    ChildFrame characterFrame;

	/**
	 * show applet in Swing Frame
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		RdwGameEngine rdw = new RdwGameEngine(1024, 768);
		JMainFrame frame = new JMainFrame(rdw, 1024, 768);
		rdw.setParentWindow(frame);
		//frame.setAlwaysOnTop(true);
	}

	public RdwGameEngine(int width, int height) {
		super(60, width, height);
		JDesktopPane desk = new JDesktopPane();
		desk.setDoubleBuffered(true);
		desk.setOpaque(false);
		desk.setDesktopManager(new DefaultDesktopManager());
		desk.setLayout(null);
		setContentPane(desk);

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

	protected double world_x = 2000;

	protected double world_y = 2000;

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

	protected boolean keyc = false;

	protected boolean showDebug = true;

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
		if (keyc) {
			if (characterFrame == null)
				characterFrame = new ChildCharacterFrame(parentWindow, "Charakterblatt");
			characterFrame.setVisible(!characterFrame.isVisible());
			keyc = false;
		}
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

		if (keyCode == KeyEvent.VK_D)
			showDebug = !showDebug;

		if (keyCode == KeyEvent.VK_C)
			keyc = true;
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

	protected ChildMapFrame child;

	/**
	 * @see Game#gameRefreshScreen()
	 */
	public void gameRefreshScreen() {
		if (gamePaused())
			return;
		// draw the background
		g2d.drawImage(background.getImage(), 0, 0, getScreenWidth() - 1,
				getScreenHeight() - 1, 0 + (int) offset_x, 0 + (int) offset_y,
				getScreenWidth() - 1 + (int) offset_x, getScreenHeight() - 1
						+ (int) offset_y, this);

        if (barFrame != null) {
			g2d.drawImage(barFrame.getImage(), getScreenWidth() - 132, 18, this);
	        for (int n = 0; n < 20; n++) {
	            int dx = getScreenWidth() - 130 + n * 5;
	            g2d.drawImage(barImage[0].getImage(), dx, 20, this);
	        }
	        g2d.drawImage(barFrame.getImage(), getScreenWidth() - 132, 33, this);
	        for (int n = 0; n < 20; n++) {
	            int dx = getScreenWidth() - 130 + n * 5;
	            g2d.drawImage(barImage[1].getImage(), dx, 35, this);
	        }
        }

		if (showDebug) {
			g2d.setColor(Color.WHITE);
			g2d.setFont(new Font("Verdana", Font.BOLD, 10));
			g2d.drawString("Aktuelle Position: " + avatar_x + ":" + avatar_y,
					10, getScreenHeight() - 40);
			g2d.drawString(
					"Steuerung mit Pfeiltasten (vorwärts, rückwärts, drehen)",
					10, getScreenHeight() - 30);
			g2d.drawString("Rennen mit STRG", 10, getScreenHeight() - 20);
			g2d.drawString("Slide: " + (int) offset_x + ":" + (int) offset_y,
					10, getScreenHeight() - 10);
		}
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
		pauseGame();
		applet().requestFocus(); // get focus, so we don't need to click to start playing
		applet().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		background = new ImageEntity(this);
		background.load("/resource/sprites/tanaris.gif");

		//load the health/shield bars
        barFrame = new ImageEntity(this);
        barFrame.load("/resource/sprites/barframe.png");
        barImage[0] = new ImageEntity(this);
        barImage[0].load("/resource/sprites/bar_health.png");
        barImage[1] = new ImageEntity(this);
        barImage[1].load("/resource/sprites/bar_shield.png");

		ImageEntity shipImage = new ImageEntity(this);
		shipImage.load("/resource/sprites/running_s.png");

		ImageEntity castleImage = new ImageEntity(this);
		castleImage.load("/resource/sprites/castle.gif");

		AnimatedSprite ship = new AnimatedSprite(this, graphics());
		ship.setSpriteType(RdwGameEngine.SPRITE_AVATAR);
		ship.setAnimImage(shipImage.getImage());
		ship.setFrameWidth(96);
		ship.setFrameHeight(96);
		ship.setTotalFrames(8);
		ship.setColumns(8);
		ship.setPosition(new Point2D(200, 200));
		ship.setAlive(true);
		ship.setFrameDelay(4);
		sprites().add(ship);

		AnimatedSprite castle = new AnimatedSprite(this, graphics());
		castle.setSpriteType(RdwGameEngine.SPRITE_STATIC_OBJECT);
		castle.setImage(castleImage.getImage());
		castle.setPosition(new Point2D(738 * 2, 888 * 2));
		castle.setAlive(true);
		sprites().add(castle);

		AnimatedSprite castle2 = new AnimatedSprite(this, graphics());
		castle2.setSpriteType(RdwGameEngine.SPRITE_STATIC_OBJECT);
		castle2.setImage(castleImage.getImage());
		castle2.setPosition(new Point2D(838 * 2, 988 * 2));
		castle2.setAlive(true);
		sprites().add(castle2);
		resumeGame();
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
			if (spr.position().X() < 25)
				spr.position().setX(25);
			if (spr.position().Y() < 25)
				spr.position().setY(25);

			if (spr.position().X() >= world_x - 100) // hard limit
				spr.position().setX(world_x - 100);
			else if (spr.position().X() >= world_x
					- getScreenWidth() / 2) { // stop scrolling
				// spr.position().setX(background.width() - getScreenWidth()/2);
			} else if (spr.position().X() > getScreenWidth() - getScreenWidth()
					/ 2) { // update offset
				offset_x = spr.position().X()
						- (getScreenWidth() - getScreenWidth() / 2);
			}

			if (spr.position().Y() >= world_y - 100) // hard
																	// limit
				spr.position().setY(world_y - 100);
			else if (spr.position().Y() >= world_y
					- getScreenHeight() / 2) { // stop scrolling
				// spr.position().setY(background.height() -
				// getScreenHeight()/2);
			} else if (spr.position().Y() > getScreenHeight()
					- getScreenHeight() / 2) { // update offset
				offset_y = spr.position().Y()
						- (getScreenHeight() - getScreenHeight() / 2);
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

	/**
	 * @return parentWindow
	 */
	public JMainFrame getParentWindow() {
		return parentWindow;
	}

	/**
	 * @param parentWindow Festzulegender parentWindow
	 */
	public void setParentWindow(JMainFrame parentWindow) {
		this.parentWindow = parentWindow;
	}

}
