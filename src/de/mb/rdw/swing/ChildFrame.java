package de.mb.rdw.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

/**
 * basic child frame
 * 
 * @author Marco
 * 
 */
public class ChildFrame extends JInternalFrame {
	
	protected JPanel banner;
	
	public ChildFrame(String title, String image_res) {
		super(title);
		setIconifiable(true);
		setMaximizable(false);
		setResizable(false);
		setBounds(10, 10, 400, 600);
		setOpaque(false);
		//setBackground(null);
		setBorder(BorderFactory.createEtchedBorder());
		setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		final Image image = new ImageIcon(getClass().getResource(image_res)).getImage();
		banner = new JPanel(new BorderLayout()) {
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
			}
		};
		setLayout(new BorderLayout());
		add(banner, BorderLayout.CENTER);
		
	}
	
	public ChildFrame(String title) {
		this(title, "/resource/images/pergament.jpg");
	}
	
	public void addComp(Component comp, Object index) {
		banner.add(comp, index);
	}
}