package java5gameprogramming.v1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.Random;

import de.mb.rdw.engine.AnimatedSprite;
import de.mb.rdw.engine.Game;
import de.mb.rdw.engine.ImageEntity;
import de.mb.rdw.engine.MidiSequence;
import de.mb.rdw.engine.Point2D;
import de.mb.rdw.engine.SoundClip;

/*****************************************************
* Beginning Java 5 Game Programming
* by Jonathan S. Harbour
* GALACTIC WAR, Chapter 11
*****************************************************/


public class GalacticWar extends Game {
    //these must be static because they are passed to a constructor
    static int FRAMERATE = 60;
    static int SCREENWIDTH = 800;
    static int SCREENHEIGHT = 600;

    //misc global constants
    final int ASTEROIDS = 10;
    final int BULLET_SPEED = 4;
    final double ACCELERATION = 0.05;
    final double SHIPROTATION = 5.0;

    //sprite state values
    final int STATE_NORMAL = 0;
    final int STATE_COLLIDED = 1;
    final int STATE_EXPLODING = 2;

    //sprite types
    final int SPRITE_SHIP = 1;
    final int SPRITE_ASTEROID_BIG = 10;
    final int SPRITE_ASTEROID_MEDIUM = 11;
    final int SPRITE_ASTEROID_SMALL = 12;
    final int SPRITE_ASTEROID_TINY = 13;
    final int SPRITE_BULLET = 100;
    final int SPRITE_EXPLOSION = 200;
    final int SPRITE_POWERUP_SHIELD = 300;
    final int SPRITE_POWERUP_HEALTH = 301;
    final int SPRITE_POWERUP_250 = 302;
    final int SPRITE_POWERUP_500 = 303;
    final int SPRITE_POWERUP_1000 = 304;
    final int SPRITE_POWERUP_GUN = 305;

    //game states
    final int GAME_MENU = 0;
    final int GAME_RUNNING = 1;
    final int GAME_OVER = 2;

    //various toggles
    boolean showBounds = false;
    boolean collisionTesting = true;

    //define the images used in the game
    ImageEntity background;
    ImageEntity bulletImage;
    ImageEntity[] bigAsteroids = new ImageEntity[5];
    ImageEntity[] medAsteroids = new ImageEntity[2];
    ImageEntity[] smlAsteroids = new ImageEntity[3];
    ImageEntity[] tnyAsteroids = new ImageEntity[4];
    ImageEntity[] explosions = new ImageEntity[2];
    ImageEntity[] shipImage = new ImageEntity[3];
    ImageEntity[] barImage = new ImageEntity[2];
    ImageEntity barFrame;
    ImageEntity powerupShield;
    ImageEntity powerupHealth;
    ImageEntity powerup250;
    ImageEntity powerup500;
    ImageEntity powerup1000;
    ImageEntity powerupGun;

    //health/shield meters and score
    int health = 20;
    int shield = 20;
    int score = 0;
    int highscore = 0;
    int firepower = 1;
    int gameState = GAME_MENU;

    //create a random number generator
    Random rand = new Random();

    //used to make ship temporarily invulnerable
    long collisionTimer = 0;

    //some key input tracking variables
    boolean keyLeft, keyRight, keyUp, keyFire, keyB, keyC, keyShield;

    //sound effects and music
    MidiSequence music = new MidiSequence();
    SoundClip shoot = new SoundClip();
    SoundClip explosion = new SoundClip();


   /*****************************************************
    * constructor
    *****************************************************/
    public GalacticWar() {
        super(FRAMERATE, SCREENWIDTH, SCREENHEIGHT);
    }

