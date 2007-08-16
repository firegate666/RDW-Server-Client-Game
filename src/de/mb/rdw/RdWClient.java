package de.mb.rdw;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.BorderFactory;
import javax.swing.DefaultDesktopManager;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.xml.parsers.FactoryConfigurationError;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import de.mb.rdw.engine.RdwGameEngine;
import de.mb.rdw.swing.ChildCharacterFrame;
import de.mb.rdw.swing.ChildHelperFrame;
import de.mb.rdw.swing.ChildMapFrame;
import de.mb.rdw.swing.listener.PopupListener;
import de.mb.util.WebstartFileProvider;

public class RdWClient extends JFrame {

	final static Logger log = Logger.getLogger(RdWClient.class);
	private JDesktopPane desk;

	private Image img;

	protected JPopupMenu popup = new JPopupMenu();

	public RdWClient() {
		img = new ImageIcon(getClass().getResource("/resource/images/background.jpg")).getImage();
		initialize();
	}

	/**
	 * add child frame
	 *
	 * @param child
	 * @param x
	 * @param y
	 * @param icon
	 */
	public void addChild(JInternalFrame child, int x, int y, boolean icon) {
		child.setLocation(x, y);
		getContentPane().add(child);
		try {
			child.setIcon(icon);
		} catch (PropertyVetoException e) {
			// TODO Automatisch erstellter Catch-Block
			e.printStackTrace();
		}
		child.setVisible(true);
		child.repaint();
	}

	protected void initialize() {
		// set desktop manager
		this.desk = new JDesktopPane() {
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(img, 0, 0, getSize().width, getSize().height, this);
			}
		};
		desk.setDesktopManager(new DefaultDesktopManager());
		desk.setOpaque(false);
		desk.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
		setContentPane(desk);

		// defaults
		this.setVisible(false);
		this.setAlwaysOnTop(false);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(0, 0, screenSize.width, screenSize.height);
		this.setUndecorated(true);
		this.setResizable(false);
		this.setVisible(true);

		// top picture
		/*JLabel banner = new JLabel() {
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(new ImageIcon(getClass().getResource("/resource/images/banner.jpg")).getImage(), 0, 0, getWidth(), 144, this);
			}
		};

		banner.setBounds(0,0,getWidth(), 144);
		getContentPane().add(banner);*/

		// add exit button
		JButton exit = new JButton();
		exit.setContentAreaFilled(false);
		exit.setIcon(new ImageIcon(getClass().getResource("/resource/images/logout.png")));
		exit.setBounds(getWidth() - 48, getHeight() - 48, 48, 48);
		exit.setBorder(BorderFactory.createEmptyBorder());
		exit.setMargin(new Insets(0, 0, 0, 0));
		exit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}

		});
		getContentPane().add(exit);

		JButton character = new JButton();
		character.setContentAreaFilled(false);
		character.setIcon(new ImageIcon(getClass().getResource("/resource/images/character.gif")));
		character.setBounds(getWidth() - 96, getHeight() - 48, 48, 48);
		character.setBorder(BorderFactory.createEmptyBorder());
		character.setMargin(new Insets(0, 0, 0, 0));
		/*character.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ChildCharacterFrame temp = new ChildCharacterFrame("Charakterblatt");
				//addChild(temp, 300, 300, false);
			}

		});*/
		getContentPane().add(character);

		/*ChildHelperFrame child2 = new ChildHelperFrame("Hilfe");
		//this.addChild(child2, 150, 150, true);

		ChildMapFrame child3 = new ChildMapFrame("Map");
		//this.addChild(child3, 300, 300, true);
*/
		RdwGameEngine game = new RdwGameEngine(800, 600);
		game.init();
		int width = 800;
		int height = 600;
		int x = (getWidth() - width) / 2;
		int y = (getHeight() - height) / 2;
		game.setBounds(x, y, width, height);
		add(game);

		// popup
		JMenuItem close = new JMenuItem("beenden");
		close.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}

		});
		popup.add(close);
		MouseListener popupListener = new PopupListener(popup);
		this.addMouseListener(popupListener);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LogManager.resetConfiguration();
		try {
			DOMConfigurator.configure(WebstartFileProvider.getFile(
					"log4j-config.xml").toURL());
			RdWClient rdwclient = new RdWClient();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (FactoryConfigurationError e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	/*
	 * public void paint(Graphics g) { g.drawImage(img, 0, 0, getSize().width,
	 * getSize().height, this); super.paint(g); }
	 */
}

