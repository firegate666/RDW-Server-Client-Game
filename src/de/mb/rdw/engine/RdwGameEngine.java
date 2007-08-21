package de.mb.rdw.engine;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.xml.parsers.FactoryConfigurationError;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.sun.j3d.utils.applet.MainFrame;

import de.mb.rdw.GameController;
import de.mb.rdw.swing.ChildCharacterFrame;
import de.mb.rdw.swing.ChildFrame;
import de.mb.util.WebstartFileProvider;

/**
 * @author Jonathan S. Harbour
 * @author Marco Behnke
 */

public class RdwGameEngine extends Game {
	final static Logger log = Logger.getLogger(RdwGameEngine.class);

	protected GameController controller;

	public final static int SPRITE_AVATAR = 0;

	public final static int SPRITE_STATIC_OBJECT = 1;

	protected ImageEntity[] barImage = new ImageEntity[2];

	protected ImageEntity barFrame;

	protected MainFrame parentWindow;

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

	protected double world_y = 1352;

	/**
	 * default walking speed
	 */
	protected int default_speed = 1;

	protected int rotationAngle = 5;

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
	 * show applet in Swing Frame
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		LogManager.resetConfiguration();
		try {
			DOMConfigurator.configure(WebstartFileProvider.getFile(
					"log4j-config.xml").toURL());
			RdwGameEngine rdw = new RdwGameEngine(1024, 768, new GameController());
			MainFrame frame = new MainFrame(rdw, 1024, 768);
			rdw.setParentWindow(frame);
			// frame.setAlwaysOnTop(true);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (FactoryConfigurationError e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public RdwGameEngine(int width, int height, GameController controller) {
		super(60, width, height);
		this.controller = controller;
		this.controller.startMessageReceiver();
	}

	/**
	 * check key events
	 *
	 */
	protected void checkkeys() {
		// speed control
		int speed = default_speed;
		if (keyctrl)
			speed = speed * 2;

		// move control
		if (keyup)
			walk(-speed);
		else if (keydown)
			walk(speed);
		else
			walk(0);

		// turn control
		if (keyleft) {
			keyleft = false;
			turn(rotationAngle);
		}
		if (keyright) {
			keyright = false;
			turn(-rotationAngle);
		}

		// additionals
		if (keyc) {
			ChildCharacterFrame characterFrame = controller.getCharacterFrame(parentWindow);
			characterFrame.setVisible(!characterFrame.isVisible());
			keyc = false;
			this.requestFocus();
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
		if (keyCode == KeyEvent.VK_CONTROL)
			keyctrl = true;
		if (keyCode == KeyEvent.VK_LEFT)
			keyleft = true;
		if (keyCode == KeyEvent.VK_RIGHT)
			keyright = true;

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
			g2d
					.drawImage(barFrame.getImage(), getScreenWidth() - 132, 18,
							this);
			for (int n = 0; n < 20; n++) {
				int dx = getScreenWidth() - 130 + n * 5;
				g2d.drawImage(barImage[0].getImage(), dx, 20, this);
			}
			g2d
					.drawImage(barFrame.getImage(), getScreenWidth() - 132, 33,
							this);
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
			g2d.drawString("Rennen mit STRG, Charakterblatt mit C", 10,
					getScreenHeight() - 20);
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
		if (avatar.faceAngle() == 360)
			avatar.setFaceAngle(0);

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

		if (speed != 0)
			controller.sendNewMessage("AVATAR:"+avatar.position().X()+":"+avatar.position().Y()+":"+avatar.faceAngle());
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
		log.info("Game Startup");
		pauseGame();
		applet().requestFocus(); // get focus, so we don't need to click to
									// start playing
		applet().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		background = new ImageEntity(this);
		background.load("/resource/sprites/tanaris.gif");

		// load the health/shield bars
		barFrame = new ImageEntity(this);
		barFrame.load("/resource/sprites/barframe.png");
		barImage[0] = new ImageEntity(this);
		barImage[0].load("/resource/sprites/bar_health.png");
		barImage[1] = new ImageEntity(this);
		barImage[1].load("/resource/sprites/bar_shield.png");

		ImageEntity castleImage = new ImageEntity(this);
		castleImage.load("/resource/sprites/castle.gif");

		DirectionalAnimatedSprite player_avatar = new DirectionalAnimatedSprite(this,
				graphics());
		player_avatar.setSpriteType(RdwGameEngine.SPRITE_AVATAR);
		player_avatar.setFrameWidth(144);
		player_avatar.setFrameHeight(144);
		player_avatar.setTotalFrames(12);
		player_avatar.setColumns(12);
		player_avatar.setPosition(new Point2D(200, 200));
		player_avatar.setAlive(true);
		player_avatar.setFrameDelay(4);
		player_avatar.loadImageSequence("/resource/sequences/walking_n0000.png", 180);
		player_avatar.loadImageSequence("/resource/sequences/walking_s0000.png", 0);
		player_avatar.loadImageSequence("/resource/sequences/walking_e0000.png", 270);
		player_avatar.loadImageSequence("/resource/sequences/walking_w0000.png", 90);
		player_avatar.set4ImageMapping();
		sprites().add(player_avatar);

		AnimatedSprite castle = new AnimatedSprite(this, graphics());
		castle.setSpriteType(RdwGameEngine.SPRITE_STATIC_OBJECT);
		castle.setImage(castleImage.getImage());
		castle.setPosition(new Point2D(50,50));
		castle.setAlive(true);
		sprites().add(castle);

		AnimatedSprite castle2 = new AnimatedSprite(this, graphics());
		castle2.setSpriteType(RdwGameEngine.SPRITE_STATIC_OBJECT);
		castle2.setImage(castleImage.getImage());
		castle2.setPosition(new Point2D(300,450));
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
			else if (spr.position().X() >= world_x - getScreenWidth() / 2) { // stop
																				// scrolling
				// spr.position().setX(background.width() - getScreenWidth()/2);
			} else if (spr.position().X() > getScreenWidth() - getScreenWidth()
					/ 2) { // update offset
				offset_x = spr.position().X()
						- (getScreenWidth() - getScreenWidth() / 2);
			}

			if (spr.position().Y() >= world_y - 100) // hard
				// limit
				spr.position().setY(world_y - 100);
			else if (spr.position().Y() >= world_y - getScreenHeight() / 2) { // stop
																				// scrolling
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
	public MainFrame getParentWindow() {
		return parentWindow;
	}

	/**
	 * @param parentWindow
	 *            Festzulegender parentWindow
	 */
	public void setParentWindow(MainFrame parentWindow) {
		this.parentWindow = parentWindow;
	}

}