    /*****************************************************
     * gameStartup event passed by game engine
     *****************************************************/
    protected void gameStartup() {
        //load sounds and music
        music.load("/resource/sprites/music.mid");
        shoot.load("/resource/sprites/shoot.au");
        explosion.load("/resource/sprites/explode.au");

        //load the health/shield bars
        barFrame = new ImageEntity(this);
        barFrame.load("/resource/sprites/barframe.png");
        barImage[0] = new ImageEntity(this);
        barImage[0].load("/resource/sprites/bar_health.png");
        barImage[1] = new ImageEntity(this);
        barImage[1].load("/resource/sprites/bar_shield.png");

        //load powerups
        powerupShield = new ImageEntity(this);
        powerupShield.load("/resource/sprites/powerup_shield2.png");
        powerupHealth = new ImageEntity(this);
        powerupHealth.load("/resource/sprites/powerup_cola.png");
        powerup250 = new ImageEntity(this);
        powerup250.load("/resource/sprites/powerup_250.png");
        powerup500 = new ImageEntity(this);
        powerup500.load("/resource/sprites/powerup_500.png");
        powerup1000 = new ImageEntity(this);
        powerup1000.load("/resource/sprites/powerup_1000.png");
        powerupGun = new ImageEntity(this);
        powerupGun.load("/resource/sprites/powerup_gun.png");

        //load the background image
        background = new ImageEntity(this);
        background.load("/resource/sprites/bluespace.png");

        //create the ship sprite--first in the sprite list
        shipImage[0] = new ImageEntity(this);
        shipImage[0].load("/resource/sprites/spaceship.png");
        shipImage[1] = new ImageEntity(this);
        shipImage[1].load("/resource/sprites/ship_thrust.png");
        shipImage[2] = new ImageEntity(this);
        shipImage[2].load("/resource/sprites/ship_shield.png");

        AnimatedSprite ship = new AnimatedSprite(this, graphics());
        ship.setSpriteType(SPRITE_SHIP);
        ship.setImage(shipImage[0].getImage());
        ship.setFrameWidth(ship.imageWidth());
        ship.setFrameHeight(ship.imageHeight());
        ship.setPosition(new Point2D(SCREENWIDTH/2, SCREENHEIGHT/2));
        ship.setAlive(true);
        //start ship off as invulnerable
        ship.setState(STATE_EXPLODING);
        collisionTimer = System.currentTimeMillis();
        sprites().add(ship);

        //load the bullet sprite image
        bulletImage = new ImageEntity(this);
        bulletImage.load("/resource/sprites/plasmashot.png");

        //load the explosion sprite image
        explosions[0] = new ImageEntity(this);
        explosions[0].load("/resource/sprites/explosion.png");
        explosions[1] = new ImageEntity(this);
        explosions[1].load("/resource/sprites/explosion2.png");

        //load the big asteroid images (5 total)
        for (int n = 0; n<5; n++) {
            bigAsteroids[n] = new ImageEntity(this);
            String fn = "/resource/sprites/asteroid" + (n+1) + ".png";
            bigAsteroids[n].load(fn);
        }
        //load the medium asteroid images (2 total)
        for (int n = 0; n<2; n++) {
            medAsteroids[n] = new ImageEntity(this);
            String fn = "/resource/sprites/medium" + (n+1) + ".png";
            medAsteroids[n].load(fn);
        }
        //load the small asteroid images (3 total)
        for (int n = 0; n<3; n++) {
            smlAsteroids[n] = new ImageEntity(this);
            String fn = "/resource/sprites/small" + (n+1) + ".png";
            smlAsteroids[n].load(fn);
        }
        //load the tiny asteroid images (4 total)
        for (int n = 0; n<4; n++) {
            tnyAsteroids[n] = new ImageEntity(this);
            String fn = "/resource/sprites/tiny" + (n+1) + ".png";
            tnyAsteroids[n].load(fn);
        }

        //start off in pause mode
        pauseGame();
    }

    private void resetGame() {
        //restart the music soundtrack
        music.setLooping(true);
        music.play();

        //save the ship for the restart
        AnimatedSprite ship = (AnimatedSprite) sprites().get(0);

        //wipe out the sprite list to start over!
        sprites().clear();

        //add the saved ship to the sprite list
        ship.setPosition(new Point2D(SCREENWIDTH/2, SCREENHEIGHT/2));
        ship.setAlive(true);
        ship.setState(STATE_EXPLODING);
        collisionTimer = System.currentTimeMillis();
        ship.setVelocity(new Point2D(0, 0));
        sprites().add(ship);

        //create the random asteroid sprites
        for (int n = 0; n<ASTEROIDS; n++) {
            createAsteroid();
        }

        //reset variables
        health = 20;
        shield = 20;
        score = 0;
        firepower = 2;
    }

    /*****************************************************
     * gameTimedUpdate event passed by game engine
     *****************************************************/
    protected void gameTimedUpdate() {
        checkInput();

        if (!gamePaused() && sprites().size() == 1) {
            resetGame();
            gameState = GAME_OVER;
        }
    }

