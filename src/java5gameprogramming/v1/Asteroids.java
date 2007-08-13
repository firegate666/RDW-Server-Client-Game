package java5gameprogramming.v1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Random;
import java5gameprogramming.SoundClip;

import javax.swing.JApplet;

import org.apache.log4j.Logger;

import com.sun.j3d.utils.applet.MainFrame;

public class Asteroids extends JApplet implements Runnable, KeyListener {

	final static Logger log = Logger.getLogger(Asteroids.class);

	protected Thread gameloop;

	protected BufferedImage backbuffer;

	protected Graphics2D g2d;

	protected boolean showBounds = false;

	protected int ASTEROIDS = 20;

	protected Asteroid[] ast = new Asteroid[ASTEROIDS];

	protected int BULLETS = 10;

	protected Bullet[] bullet = new Bullet[BULLETS];

	protected int currentBullet = 0;

	protected Ship ship = new Ship();

	protected AffineTransform identity = new AffineTransform();

	protected Random rand = new Random();

	protected SoundClip shoot;

	protected SoundClip explode;

	public void init() {
		log.info("init()");
		backbuffer = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
		g2d = backbuffer.createGraphics();

		ship.setX(320);
		ship.setY(240);

		for (int i = 0; i < BULLETS; i++) {
			bullet[i] = new Bullet();
		}

		for (int i = 0; i < ASTEROIDS; i++) {
			Asteroid temp = new Asteroid();
			temp.setRotVal(rand.nextInt(3) + 1);
			temp.setX((double) rand.nextInt(600) + 20);
			temp.setY((double) rand.nextInt(440) + 20);
			temp.setMoveAngle(rand.nextInt(360));
			double ang = temp.getMoveAngle() - 90;
			temp.setVelX(calcAngleMoveX(ang));
			temp.setVelY(calcAngleMoveY(ang));
			ast[i] = temp;
		}

		// shoot = new SoundClip("shoot.au");
		// explode = new SoundClip("explode.au");

		addKeyListener(this);
	}

	public void update(Graphics g) {
		log.info("update()");
		g2d.setTransform(identity);
		g2d.setPaint(Color.BLACK);
		g2d.fillRect(0, 0, getWidth(), getHeight());

		g2d.setColor(Color.WHITE);
		g2d.drawString("Ship: " + Math.round(ship.getX()) + ","
				+ Math.round(ship.getY()), 5, 10);
		g2d.drawString("Move angle: " + Math.round(ship.getMoveAngle() + 90),
				5, 25);
		g2d.drawString("Face angle: " + Math.round(ship.getFaceAngle()), 5, 40);

		drawShip();
		drawBullets();
		drawAsteroids();

		paint(g);
	}

	public void drawShip() {
		g2d.setTransform(identity);
		g2d.translate(ship.getX(), ship.getY());
		g2d.rotate(Math.toRadians(ship.getFaceAngle()));
		g2d.setColor(Color.ORANGE);
		g2d.fill(ship.getShape());
		if (showBounds) {
			g2d.setTransform(identity);
			g2d.setColor(Color.BLUE);
			g2d.draw(ship.getBounds());
		}
	}

	public void drawBullets() {
		for (int i = 0; i < BULLETS; i++) {
			if (bullet[i].isAlive()) {
				g2d.setTransform(identity);
				g2d.translate(bullet[i].getX(), bullet[i].getY());
				g2d.setColor(Color.MAGENTA);
				g2d.draw(bullet[i].getShape());
			}

		}
	}

	public void drawAsteroids() {
		for (int i = 0; i < ASTEROIDS; i++) {
			if (ast[i].isAlive()) {
				g2d.setTransform(identity);
				g2d.translate(ast[i].getX(), ast[i].getY());
				g2d.rotate(Math.toRadians(ast[i].getMoveAngle()));
				g2d.setColor(Color.DARK_GRAY);
				g2d.fill(ast[i].getShape());
				if (showBounds) {
					g2d.setTransform(identity);
					g2d.setColor(Color.BLUE);
					g2d.draw(ast[i].getBounds());
				}
			}

		}
	}

	public void paint(Graphics g) {
		log.info("paint()");
		g.drawImage(backbuffer, 0, 0, this);
	}

	public void start() {
		log.info("start()");
		gameloop = new Thread(this);
		gameloop.start();
	}

