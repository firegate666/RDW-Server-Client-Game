package de.mb.rdw.swing;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import de.mb.rdw.swing.gui.TransparentComboBox;
import de.mb.rdw.swing.gui.TransparentLabel;
import de.mb.rdw.swing.gui.TransparentPanel;
import de.mb.rdw.swing.gui.TransparentTextField;

public class ChildCharacterFrame extends ChildFrame {
	final static Logger log = Logger.getLogger(ChildCharacterFrame.class);

	protected Character character;
	protected JPanel mainpanel;

	protected JPanel[] container;
	protected int act_container = 0;

	public ChildCharacterFrame(String title) {
		super(title);

		banner.setLayout(null);
		setIconifiable(false);
		setClosable(true);

		mainpanel = new TransparentPanel();
		mainpanel.setBounds(50, 50, getWidth()-100, getHeight()-100);
		mainpanel.setLayout(new BorderLayout());
		addComp(mainpanel, BorderLayout.CENTER);

		mainpanel.add(getTopPanel(), BorderLayout.NORTH);
		mainpanel.add(getBottomPanel(), BorderLayout.SOUTH);

		container = new JPanel[4];
		container[0] = getDataPanel();
		container[1] = getAttributePanel();
		container[2] = getMagicPanel();
		container[3] = getExperiencePanel();
		mainpanel.add(container[act_container], BorderLayout.CENTER);
	}

	public JPanel getTopPanel() {
		TransparentPanel panel = new TransparentPanel(new GridLayout(2, 2));

		TransparentLabel label_name = new TransparentLabel("Name:");
		TransparentTextField text_name = new TransparentTextField();
		TransparentLabel label_type = new TransparentLabel("Typus:");
		TransparentComboBox text_type = new TransparentComboBox(new String[]{"Krieger", "Magier"});

		panel.add(label_name);
		panel.add(text_name);
		panel.add(label_type);
		panel.add(text_type);

		return panel;
	}

	/**
	 * switch panels
	 * 
	 * @param forward
	 */
	protected void switchPanel(boolean forward) {
		mainpanel.remove(container[act_container]);
		if (forward) {
			if (act_container < (container.length - 1))
				act_container++;
			else
				act_container = 0;
		} else {
			if (act_container > 0)
				act_container--;
			else
				act_container = container.length - 1;
		}
		mainpanel.add(container[act_container], BorderLayout.CENTER);
		mainpanel.updateUI();
	}

	public JPanel getBottomPanel() {
		TransparentPanel panel = new TransparentPanel(new GridLayout(1, 2));

		TransparentLabel prev = new TransparentLabel();
		prev.setHorizontalAlignment(JLabel.LEFT);
		prev.setVerticalAlignment(JLabel.CENTER);
		prev.setIcon(new ImageIcon(getClass().getResource("/resource/images/prev.png")));
		prev.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				log.info("prev clicked");
				switchPanel(false);

			}

			public void mouseEntered(MouseEvent e) {
				// TODO Automatisch erstellter Methoden-Stub

			}

			public void mouseExited(MouseEvent e) {
				// TODO Automatisch erstellter Methoden-Stub

			}

			public void mousePressed(MouseEvent e) {
				// TODO Automatisch erstellter Methoden-Stub

			}