    /*****************************************************
     * gameRefreshScreen event passed by game engine
     *****************************************************/
    protected void gameRefreshScreen() {
        Graphics2D g2d = graphics();

        //draw the background
        g2d.drawImage(background.getImage(),0,0,SCREENWIDTH-1,SCREENHEIGHT-1,this);

        //what is the game state?
        if (gameState == GAME_MENU) {
            g2d.setFont(new Font("Verdana", Font.BOLD, 36));
            g2d.setColor(Color.BLACK);
            g2d.drawString("GALACTIC WAR", 252, 202);
            g2d.setColor(new Color(200,30,30));
            g2d.drawString("GALACTIC WAR", 250, 200);

            int x = 270, y = 15;
            g2d.setFont(new Font("Times New Roman", Font.ITALIC | Font.BOLD, 20));
            g2d.setColor(Color.YELLOW);
            g2d.drawString("CONTROLS:", x, ++y*20);
            g2d.drawString("ROTATE - Left/Right Arrows", x+20, ++y*20);
            g2d.drawString("THRUST - Up Arrow", x+20, ++y*20);
            g2d.drawString("SHIELD - Shift key (no scoring)", x+20, ++y*20);
            g2d.drawString("FIRE - Ctrl key", x+20, ++y*20);

            g2d.setColor(Color.WHITE);
            g2d.drawString("POWERUPS INCREASE FIREPOWER!", 240, 480);

            g2d.setFont(new Font("Ariel", Font.BOLD, 24));
            g2d.setColor(Color.ORANGE);
            g2d.drawString("Press ENTER to start", 280, 570);
        }
        else if (gameState == GAME_RUNNING) {
            //draw health/shield bars and meters
            g2d.drawImage(barFrame.getImage(), SCREENWIDTH - 132, 18, this);
            for (int n = 0; n < health; n++) {
                int dx = SCREENWIDTH - 130 + n * 5;
                g2d.drawImage(barImage[0].getImage(), dx, 20, this);
            }
            g2d.drawImage(barFrame.getImage(), SCREENWIDTH - 132, 33, this);
            for (int n = 0; n < shield; n++) {
                int dx = SCREENWIDTH - 130 + n * 5;
                g2d.drawImage(barImage[1].getImage(), dx, 35, this);
            }

            //draw the bullet upgrades
            for (int n = 0; n < firepower; n++) {
                int dx = SCREENWIDTH - 220 + n * 13;
                g2d.drawImage(powerupGun.getImage(), dx, 17, this);
            }

            //display the score
            g2d.setFont(new Font("Verdana", Font.BOLD, 24));
            g2d.setColor(Color.WHITE);
            g2d.drawString("" + score, 20, 40);
            g2d.setColor(Color.RED);
            g2d.drawString("" + highscore, 350, 40);
        }
        else if (gameState == GAME_OVER) {
            g2d.setFont(new Font("Verdana", Font.BOLD, 36));
            g2d.setColor(new Color(200, 30, 30));
            g2d.drawString("GAME OVER", 270, 200);

            g2d.setFont(new Font("Arial", Font.CENTER_BASELINE, 24));
            g2d.setColor(Color.ORANGE);
            g2d.drawString("Press ENTER to restart", 260, 500);
        }
    }

    /*****************************************************
     * gameShutdown event passed by game engine
     *****************************************************/
    protected void gameShutdown() {
        music.stop();
        shoot.stop();
        explosion.stop();
    }

    /*****************************************************
     * spriteUpdate event passed by game engine
     *****************************************************/
    public void spriteUpdate(AnimatedSprite sprite) {
        switch(sprite.spriteType()) {
        case SPRITE_SHIP:
            warp(sprite);
            break;

        case SPRITE_BULLET:
            warp(sprite);
            break;

        case SPRITE_EXPLOSION:
            if (sprite.currentFrame() == sprite.totalFrames()-1) {
                sprite.setAlive(false);
            }
            break;

        case SPRITE_ASTEROID_BIG:
        case SPRITE_ASTEROID_MEDIUM:
        case SPRITE_ASTEROID_SMALL:
        case SPRITE_ASTEROID_TINY:
            warp(sprite);
            break;

        case SPRITE_POWERUP_SHIELD:
        case SPRITE_POWERUP_HEALTH:
        case SPRITE_POWERUP_250:
        case SPRITE_POWERUP_500:
        case SPRITE_POWERUP_1000:
        case SPRITE_POWERUP_GUN:
            warp(sprite);
            //make powerup animation wobble
            double rot = sprite.rotationRate();
            if (sprite.faceAngle() > 350) {
                sprite.setRotationRate(rot * -1);
                sprite.setFaceAngle(350);
            }
            else if (sprite.faceAngle() < 10) {
                sprite.setRotationRate(rot * -1);
                sprite.setFaceAngle(10);
            }
            break;
        }
    }