	public void run() {
		log.info("run()");
		Thread t = Thread.currentThread();
		while (t == gameloop) {
			try {
				gameUpdate();
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			repaint();
		}
	}

	public void stop() {
		gameloop = null;
	}

	protected void gameUpdate() {
		updateShip();
		updateBullets();
		updateAsteroids();
		checkCollisions();
	}

	public void updateShip() {
		ship.incX(ship.getVelX());
		if (ship.getX() < -10)
			ship.setX(getWidth() + 10);
		else if (ship.getX() > getWidth() + 10)
			ship.setX(-10);

		ship.incY(ship.getVelY());
		if (ship.getY() < -10)
			ship.setY(getHeight() + 10);
		else if (ship.getY() > getHeight() + 10)
			ship.setY(-10);
	}

	public void updateBullets() {
		for (int i = 0; i < BULLETS; i++) {
			if (bullet[i].isAlive()) {
				bullet[i].incX(bullet[i].getVelX());
				if (bullet[i].getX() < 0 || bullet[i].getX() > getWidth())
					bullet[i].setAlive(false);
				bullet[i].incY(bullet[i].getVelY());
				if (bullet[i].getY() < 0 || bullet[i].getY() > getHeight())
					bullet[i].setAlive(false);
			}

		}
	}

	public void updateAsteroids() {
		for (int i = 0; i < ASTEROIDS; i++) {
			if (ast[i].isAlive()) {
				ast[i].incX(ast[i].getVelX());
				if (ast[i].getX() < -20)
					ast[i].setX(getWidth() + 20);
				else if (ast[i].getX() > getWidth() + 20)
					ast[i].setX(-20);
				ast[i].incY(ast[i].getVelY());
				if (ast[i].getY() < -20)
					ast[i].setY(getHeight() + 20);
				else if (ast[i].getY() > getHeight() + 20)
					ast[i].setY(-20);
				ast[i].incMoveAngle(ast[i].getRotVal());
				if (ast[i].getMoveAngle() < 0)
					ast[i].setMoveAngle(360 - ast[i].getRotVal());
				else if (ast[i].getMoveAngle() > 360)
					ast[i].setMoveAngle(ast[i].getRotVal());
			}

		}
	}

	public void checkCollisions() {
		for (int m = 0; m < ASTEROIDS; m++) {
			if (ast[m].isAlive()) {
				for (int n = 0; n < BULLETS; n++) {
					if (bullet[n].isAlive()) {
						if (ast[m].getBounds().contains(bullet[n].getX(),
								bullet[n].getY())) {
							bullet[n].setAlive(false);
							ast[m].setAlive(false);
							// explode.play();
							continue;
						}
					}

				}
				if (ast[m].getBounds().intersects(ship.getBounds())) {
					ast[m].setAlive(false);
					// explode.play();
					ship.setX(320);
					ship.setY(240);
					ship.setFaceAngle(0);
					ship.setVelX(0);
					ship.setVelY(0);
					continue;
				}
			}

		}
	}

	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch (keyCode) {
		case KeyEvent.VK_LEFT:
			ship.incFaceAngle(-5);
			if (ship.getFaceAngle() < 0)
				ship.setFaceAngle(360 - 5);
			break;
		case KeyEvent.VK_RIGHT:
			ship.incFaceAngle(5);
			if (ship.getFaceAngle() > 360)
				ship.setFaceAngle(5);
			break;
		case KeyEvent.VK_UP:
			ship.setMoveAngle(ship.getFaceAngle() - 90);
			ship.incVelX(calcAngleMoveX(ship.getMoveAngle()) * 0.1);
			ship.incVelY(calcAngleMoveY(ship.getMoveAngle()) * 0.1);
			break;
		case KeyEvent.VK_CONTROL:
		case KeyEvent.VK_ENTER:
		case KeyEvent.VK_SPACE:
			currentBullet++;
			if (currentBullet > BULLETS - 1)
				currentBullet = 0;
			bullet[currentBullet].setAlive(true);
			bullet[currentBullet].setX(ship.getX());
			bullet[currentBullet].setY(ship.getY());
			bullet[currentBullet].setMoveAngle(ship.getFaceAngle() - 90);
			double angle = bullet[currentBullet].getMoveAngle();
			double svx = ship.getVelX();
			double svy = ship.getVelY();
			bullet[currentBullet].setVelX(svx + calcAngleMoveX(angle) * 2);
			bullet[currentBullet].setVelY(svy + calcAngleMoveY(angle) * 2);
			// shoot.play();
			break;
		case KeyEvent.VK_B:
			showBounds = !showBounds;
			break;
		}
	}

	public double calcAngleMoveX(double angle) {
		return (double) (Math.cos(angle * Math.PI / 180));
	}

	public double calcAngleMoveY(double angle) {
		return (double) (Math.sin(angle * Math.PI / 180));
	}

	public void keyReleased(KeyEvent e) {
		// TODO Automatisch erstellter Methoden-Stub

	}

	public void keyTyped(KeyEvent e) {
		// TODO Automatisch erstellter Methoden-Stub

	}

	public static void main(String[] args) {
		MainFrame frame = new MainFrame(new Asteroids(), 640, 480);
	}

}
