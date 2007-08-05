package de.mb.rdw.swing.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;

/**
 * Get Popuplistener for popupmenu
 * 
 * @author Marco
 * 
 */
public class PopupListener extends MouseAdapter {

	JPopupMenu popup;

	/**
	 * 
	 * @param popup
	 */
	public PopupListener(JPopupMenu popup) {
		this.popup = popup;
	}

	public void mousePressed(MouseEvent e) {
		maybeShowPopup(e);
	}

	public void mouseReleased(MouseEvent e) {
		maybeShowPopup(e);
	}

	private void maybeShowPopup(MouseEvent e) {
		if (e.isPopupTrigger()) {
			popup.show(e.getComponent(), e.getX(), e.getY());
		}
	}
}