    /*****************************************************
     * spriteDraw event passed by game engine
     * called by the game class after each sprite is drawn
     * to give you a chance to manipulate the sprite
     *****************************************************/
    public void spriteDraw(AnimatedSprite sprite) {
        if (showBounds) {
            if (sprite.collided())
                sprite.drawBounds(Color.RED);
            else
                sprite.drawBounds(Color.BLUE);
        }
    }

    /*****************************************************
     * spriteDying event passed by game engine
     * called after a sprite's age reaches its lifespan
     * at which point it will be killed off, and then removed from
     * the linked list. you can cancel the purging process here.
     *****************************************************/
    public void spriteDying(AnimatedSprite sprite) {
    }

    /*****************************************************
     * spriteCollision event passed by game engine
     *****************************************************/
    public void spriteCollision(AnimatedSprite spr1, AnimatedSprite spr2) {
        //jump out quickly if collisions are off
        if (!collisionTesting) return;

        //figure out what type of sprite has collided
        switch(spr1.spriteType()) {
        case SPRITE_BULLET:
            //did bullet hit an asteroid?
            if (isAsteroid(spr2.spriteType())) {
                bumpScore(5);
                spr1.setAlive(false);
                spr2.setAlive(false);
                breakAsteroid(spr2);
            }
            break;
        case SPRITE_SHIP:
            //did asteroid crash into the ship?
            if (isAsteroid(spr2.spriteType())) {
                if (spr1.state() == STATE_NORMAL) {
                    if (keyShield) { //***
                        shield -= 1;
                    }
                    else { //***
                        collisionTimer = System.currentTimeMillis();
                        spr1.setVelocity(new Point2D(0, 0));
                        double x = spr1.position().X() - 10;
                        double y = spr1.position().Y() - 10;
                        startBigExplosion(new Point2D(x, y));
                        spr1.setState(STATE_EXPLODING);
                        //reduce ship health
                        health -= 1; //***
                        if (health < 0) {
                            gameState = GAME_OVER;
                        }
                        //reduce firepower
                        firepower--;
                        if (firepower < 1) firepower = 1;

                    }
                    spr2.setAlive(false);
                    breakAsteroid(spr2);
                }
                //make ship temporarily invulnerable
                else if (spr1.state() == STATE_EXPLODING) {
                    if (collisionTimer + 3000 <
                        System.currentTimeMillis()) {
                        spr1.setState(STATE_NORMAL);
                    }
                }
            }
            break;
//***
        case SPRITE_POWERUP_SHIELD:
            if (spr2.spriteType()==SPRITE_SHIP) {
                shield += 5;
                if (shield > 20) shield = 20;
                spr1.setAlive(false);
            }
            break;

        case SPRITE_POWERUP_HEALTH:
            if (spr2.spriteType()==SPRITE_SHIP) {
                health += 5;
                if (health > 20) health = 20;
                spr1.setAlive(false);
            }
            break;

        case SPRITE_POWERUP_250:
            if (spr2.spriteType()==SPRITE_SHIP) {
                bumpScore(250);
                spr1.setAlive(false);
            }
            break;

        case SPRITE_POWERUP_500:
            if (spr2.spriteType()==SPRITE_SHIP) {
                bumpScore(500);
                spr1.setAlive(false);
            }
            break;

        case SPRITE_POWERUP_1000:
            if (spr2.spriteType()==SPRITE_SHIP) {
                bumpScore(1000);
                spr1.setAlive(false);
            }
            break;

        case SPRITE_POWERUP_GUN:
            if (spr2.spriteType()==SPRITE_SHIP) {
                firepower++;
                if (firepower > 5) firepower = 5;
                spr1.setAlive(false);
            }
            break;

        }

    }

