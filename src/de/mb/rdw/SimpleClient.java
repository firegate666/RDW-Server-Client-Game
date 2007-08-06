package de.mb.rdw;

import hellojava3D.HelloJava3Dd;

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

import de.mb.rdw.swing.ChildCharacterFrame;
import de.mb.rdw.swing.ChildHelperFrame;
import de.mb.rdw.swing.ChildMapFrame;
import de.mb.rdw.swing.listener.PopupListener;
import de.mb.util.WebstartFileProvider;

public class SimpleClient extends JFrame {

	final static Logger log = Logger.getLogger(SimpleClient.class);

	private JDesktopPane desk;

	private Image img;

	public SimpleClient() {
		img = new ImageIcon(getClass().getResource(
				"/resource/images/background.jpg")).getImage();
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// defaults
		this.setVisible(false);
		this.setBounds(50, 50, 800, 720);
		this.setVisible(true);

		JButton character = new JButton("Neu");
		character.setBounds(0, 0, 48, 48);
		character.setBorder(BorderFactory.createEtchedBorder());
		character.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ChildCharacterFrame temp = new ChildCharacterFrame(
						"Charakterblatt", true);
				addChild(temp, 0, 0, false);
			}

		});
		getContentPane().add(character);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LogManager.resetConfiguration();
		try {
			DOMConfigurator.configure(WebstartFileProvider.getFile(
					"log4j-config.xml").toURL());
			SimpleClient rdwclient = new SimpleClient();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (FactoryConfigurationError e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}
}