			public void mouseReleased(MouseEvent e) {
				// TODO Automatisch erstellter Methoden-Stub

			}

		});
		TransparentLabel next = new TransparentLabel();
		next.setHorizontalAlignment(JLabel.RIGHT);
		next.setVerticalAlignment(JLabel.CENTER);
		next.setIcon(new ImageIcon(getClass().getResource("/resource/images/next.png")));
		next.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				log.info("next clicked");
				switchPanel(true);

			}

			public void mouseEntered(MouseEvent e) {
				// TODO Automatisch erstellter Methoden-Stub

			}

			public void mouseExited(MouseEvent e) {
				// TODO Automatisch erstellter Methoden-Stub

			}

			public void mousePressed(MouseEvent e) {
				// TODO Automatisch erstellter Methoden-Stub

			}

			public void mouseReleased(MouseEvent e) {
				// TODO Automatisch erstellter Methoden-Stub

			}

		});

		panel.add(prev);
		panel.add(next);

		return panel;
	}

	public JPanel getExperiencePanel() {
		TransparentPanel panel = new TransparentPanel(null);

		TransparentLabel header = new TransparentLabel("Stufe und Erfahrung");
		header.setFont(new Font("Verdana", Font.BOLD, 16));
		panel.add(header, 10, 10, 300, 50);

		TransparentLabel label_level_k = new TransparentLabel("Kampf");
		TransparentLabel label_level_w = new TransparentLabel("Weisheit");

		TransparentLabel label_level = new TransparentLabel("Stufe:");
		TransparentTextField text_level_k = new TransparentTextField("1");
		TransparentTextField text_level_w = new TransparentTextField("1");

		TransparentLabel label_xp = new TransparentLabel("Erfahrung:");
		TransparentTextField text_kep = new TransparentTextField("0");
		TransparentTextField text_Wep = new TransparentTextField("0");

		int x = 0; 
		int y = 50;
		panel.add(label_level_k, x+100, y+10, 50, 23);
		panel.add(label_level_w, x+165, y+10, 70, 23);

		panel.add(label_level, x+10, y+40, 100, 23);
		panel.add(text_level_k, x+100, y+40, 23, 23);
		panel.add(text_level_w, x+165, y+40, 23, 23);

		panel.add(label_xp, x+10, y+70, 100, 23);
		panel.add(text_kep, x+100, y+70, 50, 23);
		panel.add(text_Wep, x+165, y+70, 50, 23);
		return panel;
	}
	
	public JPanel getMagicPanel() {
		TransparentPanel panel = new TransparentPanel(null);

		TransparentLabel header = new TransparentLabel("Magie");
		header.setFont(new Font("Verdana", Font.BOLD, 16));
		panel.add(header, 10, 10, 300, 50);

/*		TransparentLabel label_titel = new TransparentLabel("Titel:");
		TransparentTextField text_titel = new TransparentTextField();

		int x = 0; 
		int y = 50;
		panel.add(label_titel, x+10, y+10, 100, 23);
		panel.add(text_titel, x+90, y+10, 100, 23);
*/
		return panel;
	}

	public JPanel getDataPanel() {
		TransparentPanel panel = new TransparentPanel(null);

		TransparentLabel header = new TransparentLabel("Allgemeine Daten");
		header.setFont(new Font("Verdana", Font.BOLD, 16));
		panel.add(header, 10, 10, 300, 50);

		TransparentLabel label_titel = new TransparentLabel("Titel:");
		TransparentTextField text_titel = new TransparentTextField();

		int x = 0; 
		int y = 50;
		panel.add(label_titel, x+10, y+10, 100, 23);
		panel.add(text_titel, x+90, y+10, 100, 23);

		return panel;
	}

	public JPanel getAttributePanel() {
		TransparentPanel panel = new TransparentPanel(null);

		TransparentLabel header = new TransparentLabel("Attribute");
		header.setFont(new Font("Verdana", Font.BOLD, 16));
		panel.add(header, 10, 10, 300, 50);
		
		TransparentLabel label_auss = new TransparentLabel("Aussehen:");
		TransparentTextField text_auss = new TransparentTextField();
		TransparentLabel label_kl = new TransparentLabel("Klugheit:");
		TransparentTextField text_kl = new TransparentTextField();
		TransparentLabel label_wei = new TransparentLabel("Weisheit:");
		TransparentTextField text_wei = new TransparentTextField();
		TransparentLabel label_tak = new TransparentLabel("Tapferkeit:");
		TransparentTextField text_tak = new TransparentTextField();
		TransparentLabel label_ausd = new TransparentLabel("Ausdauer:");
		TransparentTextField text_ausd = new TransparentTextField();
		TransparentLabel label_kk = new TransparentLabel("Körperkraft:");
		TransparentTextField text_kk = new TransparentTextField();
		TransparentLabel label_ge = new TransparentLabel("Gewandheit:");
		TransparentTextField text_ge = new TransparentTextField();

		int x = 0; 
		int y = 50;
		
		panel.add(label_auss, x+10, y+10, 100, 23);
		panel.add(text_auss, x+90, y+10, 23, 23);
		panel.add(label_kl, x+10, y+40, 100, 23);
		panel.add(text_kl, x+90, y+40, 23, 23);
		panel.add(label_wei, x+10, y+70, 100, 23);
		panel.add(text_wei, x+90, y+70, 23, 23);
		panel.add(label_tak, x+10, y+100, 100, 23);
		panel.add(text_tak, x+90, y+100, 23, 23);
		panel.add(label_ausd, x+10, y+130, 100, 23);
		panel.add(text_ausd, x+90, y+130, 23, 23);
		panel.add(label_kk, x+10, y+160, 100, 23);
		panel.add(text_kk, x+90, y+160, 23, 23);
		panel.add(label_ge, x+10, y+190, 100, 23);
		panel.add(text_ge, x+90, y+190, 23, 23);

		TransparentLabel label_st = new TransparentLabel("Standhaftigkeit:");
		TransparentTextField text_st = new TransparentTextField();
		TransparentLabel label_ins = new TransparentLabel("Instinkt:");
		TransparentTextField text_ins = new TransparentTextField();
		TransparentLabel label_pg = new TransparentLabel("Psy. Gesundheit:");
		TransparentTextField text_pg = new TransparentTextField();
		TransparentLabel label_if = new TransparentLabel("Immunitätsfaktor:");
		TransparentTextField text_if = new TransparentTextField();
		TransparentLabel label_kf = new TransparentLabel("Konzentrationsfaktor:");
		TransparentTextField text_kf = new TransparentTextField();

		panel.add(label_st, x+130, y+10, 140, 23);
		panel.add(text_st, x+260, y+10, 23, 23);
		panel.add(label_ins, x+130, y+40, 140, 23);
		panel.add(text_ins, x+260, y+40, 23, 23);
		panel.add(label_pg, x+130, y+70, 140, 23);
		panel.add(text_pg, x+260, y+70, 23, 23);
		panel.add(label_if, x+130, y+100, 140, 23);
		panel.add(text_if, x+260, y+100, 23, 23);
		panel.add(label_kf, x+130, y+130, 140, 23);
		panel.add(text_kf, x+260, y+130, 23, 23);

		return panel;
	}


}