    /*****************************************************
     * gameKeyDown event passed by game engine
     *****************************************************/
    public void gameKeyDown(int keyCode) {
        switch(keyCode) {
        case KeyEvent.VK_LEFT:
            keyLeft = true;
            break;
        case KeyEvent.VK_RIGHT:
            keyRight = true;
            break;
        case KeyEvent.VK_UP:
            keyUp = true;
            break;
        case KeyEvent.VK_CONTROL:
            keyFire = true;
            break;
        case KeyEvent.VK_B:
            //toggle bounding rectangles
            showBounds = !showBounds;
            break;
        case KeyEvent.VK_C:
            //toggle collision testing
            collisionTesting = !collisionTesting;
            break;
        case KeyEvent.VK_SHIFT:
            if ((!keyUp) && (shield > 0))
                keyShield = true;
            else
                keyShield = false;
            break;

        case KeyEvent.VK_ENTER:
            if (gameState == GAME_MENU) {
                resetGame();
                resumeGame();
                gameState = GAME_RUNNING;
            }
            else if (gameState == GAME_OVER) {
                resetGame();
                resumeGame();
                gameState = GAME_RUNNING;
            }
            break;

        case KeyEvent.VK_ESCAPE:
            if (gameState == GAME_RUNNING) {
                pauseGame();
                gameState = GAME_OVER;
            }
            break;
        }
    }

    /*****************************************************
     * gameKeyUp event passed by game engine
     *****************************************************/
    public void gameKeyUp(int keyCode) {
        switch(keyCode) {
        case KeyEvent.VK_LEFT:
            keyLeft = false;
            break;
        case KeyEvent.VK_RIGHT:
            keyRight = false;
            break;
        case KeyEvent.VK_UP:
            keyUp = false;
            break;
        case KeyEvent.VK_CONTROL:
            keyFire = false;
            fireBullet();
            break;
        case KeyEvent.VK_SHIFT:
            keyShield = false;
            break;
        }
    }

    /*****************************************************
     * mouse events passed by game engine
     * the game is not currently using mouse input
     *****************************************************/
    public void gameMouseDown() { }
    public void gameMouseUp() { }
    public void gameMouseMove() { }

    /*****************************************************
     * break up an asteroid into smaller pieces
     *****************************************************/
    private void breakAsteroid(AnimatedSprite sprite) {
        switch(sprite.spriteType()) {
        case SPRITE_ASTEROID_BIG:
            //spawn medium asteroids over the old one
            spawnAsteroid(sprite);
            spawnAsteroid(sprite);
            spawnAsteroid(sprite);
            //draw big explosion
            startBigExplosion(sprite.position());
            break;
        case SPRITE_ASTEROID_MEDIUM:
            //spawn small asteroids over the old one
            spawnAsteroid(sprite);
            spawnAsteroid(sprite);
            spawnAsteroid(sprite);
            //draw small explosion
            startSmallExplosion(sprite.position());
            break;
        case SPRITE_ASTEROID_SMALL:
            //spawn tiny asteroids over the old one
            spawnAsteroid(sprite);
            spawnAsteroid(sprite);
            spawnAsteroid(sprite);
            //draw small explosion
            startSmallExplosion(sprite.position());
            break;
        case SPRITE_ASTEROID_TINY:
            //spawn a random powerup
            spawnPowerup(sprite);
            //draw small explosion
            startSmallExplosion(sprite.position());
            break;
        }
    }

    /*****************************************************
     * spawn a smaller asteroid based on passed sprite
     *****************************************************/
    private void spawnAsteroid(AnimatedSprite sprite) {
        //create a new asteroid sprite
        AnimatedSprite ast = new AnimatedSprite(this, graphics());
        ast.setAlive(true);

        //set pseudo-random position around source sprite
        int w = sprite.getBounds().width;
        int h = sprite.getBounds().height;
        double x = sprite.position().X() + w/2 + rand.nextInt(20)-40;
        double y = sprite.position().Y() + h/2 + rand.nextInt(20)-40;
        ast.setPosition(new Point2D(x,y));

        //set rotation and direction angles
        ast.setFaceAngle(rand.nextInt(360));
        ast.setMoveAngle(rand.nextInt(360));
        ast.setRotationRate(rand.nextDouble());

        //set velocity based on movement direction
        double ang = ast.moveAngle() - 90;
        double velx = calcAngleMoveX(ang);
        double vely = calcAngleMoveY(ang);
        ast.setVelocity(new Point2D(velx, vely));

        //set some size-specific properties
        switch(sprite.spriteType()) {
        case SPRITE_ASTEROID_BIG:
            ast.setSpriteType(SPRITE_ASTEROID_MEDIUM);

            //pick one of the random asteroid images
            int i = rand.nextInt(2);
            ast.setImage(medAsteroids[i].getImage());
            ast.setFrameWidth(medAsteroids[i].width());
            ast.setFrameHeight(medAsteroids[i].height());

            break;
        case SPRITE_ASTEROID_MEDIUM:
            ast.setSpriteType(SPRITE_ASTEROID_SMALL);

            //pick one of the random asteroid images
            i = rand.nextInt(3);
            ast.setImage(smlAsteroids[i].getImage());
            ast.setFrameWidth(smlAsteroids[i].width());
            ast.setFrameHeight(smlAsteroids[i].height());
            break;

        case SPRITE_ASTEROID_SMALL:
            ast.setSpriteType(SPRITE_ASTEROID_TINY);

            //pick one of the random asteroid images
            i = rand.nextInt(4);
            ast.setImage(tnyAsteroids[i].getImage());
            ast.setFrameWidth(tnyAsteroids[i].width());
            ast.setFrameHeight(tnyAsteroids[i].height());
            break;
        }

         //add the new asteroid to the sprite list
        sprites().add(ast);
    }

