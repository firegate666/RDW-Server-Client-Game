package de.mb.rdw.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import de.mb.rdw.swing.listener.MoveWindowListener;

/**
 * basic child frame
 *
 * @author Marco
 *
 */
public class ChildFrame extends JDialog {
	final static Logger log = Logger.getLogger(ChildFrame.class);

	protected JPanel banner = new JPanel(new BorderLayout());

	public ChildFrame(Frame owner, String title, String image_res) {
		super(owner);
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
		btn_exit.setIcon(new ImageIcon(getClass().getResource(
				"/resource/images/exit.gif")));
		btn_exit.setBounds(0, 0, 16, 16);
		btn_exit.setBorder(BorderFactory.createLineBorder(
				new Color(50, 50, 50), 1));
		btn_exit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}

		});
		addComp(btn_exit, BorderLayout.SOUTH);

		// add support for moving even if undecorated
		MoveWindowListener moveWindowListener = new MoveWindowListener(this);
		addMouseListener(moveWindowListener);
		addMouseMotionListener(moveWindowListener);
	}

	public void addComp(Component comp, Object index) {
		banner.add(comp, index);
	}
}