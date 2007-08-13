package de.mb.engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import com.sun.j3d.utils.applet.MainFrame;

/*****************************************************
* Beginning Java 5 Game Programming
* by Jonathan S. Harbour
* GALACTIC WAR, Chapter 11
*****************************************************/

public class RdwGameEngine extends Game {

	public static void main(String[] args) {
		RdwGameEngine rdw = new RdwGameEngine(800, 600);
		MainFrame frame = new MainFrame(rdw, 800, 600);
	}
	
	public final static int SPRITE_AVATAR = 0;
	public final static int SPRITE_STATIC_OBJECT = 1;
	
	ImageEntity background;

	int x=0,y=0;
	
	double slidex = 0;
	double slidey = 0;
	
	int default_speed = 2;

	public RdwGameEngine(int width, int height) {
		super(60, width, height);
	}
	
	boolean keyup = false;
	boolean keydown = false;
	boolean keyleft = false;
	boolean keyright = false;
	boolean keyctrl = false;

	public void checkkeys() {
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
			turnShip(5);
		if (keyright)
			turnShip(-5);
	}

	@Override
	void gameKeyDown(int keyCode) {
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

	@Override
	void gameKeyUp(int keyCode) {
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

	@Override
	void gameMouseDown() {
		// TODO Automatisch erstellter Methoden-Stub

	}

	@Override
	void gameMouseMove() {
		// TODO Automatisch erstellter Methoden-Stub

	}

	@Override
	void gameMouseUp() {
		// TODO Automatisch erstellter Methoden-Stub

	}

	@Override
	void gameRefreshScreen() {
        Graphics2D g2d = graphics();

        //draw the background
        g2d.drawImage(background.getImage(),0,0,getScreenWidth()-1,getScreenHeight()-1,0+(int)slidex,0+(int)slidey,getScreenWidth()-1+(int)slidex,getScreenHeight()-1+(int)slidey,this);

        g2d.setFont(new Font("Verdana", Font.BOLD, 16));
        g2d.setColor(Color.BLACK);
        g2d.drawString("Aktuelle Position: "+x+":"+y, 10, 16);
        g2d.setFont(new Font("Verdana", Font.BOLD, 10));
        g2d.setColor(Color.WHITE);
        g2d.drawString("Steuerung mit Pfeiltasten (vorwärts, rückwärts, drehen)", 10, getScreenHeight()-30);
        g2d.drawString("Rennen mit STRG", 10, getScreenHeight()-20);
        g2d.drawString("Slide: "+slidex+":"+slidey, 10, getScreenHeight()-10);
}

	public void turnShip(int angle) {
		AnimatedSprite ship = (AnimatedSprite)sprites().get(0);
        ship.setFaceAngle(ship.faceAngle() - angle);
        if (ship.faceAngle() < 0)
            ship.setFaceAngle(360 - angle);

	}

    public void walk(int speed) {

        //the ship is always the first sprite in the linked list
        AnimatedSprite ship = (AnimatedSprite)sprites().get(0);

        if (speed == 0) {
        	ship.setVelocity(new Point2D(0, 0));
        	ship.setAnimationDirection(0);
        } else if (speed > 0)
        	ship.setAnimationDirection(1);
        else
        	ship.setAnimationDirection(-1);
        
        //up arrow adds thrust to ship (1/10 normal speed)
        ship.setMoveAngle(ship.faceAngle() - 90);

        //calculate the X and Y velocity based on angle
        double velx = calcAngleMoveX(ship.moveAngle()) * speed;
        double vely = calcAngleMoveY(ship.moveAngle()) * speed;
        ship.setVelocity(new Point2D(velx, vely));

    }
	@Override
	void gameShutdown() {
		// TODO Automatisch erstellter Methoden-Stub

	}

	@Override
	void gameStartup() {
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
        ship.setPosition(new Point2D(getScreenWidth()/2, getScreenHeight()/2));
        ship.setAlive(true);
        ship.setFrameDelay(4);
        sprites().add(ship);

        AnimatedSprite castle = new AnimatedSprite(this, graphics());
        castle.setSpriteType(RdwGameEngine.SPRITE_STATIC_OBJECT);
        castle.setImage(castleImage.getImage());
        castle.setPosition(new Point2D(738*2, 888*2));
        castle.setAlive(true);
        sprites().add(castle);
	}

	@Override
	void gameTimedUpdate() {
		checkkeys();
	}

	@Override
	void spriteCollision(AnimatedSprite spr1, AnimatedSprite spr2) {
	}

	@Override
	void spriteDraw(AnimatedSprite sprite) {
	}

	@Override
	void spriteDying(AnimatedSprite sprite) {
		// TODO Automatisch erstellter Methoden-Stub

	}

	@Override
	void spriteUpdate(AnimatedSprite sprite) {
		warp(sprite);
	}
    public void warp(AnimatedSprite spr) {
    	
    	// player av
    	if (spr.spriteType() == RdwGameEngine.SPRITE_AVATAR) {
	        //create some shortcut variables
	        int w = spr.frameWidth()-1;
	        int h = spr.frameHeight()-1;
	
	        // stop sprite at screen edge
	        if (spr.position().X() < 100)
	            spr.position().setX(100);
	        if (spr.position().Y() < 100)
	            spr.position().setY(100);
	        
	        if (spr.position().X() > background.width()-100) {
	        	spr.position().setX(background.width()-100);
	        } else if (spr.position().X() > getScreenWidth()-100) {
	            slidex = spr.position().X() - (getScreenWidth()-100);
	        }
	        if (spr.position().Y() > background.height()-100) {
	        	spr.position().setY(background.height()-100);
	        } else if (spr.position().Y() > getScreenHeight()-100) {
	            slidey = spr.position().Y() - (getScreenHeight()-100);
	        }
    	}
        
        if (spr.spriteType() == RdwGameEngine.SPRITE_AVATAR) {
        	x = (int)spr.position().X();
        	y = (int)spr.position().Y();
        	spr.setDrawposition(new Point2D((int)(spr.position().X() - slidex), (int)(spr.position().Y() - slidey)));
        } else
        	spr.setDrawposition(new Point2D((int)(spr.position().X() - slidex), (int)(spr.position().Y() - slidey)));
    }
}