    /*****************************************************
     * create a random powerup at the supplied sprite location
     *****************************************************/
    private void spawnPowerup(AnimatedSprite sprite) {
        //only a few tiny sprites spit out a powerup
        int n = rand.nextInt(100);
        if (n > 12) return;

        //use this powerup sprite
        AnimatedSprite spr = new AnimatedSprite(this, graphics());
        spr.setRotationRate(8);
        spr.setPosition(sprite.position());
        double velx = rand.nextDouble();
        double vely = rand.nextDouble();
        spr.setVelocity(new Point2D(velx, vely));
        spr.setLifespan(1500);
        spr.setAlive(true);

        //customize the sprite based on powerup type
        switch(rand.nextInt(6)) {
        case 0:
            //create a new shield powerup sprite
            spr.setImage(powerupShield.getImage());
            spr.setSpriteType(SPRITE_POWERUP_SHIELD);
            sprites().add(spr);
            break;

        case 1:
            //create a new health powerup sprite
            spr.setImage(powerupHealth.getImage());
            spr.setSpriteType(SPRITE_POWERUP_HEALTH);
            sprites().add(spr);
            break;

        case 2:
            //create a new 250-point powerup sprite
            spr.setImage(powerup250.getImage());
            spr.setSpriteType(SPRITE_POWERUP_250);
            sprites().add(spr);
            break;

        case 3:
            //create a new 500-point powerup sprite
            spr.setImage(powerup500.getImage());
            spr.setSpriteType(SPRITE_POWERUP_500);
            sprites().add(spr);
            break;

        case 4:
            //create a new 1000-point powerup sprite
            spr.setImage(powerup1000.getImage());
            spr.setSpriteType(SPRITE_POWERUP_1000);
            sprites().add(spr);
            break;

        case 5:
            //create a new gun powerup sprite
            spr.setImage(powerupGun.getImage());
            spr.setSpriteType(SPRITE_POWERUP_GUN);
            sprites().add(spr);
            break;

        }
    }

    /*****************************************************
     * create a random "big" asteroid
     *****************************************************/
    public void createAsteroid() {
        //create a new asteroid sprite
        AnimatedSprite ast = new AnimatedSprite(this, graphics());
        ast.setAlive(true);
        ast.setSpriteType(SPRITE_ASTEROID_BIG);

        //pick one of the random asteroid images
        int i = rand.nextInt(5);
        ast.setImage(bigAsteroids[i].getImage());
        ast.setFrameWidth(bigAsteroids[i].width());
        ast.setFrameHeight(bigAsteroids[i].height());

        //set to a random position on the screen
        int x = rand.nextInt(SCREENWIDTH-128);
        int y = rand.nextInt(SCREENHEIGHT-128);
        ast.setPosition(new Point2D(x, y));

        //set rotation and direction angles
        ast.setFaceAngle(rand.nextInt(360));
        ast.setMoveAngle(rand.nextInt(360));
        ast.setRotationRate(rand.nextDouble());

        //set velocity based on movement direction
        double ang = ast.moveAngle() - 90;
        double velx = calcAngleMoveX(ang);
        double vely = calcAngleMoveY(ang);
        ast.setVelocity(new Point2D(velx, vely));

        //add the new asteroid to the sprite list
        sprites().add(ast);
    }

