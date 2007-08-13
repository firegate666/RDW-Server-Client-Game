package de.mb.engine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/*****************************************************
* Beginning Java 5 Game Programming
* by Jonathan S. Harbour
* GALACTIC WAR, Chapter 11
*****************************************************/

public class RdwGameEngine extends Game {

	ImageEntity background;

	public RdwGameEngine() {
		super(60, 800, 600);
	}

	boolean keyup = false;
	boolean keydown = false;
	boolean keyleft = false;
	boolean keyright = false;

	public void checkkeys() {
		if (keyup)
			applyThrust(-2);
		else if (keydown)
			applyThrust(2);
		else
			applyThrust(0);

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
        g2d.drawImage(background.getImage(),0,0,800-1,600-1,this);
    }

	public void turnShip(int angle) {
		AnimatedSprite ship = (AnimatedSprite)sprites().get(0);
        ship.setFaceAngle(ship.faceAngle() - angle);
        if (ship.faceAngle() < 0)
            ship.setFaceAngle(360 - angle);

	}

    public void applyThrust(int ACCELERATION) {

        //the ship is always the first sprite in the linked list
        AnimatedSprite ship = (AnimatedSprite)sprites().get(0);

        if (ACCELERATION == 0) {
        	ship.setVelocity(new Point2D(0, 0));
        	ship.setAnimationDirection(0);
        } else if (ACCELERATION > 0)
        	ship.setAnimationDirection(1);
        else
        	ship.setAnimationDirection(-1);
        
        //up arrow adds thrust to ship (1/10 normal speed)
        ship.setMoveAngle(ship.faceAngle() - 90);

        //calculate the X and Y velocity based on angle
        double velx = calcAngleMoveX(ship.moveAngle()) * ACCELERATION;
        double vely = calcAngleMoveY(ship.moveAngle()) * ACCELERATION;
        ship.setVelocity(new Point2D(velx, vely));

    }
	@Override
	void gameShutdown() {
		// TODO Automatisch erstellter Methoden-Stub

	}

	@Override
	void gameStartup() {
        background = new ImageEntity(this);
        background.load("/resource/sprites/bluespace.png");

        ImageEntity shipImage = new ImageEntity(this);
        shipImage.load("/resource/sprites/xball.png");

        AnimatedSprite ship = new AnimatedSprite(this, graphics());
        ship.setSpriteType(0);
        ship.setAnimImage(shipImage.getImage());
        ship.setFrameWidth(64);
        ship.setFrameHeight(64);
        ship.setTotalFrames(64);
        ship.setColumns(8);
        ship.setPosition(new Point2D(800/2, 600/2));
        ship.setAlive(true);
        ship.setFrameDelay(4);
        sprites().add(ship);
	}

	@Override
	void gameTimedUpdate() {
		checkkeys();
	}

	@Override
	void spriteCollision(AnimatedSprite spr1, AnimatedSprite spr2) {
		// TODO Automatisch erstellter Methoden-Stub

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
        //create some shortcut variables
        int w = spr.frameWidth()-1;
        int h = spr.frameHeight()-1;

        //wrap the sprite around the screen edges
        if (spr.position().X() < 0-w)
            spr.position().setX(800);
        else if (spr.position().X() > 800)
            spr.position().setX(0-w);
        if (spr.position().Y() < 0-h)
            spr.position().setY(600);
        else if (spr.position().Y() > 600)
            spr.position().setY(0-h);
    }
}
