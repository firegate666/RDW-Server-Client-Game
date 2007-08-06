package de.mb.rdw;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

public class GameEngine extends JPanel implements MouseListener {
	final static Logger log = Logger.getLogger(GameEngine.class);

    private int mOffset;

    public GameEngine() {
        setBackground(Color.white);
        addMouseListener(this);
    }

    public void setOffset(int offset) {
        mOffset = offset;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Helvetica", Font.PLAIN, 24));
        g.setColor(Color.green);
        g.drawString("An Applet or an Application?", 10+mOffset, 50);
        g.drawString("That is the question.", 10+mOffset, 100);
    }

	public void mouseClicked(MouseEvent e) {
		log.info("mouseClicked");
	}

	public void mouseEntered(MouseEvent e) {
		log.info("mouseEntered");
	}

	public void mouseExited(MouseEvent e) {
		log.info("mouseExited");
	}

	public void mousePressed(MouseEvent e) {
		log.info("mousePressed");
	}

	public void mouseReleased(MouseEvent e) {
		log.info("mouseReleased");
	}

}