    /*****************************************************
     * returns true if passed sprite type is an asteroid type
     *****************************************************/
    private boolean isAsteroid(int spriteType) {
        switch(spriteType) {
        case SPRITE_ASTEROID_BIG:
        case SPRITE_ASTEROID_MEDIUM:
        case SPRITE_ASTEROID_SMALL:
        case SPRITE_ASTEROID_TINY:
            return true;
        default:
            return false;
        }
    }

    /*****************************************************
     * process keys that have been pressed
     *****************************************************/
    public void checkInput() {
        if (gameState != GAME_RUNNING) return;

        //the ship is always the first sprite in the linked list
        AnimatedSprite ship = (AnimatedSprite)sprites().get(0);
        if (keyLeft) {
        	System.out.println("Rotate Left");
            //left arrow rotates ship left 5 degrees
            ship.setFaceAngle(ship.faceAngle() - SHIPROTATION);
            if (ship.faceAngle() < 0)
                ship.setFaceAngle(360 - SHIPROTATION);
            System.out.println("Angle "+ship.faceAngle());

        } else if (keyRight) {
            //right arrow rotates ship right 5 degrees
        	System.out.println("Rotate Right");
            ship.setFaceAngle(ship.faceAngle() + SHIPROTATION);
            if (ship.faceAngle() > 360)
                ship.setFaceAngle(SHIPROTATION);
            System.out.println("Angle "+ship.faceAngle());
        }
        if (keyUp) {
            //up arrow applies thrust to ship
            ship.setImage(shipImage[1].getImage());
            applyThrust();
        }
        else if (keyShield) {
            ship.setImage(shipImage[2].getImage());
        }
       else
            //set ship image to normal non-thrust image
            ship.setImage(shipImage[0].getImage());
    }

    /*****************************************************
     * increase the thrust of the ship based on facing angle
     *****************************************************/
    public void applyThrust() {
        //the ship is always the first sprite in the linked list
        AnimatedSprite ship = (AnimatedSprite)sprites().get(0);

        //up arrow adds thrust to ship (1/10 normal speed)
        ship.setMoveAngle(ship.faceAngle() - 90);

        //calculate the X and Y velocity based on angle
        double velx = ship.velocity().X();
        System.out.println("velx "+velx);
        velx += calcAngleMoveX(ship.moveAngle()) * ACCELERATION;
        if (velx < -5) velx = -5;
        else if (velx > 5) velx = 5;
        double vely = ship.velocity().Y();
        vely += calcAngleMoveY(ship.moveAngle()) * ACCELERATION;
        if (vely < -5) vely = -5;
        else if (vely > 5) vely = 5;
        ship.setVelocity(new Point2D(velx, vely));

    }

    /*****************************************************
     * fire a bullet from the ship's position and orientation
     *****************************************************/
    public void fireBullet() {
        //create the new bullet sprite
        AnimatedSprite[] bullets = new AnimatedSprite[6];

        switch(firepower) {
        case 1:
            bullets[0] = stockBullet();
            sprites().add(bullets[0]);
            break;

        case 2:
            bullets[0] = stockBullet();
            adjustDirection(bullets[0], -4);
            sprites().add(bullets[0]);

            bullets[1] = stockBullet();
            adjustDirection(bullets[1], 4);
            sprites().add(bullets[1]);

            break;

        case 3:
            bullets[0] = stockBullet();
            adjustDirection(bullets[0], -4);
            sprites().add(bullets[0]);

            bullets[1] = stockBullet();
            sprites().add(bullets[1]);

            bullets[2] = stockBullet();
            adjustDirection(bullets[2], 4);
            sprites().add(bullets[2]);

            break;

        case 4:
            bullets[0] = stockBullet();
            adjustDirection(bullets[0], -5);
            sprites().add(bullets[0]);

            bullets[1] = stockBullet();
            adjustDirection(bullets[1], 5);
            sprites().add(bullets[1]);

            bullets[2] = stockBullet();
            adjustDirection(bullets[2], -10);
            sprites().add(bullets[2]);

            bullets[3] = stockBullet();
            adjustDirection(bullets[3], 10);
            sprites().add(bullets[3]);

            break;

        case 5:
            bullets[0] = stockBullet();
            adjustDirection(bullets[0], -6);
            sprites().add(bullets[0]);

            bullets[1] = stockBullet();
            adjustDirection(bullets[1], 6);
            sprites().add(bullets[1]);

            bullets[2] = stockBullet();
            adjustDirection(bullets[2], -15);
            sprites().add(bullets[2]);

            bullets[3] = stockBullet();
            adjustDirection(bullets[3], 15);
            sprites().add(bullets[3]);

            bullets[4] = stockBullet();
            adjustDirection(bullets[4], -60);
            sprites().add(bullets[4]);

            bullets[5] = stockBullet();
            adjustDirection(bullets[5], 60);
            sprites().add(bullets[5]);
            break;
        }

        shoot.play();

    }

