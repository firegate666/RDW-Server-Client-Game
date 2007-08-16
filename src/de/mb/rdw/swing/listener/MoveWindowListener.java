package de.mb.rdw.swing.listener;

import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MoveWindowListener implements MouseListener, MouseMotionListener {


	/** true if we are in drag */
	protected boolean inDrag = false;

	/** starting location of a drag */
	protected int startX = -1, startY = -1;

	/** current location of a drag */
	protected int curX = -1, curY = -1;

	protected Window parent;

	public MoveWindowListener(Window parent) {
		this.parent = parent;
	}

	/** Called when the mouse has been clicked on a component. */
	public void mouseClicked(MouseEvent e) {
	}

	/** Called when the mouse enters a component. */
	public void mouseEntered(MouseEvent e) {
	}

	/** Called when the mouse exits a component. */
	public void mouseExited(MouseEvent e) {
	}

	/** Called when the mouse has been pressed. */
	public void mousePressed(MouseEvent e) {
		startX = e.getPoint().x - parent.getX();
		startY = e.getPoint().y - parent.getY();
	}

	/** Called when the mouse has been released. */
	public void mouseReleased(MouseEvent e) {
		if (inDrag && e.getButton() == MouseEvent.BUTTON1) {
			inDrag = false;
			parent.setLocation(curX - startX, curY - startY);
		}
	}

	public void mouseDragged(MouseEvent e) {
		inDrag = true;
		curX = e.getPoint().x;
		curY = e.getPoint().y;
	}

	public void mouseMoved(MouseEvent e) {
	}
}