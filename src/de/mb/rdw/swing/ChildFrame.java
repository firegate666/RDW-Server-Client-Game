package de.mb.rdw.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

/**
 * basic child frame
 *
 * @author Marco
 *
 */
public class ChildFrame extends JDialog implements MouseListener,
		MouseMotionListener {
	final static Logger log = Logger.getLogger(ChildFrame.class);

	protected JPanel banner = new JPanel(new BorderLayout());

	protected boolean moveMode = false;

	/** true if we are in drag */
	boolean inDrag = false;

	/** starting location of a drag */
	int startX = -1, startY = -1;

	/** current location of a drag */
	int curX = -1, curY = -1;

	public ChildFrame(Frame owner, String title, String image_res) {
		super(owner);
		addMouseListener(this);
		addMouseMotionListener(this);
		setResizable(false);
		setBounds(10, 10, 400, 600);
		setUndecorated(true);
		setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		if (image_res != null) {
			final Image image = new ImageIcon(getClass().getResource(image_res))
					.getImage();
			banner = new JPanel(new BorderLayout()) {
				protected void paintComponent(Graphics g) {
					super.paintComponent(g);
					g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
				}
			};
			add(banner, BorderLayout.CENTER);
		}
		TransparentButton btn_exit = new TransparentButton();
		btn_exit.setIcon(new ImageIcon(getClass().getResource("/resource/images/exit.gif")));
		btn_exit.setBounds(0,0,16,16);
		btn_exit.setBorder(BorderFactory.createLineBorder(new Color(50,50,50), 1));
		btn_exit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}

		});
		addComp(btn_exit, BorderLayout.SOUTH);
	}

	public void addComp(Component comp, Object index) {
		banner.add(comp, index);
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
		if (e.getButton() == MouseEvent.BUTTON1) {
			startX = e.getPoint().x - getLocation().x;
			startY = e.getPoint().y - getLocation().y;
			inDrag = true;
		}
	}

	/** Called when the mouse has been released. */
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			inDrag = false;
			setLocation(curX - startX, curY - startY);
		}
	}

	public void mouseDragged(MouseEvent e) {
		curX = e.getPoint().x;
		curY = e.getPoint().y;

	}

	public void mouseMoved(MouseEvent e) {
	}
}