    private void adjustDirection(AnimatedSprite sprite, double angle) {
        angle = sprite.faceAngle() + angle;
        if (angle < 0) angle += 360;
        else if (angle > 360) angle -= 360;
        sprite.setFaceAngle(angle);
        sprite.setMoveAngle(sprite.faceAngle()-90);
        angle = sprite.moveAngle();
        double svx = calcAngleMoveX(angle) * BULLET_SPEED;
        double svy = calcAngleMoveY(angle) * BULLET_SPEED;
        sprite.setVelocity(new Point2D(svx, svy));
    }

     private AnimatedSprite stockBullet() {
         //the ship is always the first sprite in the linked list
         AnimatedSprite ship = (AnimatedSprite)sprites().get(0);

         AnimatedSprite bul = new AnimatedSprite(this, graphics());
         bul.setAlive(true);
         bul.setImage(bulletImage.getImage());
         bul.setFrameWidth(bulletImage.width());
         bul.setFrameHeight(bulletImage.height());
         bul.setSpriteType(SPRITE_BULLET);
         bul.setLifespan(90);
         bul.setFaceAngle(ship.faceAngle());
         bul.setMoveAngle(ship.faceAngle() - 90);
         //set the bullet's velocity
         double angle = bul.moveAngle();
         double svx = calcAngleMoveX(angle) * BULLET_SPEED;
         double svy = calcAngleMoveY(angle) * BULLET_SPEED;
         bul.setVelocity(new Point2D(svx, svy));
         //set the bullet's starting position
         double x = ship.center().X() - bul.imageWidth()/2;
         double y = ship.center().Y() - bul.imageHeight()/2;
         bul.setPosition(new Point2D(x,y));

         return bul;
     }

    /*****************************************************
     * launch a big explosion at the passed location
     *****************************************************/
    public void startBigExplosion(Point2D point) {
        //create a new explosion at the passed location
        AnimatedSprite expl = new AnimatedSprite(this,graphics());
        expl.setSpriteType(SPRITE_EXPLOSION);
        expl.setAlive(true);
        expl.setAnimImage(explosions[0].getImage());
        expl.setTotalFrames(16);
        expl.setColumns(4);
        expl.setFrameWidth(96);
        expl.setFrameHeight(96);
        expl.setFrameDelay(2);
        expl.setPosition(point);

        //add the new explosion to the sprite list
        sprites().add(expl);

        explosion.play();
    }

    /*****************************************************
     * launch a small explosion at the passed location
     *****************************************************/
    public void startSmallExplosion(Point2D point) {
        //create a new explosion at the passed location
        AnimatedSprite expl = new AnimatedSprite(this,graphics());
        expl.setSpriteType(SPRITE_EXPLOSION);
        expl.setAlive(true);
        expl.setAnimImage(explosions[1].getImage());
        expl.setTotalFrames(8);
        expl.setColumns(4);
        expl.setFrameWidth(40);
        expl.setFrameHeight(40);
        expl.setFrameDelay(2);
        expl.setPosition(point);

        //add the new explosion to the sprite list
        sprites().add(expl);

        explosion.play();

    }

    /*****************************************************
     * cause sprite to warp around the edges of the screen
     *****************************************************/
    public void warp(AnimatedSprite spr) {
        //create some shortcut variables
        int w = spr.frameWidth()-1;
        int h = spr.frameHeight()-1;

        //wrap the sprite around the screen edges
        if (spr.position().X() < 0-w)
            spr.position().setX(SCREENWIDTH);
        else if (spr.position().X() > SCREENWIDTH)
            spr.position().setX(0-w);
        if (spr.position().Y() < 0-h)
            spr.position().setY(SCREENHEIGHT);
        else if (spr.position().Y() > SCREENHEIGHT)
            spr.position().setY(0-h);
    }


    /*****************************************************
     * increment the score and update high score if needed
     *****************************************************/
    public void bumpScore(int howmuch) {
        score += howmuch;
        if (score > highscore)
            highscore = score;
    }
}