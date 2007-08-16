package de.mb.rdw.engine;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class DirectionalAnimatedSprite extends AnimatedSprite {

	private Map<Integer, ImageEntity> imageSequences;

	protected int lastKey = 0;

	public DirectionalAnimatedSprite(Applet applet, Graphics2D g2d) {
		super(applet, g2d);
		imageSequences = new HashMap<Integer, ImageEntity>(8);
	}

	public void loadImageSequence(String filename, int angle) {
		ImageEntity ie = new ImageEntity(applet());
		ie.load(filename);
		imageSequences.put(angle, ie);
	}

	public void updateFrame() {
		if (faceAngle() != lastKey) {
			setCurrentFrame(0);
			setAnimImage(imageSequences.get((int)faceAngle()).getImage());
		}
		super.updateFrame();
	}
}
