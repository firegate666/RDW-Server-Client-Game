package de.mb.rdw.engine;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import org.apache.log4j.Logger;

/*****************************************************
* Beginning Java 5 Game Programming
* by Jonathan S. Harbour
* Applet Game Framework class
*****************************************************/

public abstract class Game extends Applet implements Runnable, KeyListener,
    MouseListener, MouseMotionListener {

	final static Logger log = Logger.getLogger(Game.class);

    //the main game loop thread
    private Thread gameloop;

    //internal list of sprites
    private LinkedList<AnimatedSprite> _sprites;
    public LinkedList<AnimatedSprite> sprites() { return _sprites; }

    //screen and double buffer related variables
    private BufferedImage backbuffer;
    protected Graphics2D g2d;
    protected int screenWidth, screenHeight;

    //keep track of mouse position and buttons
    private Point2D mousePos = new Point2D(0,0);
    private boolean mouseButtons[] = new boolean[4];

    //frame rate counters and other timing variables
    private int _frameCount = 0;
    private int _frameRate = 0;
    private int desiredRate;
    private long startTime = System.currentTimeMillis();

    //local applet object
    public Applet applet() { return this; }

    //game pause state
    private boolean _gamePaused = false;
    public boolean gamePaused() { return _gamePaused; }
    public void pauseGame() { _gamePaused = true; }
    public void resumeGame() { _gamePaused = false; }

    //declare the game event methods that sub-class must implement
    protected abstract void gameStartup();
    protected abstract void gameTimedUpdate();
    protected abstract void gameRefreshScreen();
    protected abstract void gameShutdown();
    protected abstract void gameKeyDown(int keyCode);
    protected abstract void gameKeyUp(int keyCode);
    protected abstract void gameMouseDown();
    protected abstract void gameMouseUp();
    protected abstract void gameMouseMove();
    protected abstract void spriteUpdate(AnimatedSprite sprite);
    protected abstract void spriteDraw(AnimatedSprite sprite);
    protected abstract void spriteDying(AnimatedSprite sprite);
    protected abstract void spriteCollision(AnimatedSprite spr1, AnimatedSprite spr2);

    /*****************************************************
     * constructor
     *****************************************************/
    public Game(int frameRate, int width, int height) {
        desiredRate = frameRate;
        screenWidth = width;
        screenHeight = height;
    }

    //return g2d object so sub-class can draw things
    public Graphics2D graphics() { return g2d; }

    //current frame rate
    public int frameRate() { return _frameRate; }

    //mouse buttons and movement
    public boolean mouseButton(int btn) { return mouseButtons[btn]; }
    public Point2D mousePosition() { return mousePos; }

    /*****************************************************
     * applet init event method
     *****************************************************/
    public void init() {
    	log.info("Init Game");
        //create the back buffer and drawing surface
        backbuffer = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);
        g2d = backbuffer.createGraphics();

        //create the internal sprite list
        _sprites = new LinkedList<AnimatedSprite>();

        //start the input listeners
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);

        //this method implemented by sub-class
        gameStartup();
        update(g2d);
    }

    /*****************************************************
     * applet update event method
     *****************************************************/
    public void update(Graphics g) {
        //calculate frame rate
        _frameCount++;
        if (System.currentTimeMillis() > startTime + 1000) {
            startTime = System.currentTimeMillis();
            _frameRate = _frameCount;
            _frameCount = 0;

            //once every second all dead sprites are deleted
            purgeSprites();
        }
        //this method implemented by sub-class
        gameRefreshScreen();

        //draw the internal list of sprites
        if (!gamePaused()) {
            drawSprites();
        }

        paint(g);
    }

    /*****************************************************
      * applet window paint event method
      *****************************************************/
     public void paint(Graphics g) {
         g.drawImage(backbuffer, 0, 0, this);
     }

     /*****************************************************
      * thread start event - start the game loop running
      *****************************************************/
     public void start() {
         gameloop = new Thread(this);
         gameloop.start();
     }
     /*****************************************************
      * thread run event (game loop)
      *****************************************************/
     public void run() {
         //acquire the current thread
         Thread t = Thread.currentThread();

         //process the main game loop thread
         while (t == gameloop) {
             try {
                 //set a consistent frame rate
                 Thread.sleep(1000 / desiredRate);
             }
             catch(InterruptedException e) {
                 e.printStackTrace();
             }

             //update the internal list of sprites
             if (!gamePaused()) {
                 updateSprites();
                 testCollisions();
             }

             //allow main game to update if needed
             gameTimedUpdate();

             //refresh the screen
             repaint();
         }
     }

     /*****************************************************
      * thread stop event
      *****************************************************/
     public void stop() {
         //kill the game loop
         gameloop = null;

         //this method implemented by sub-class
         gameShutdown();
     }

     /*****************************************************
      * key listener events
      *****************************************************/
     public void keyTyped(KeyEvent k) { }
     public void keyPressed(KeyEvent k) {
         gameKeyDown(k.getKeyCode());
     }
     public void keyReleased(KeyEvent k) {
         gameKeyUp(k.getKeyCode());
     }

     /*****************************************************
      * checkButtons stores the state of the mouse buttons
      *****************************************************/
     private void checkButtons(MouseEvent e) {
             switch(e.getButton()) {
             case MouseEvent.BUTTON1:
                 mouseButtons[1] = true;
                 mouseButtons[2] = false;
                 mouseButtons[3] = false;
                 break;
             case MouseEvent.BUTTON2:
                 mouseButtons[1] = false;
                 mouseButtons[2] = true;
                 mouseButtons[3] = false;
                 break;
             case MouseEvent.BUTTON3:
                 mouseButtons[1] = false;
                 mouseButtons[2] = false;
                 mouseButtons[3] = true;
                 break;
             }
    }

    /*****************************************************
     * mouse listener events
     *****************************************************/
     public void mousePressed(MouseEvent e) {
         checkButtons(e);
         mousePos.setX(e.getX());
         mousePos.setY(e.getY());
         gameMouseDown();
     }
     public void mouseReleased(MouseEvent e) {
         checkButtons(e);
         mousePos.setX(e.getX());
         mousePos.setY(e.getY());
         gameMouseUp();
     }
     public void mouseMoved(MouseEvent e) {
         checkButtons(e);
         mousePos.setX(e.getX());
         mousePos.setY(e.getY());
         gameMouseMove();
     }
     public void mouseDragged(MouseEvent e) {
         checkButtons(e);
         mousePos.setX(e.getX());
         mousePos.setY(e.getY());
         gameMouseDown();
         gameMouseMove();
     }
     public void mouseEntered(MouseEvent e) {
         mousePos.setX(e.getX());
         mousePos.setY(e.getY());
         gameMouseMove();
     }
     public void mouseExited(MouseEvent e) {
         mousePos.setX(e.getX());
         mousePos.setY(e.getY());
         gameMouseMove();
     }
     //this event is not needed
     public void mouseClicked(MouseEvent e) { }

     /*****************************************************
      * X and Y velocity calculation functions
      *****************************************************/
     protected double calcAngleMoveX(double angle) {
         return (double)(Math.cos(angle * Math.PI / 180));
     }
     protected double calcAngleMoveY(double angle) {
         return (double) (Math.sin(angle * Math.PI / 180));
     }

     /*****************************************************
      * update the sprite list from the game loop thread
      *****************************************************/
     protected void updateSprites() {
         for (int n=0; n < _sprites.size(); n++) {
             AnimatedSprite spr = _sprites.get(n);
             if (spr.alive()) {
                 spr.updatePosition();
                 spr.updateRotation();
                 spr.updateAnimation();
                 spriteUpdate(spr);
                 spr.updateLifetime();
                 if (!spr.alive()) {
                     spriteDying(spr);
                 }
             }
         }
     }

     /*****************************************************
      * perform collision testing of all active sprites
      *****************************************************/
     protected void testCollisions() {
         //iterate through the sprite list, test each sprite against
         //every other sprite in the list
         for (int first=0; first < _sprites.size(); first++) {

             //get the first sprite to test for collision
             AnimatedSprite spr1 = _sprites.get(first);
             if (spr1.alive()) {

                 //look through all sprites again for collisions
                 for (int second = 0; second < _sprites.size(); second++) {

                     //make sure this isn't the same sprite
                     if (first != second) {

                         //get the second sprite to test for collision
                         AnimatedSprite spr2 = _sprites.get(second);
                         if (spr2.alive()) {
                             if (spr2.collidesWith(spr1)) {
                                 spriteCollision(spr1, spr2);
                                 break;
                             }
                             else
                                spr1.setCollided(false);

                         }
                     }
                 }
             }
         }
     }

     /*****************************************************
      * draw all active sprites in the sprite list
      * sprites lower in the list are drawn on top
      *****************************************************/
     protected void drawSprites() {
         //draw sprites in reverse order (reverse priority)
         for (int n=_sprites.size()-1; n>-1; n--) {
             AnimatedSprite spr = _sprites.get(n);
             if (spr.alive()) {
                 spr.updateFrame();
                 spr.transform();
                 spr.draw();
                 spriteDraw(spr);
             }
         }
     }

     /*****************************************************
      * once every second during the frame update, this method
      * is called to remove all dead sprites from the linked list
      *****************************************************/
     private void purgeSprites() {
         for (int n=0; n < _sprites.size(); n++) {
             AnimatedSprite spr =  _sprites.get(n);
             if (!spr.alive()) {
                 _sprites.remove(n);
             }
         }
     }
	public int getScreenHeight() {
		return screenHeight;
	}
	public int getScreenWidth() {
		return screenWidth;
	}
}