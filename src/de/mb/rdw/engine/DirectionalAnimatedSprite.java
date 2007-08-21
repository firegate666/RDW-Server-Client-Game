package de.mb.rdw.engine;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class DirectionalAnimatedSprite extends AnimatedSprite {

	private Map<Integer, ImageEntity> imageSequences;
	private Map<Integer, Integer> angleImageMapping;

	protected int lastKey = 0;

	protected void set4ImageMapping() {
		angleImageMapping = new HashMap<Integer, Integer>();
		for (int i = 0; i < 45; i++)
			angleImageMapping.put(i, 0);
		for (int i = 45; i < 135; i++)
			angleImageMapping.put(i, 90);
		for (int i = 135; i < 225; i++)
			angleImageMapping.put(i, 180);
		for (int i = 225; i < 315; i++)
			angleImageMapping.put(i, 270);
		for (int i = 315; i < 360; i++)
			angleImageMapping.put(i, 0);
	}

	protected void setDefaultMapping() {
		angleImageMapping = new HashMap<Integer, Integer>();
		for (int i = 0; i < 360; i++)
			angleImageMapping.put(i, 0);
	}

	public DirectionalAnimatedSprite(Applet applet, Graphics2D g2d) {
		super(applet, g2d);
		imageSequences = new HashMap<Integer, ImageEntity>(8);
		setDefaultMapping();
	}

	public void loadImageSequence(String filename, int angle) {
		ImageEntity ie = new ImageEntity(applet());
		ie.load(filename);
		imageSequences.put(angle, ie);
	}

	public void updateFrame() {
		int angle = (int)faceAngle();
		if (angleImageMapping.containsKey(angle)) // get imagemapping for angle
			angle = angleImageMapping.get(angle);
		if (imageSequences.containsKey(angle)) // put image for angle
			setAnimImage(imageSequences.get(angle).getImage());
		super.updateFrame();
	}
}
