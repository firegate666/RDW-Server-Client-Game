package de.mb.rdw.swing;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;

import de.mb.rdw.model.GameCharacter;
import de.mb.rdw.model.GameCharacterType;
import de.mb.rdw.model.items.Item;
import de.mb.rdw.swing.implementations.TransparentAttributeComboBox;
import de.mb.rdw.swing.implementations.TransparentAttributeTextArea;
import de.mb.rdw.swing.implementations.TransparentAttributeTextField;
import de.mb.util.Utils;

public class ChildCharacterFrame extends ChildFrame implements ActionListener {
	final static Logger log = Logger.getLogger(ChildCharacterFrame.class);

	protected GameCharacter character = new GameCharacter();

	protected JPanel mainpanel = new TransparentPanel();

	protected JPanel[] container;

	protected String basetitle;

	protected int act_container = 0;

	public ChildCharacterFrame(String title) {
		this(title, false);
	}

	public ChildCharacterFrame(String title, boolean iconifiable) {
		super(title);
		basetitle = title;
		banner.setLayout(null);
		setIconifiable(iconifiable);
		setClosable(true);
		initPanels();
	}

	protected void initPanels() {
		if (!character.getAttribute("name").equalsIgnoreCase(""))
			setTitle(character.getAttribute("name"));
		else
			setTitle(basetitle);
		mainpanel.removeAll();
		mainpanel.setBounds(50, 50, getWidth() - 100, getHeight() - 100);
		mainpanel.setLayout(new BorderLayout());
		addComp(mainpanel, BorderLayout.CENTER);

		mainpanel.add(getTopPanel(), BorderLayout.NORTH);
		mainpanel.add(getBottomPanel(), BorderLayout.SOUTH);

		int i = 0;
		container = new JPanel[] { getDataPanel(), getAttributePanel(),
				getMagicPanel(), getEquipmentPanel(), getExperiencePanel(), };
		mainpanel.add(container[act_container], BorderLayout.CENTER);
		mainpanel.updateUI();
	}

	public JPanel getTopPanel() {
		TransparentPanel panel = new TransparentPanel(new GridLayout(2, 2));

		TransparentLabel label_name = new TransparentLabel("Name:");
		TransparentAttributeTextField text_name = new TransparentAttributeTextField(
				character, "name", -1, JTextField.LEFT);
		TransparentLabel label_type = new TransparentLabel("Typus:");
		final TransparentAttributeComboBox text_type = new TransparentAttributeComboBox(
				GameCharacterType.getAvailableCharacters(character), character,
				"type");
		text_type.addMouseListener(new MouseListener() {

			public void mouseEntered(MouseEvent e) {
				Object item = text_type.getSelectedItem();
				DefaultComboBoxModel model = new DefaultComboBoxModel(
						GameCharacterType.getAvailableCharacters(character));
				text_type.setModel(model);
				model.setSelectedItem(item);
			}

			public void mouseClicked(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}
		});

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
		TransparentPanel panel = new TransparentPanel(new GridLayout(1, 5));

		TransparentButton prev = new TransparentButton();
		prev.setHorizontalAlignment(JLabel.LEFT);
		prev.setVerticalAlignment(JLabel.CENTER);
		prev.setIcon(new ImageIcon(getClass().getResource(
				"/resource/images/prev.png")));
		prev.setActionCommand("PREV");
		prev.addActionListener(this);

		TransparentButton next = new TransparentButton();
		next.setHorizontalAlignment(JLabel.RIGHT);
		next.setVerticalAlignment(JLabel.CENTER);
		next.setIcon(new ImageIcon(getClass().getResource(
				"/resource/images/next.png")));
		next.setActionCommand("NEXT");
		next.addActionListener(this);

		TransparentButton load = new TransparentButton();
		load.setActionCommand("LOAD");
		load.addActionListener(this);
		load.setIcon(new ImageIcon(getClass().getResource(
				"/resource/images/load.gif")));
		TransparentButton clear = new TransparentButton();
		clear.setActionCommand("CLEAR");
		clear.addActionListener(this);
		clear.setIcon(new ImageIcon(getClass().getResource(
				"/resource/images/create.gif")));
		TransparentButton save = new TransparentButton();
		save.setActionCommand("SAVE");
		save.addActionListener(this);
		save.setIcon(new ImageIcon(getClass().getResource(
				"/resource/images/save.gif")));

		panel.add(prev);
		panel.add(clear);
		panel.add(load);
		panel.add(save);
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
		TransparentAttributeTextField text_level_k = new TransparentAttributeTextField(
				character, "level_k", 2);
		TransparentAttributeTextField text_level_w = new TransparentAttributeTextField(
				character, "level_w", 2);

		TransparentLabel label_xp = new TransparentLabel("Erfahrung:");
		TransparentAttributeTextField text_kep = new TransparentAttributeTextField(
				character, "kep", 2);
		TransparentAttributeTextField text_Wep = new TransparentAttributeTextField(
				character, "wep", 2);

		int x = 0;
		int y = 50;
		panel.add(label_level_k, x + 100, y + 10, 50, 23);
		panel.add(label_level_w, x + 165, y + 10, 70, 23);

		panel.add(label_level, x + 10, y + 40, 100, 23);
		panel.add(text_level_k, x + 127, y + 40, 23, 23);
		panel.add(text_level_w, x + 192, y + 40, 23, 23);

		panel.add(label_xp, x + 10, y + 70, 100, 23);
		panel.add(text_kep, x + 100, y + 70, 50, 23);
		panel.add(text_Wep, x + 165, y + 70, 50, 23);
		return panel;
	}

	public JPanel getMagicPanel() {
		TransparentPanel panel = new TransparentPanel(null);

		TransparentLabel header = new TransparentLabel("Magie");
		header.setFont(new Font("Verdana", Font.BOLD, 16));
		panel.add(header, 10, 10, 300, 50);

		return panel;
	}

	public JPanel getEquipmentPanel() {
		TransparentPanel panel = new TransparentPanel(null);

		TransparentLabel header = new TransparentLabel("Ausrüstung");
		header.setFont(new Font("Verdana", Font.BOLD, 16));
		panel.add(header, 10, 10, 300, 50);

		TransparentList text_equipment = new TransparentList(Item.getList());
		TransparentButton button_add = new TransparentButton();
		button_add.setIcon(new ImageIcon(getClass().getResource(
				"/resource/images/add.gif")));
		button_add.setToolTipText("Ausrüstungsgegenstand hinzufügen");

		int x = 0;
		int y = 50;
		panel.add(text_equipment.getScrollPane(), x + 10, y + 10, 280, 280);
		panel.add(button_add, x + 125, y + 295, 40, 40);

		return panel;
	}

	public JPanel getDataPanel() {
		TransparentPanel panel = new TransparentPanel(null);

		TransparentLabel header = new TransparentLabel("Allgemeine Daten");
		header.setFont(new Font("Verdana", Font.BOLD, 16));
		panel.add(header, 10, 10, 300, 50);

		TransparentLabel label_titel = new TransparentLabel("Titel:");
		TransparentAttributeTextField text_titel = new TransparentAttributeTextField(
				character, "titel", -1, JTextField.LEFT);

		TransparentLabel label_age = new TransparentLabel("Alter:");
		TransparentAttributeTextField text_age = new TransparentAttributeTextField(
				character, "age", 4);

		TransparentLabel label_size = new TransparentLabel("Größe:");
		TransparentAttributeTextField text_size = new TransparentAttributeTextField(
				character, "size", 3);

		TransparentLabel label_weight = new TransparentLabel("Gewicht:");
		TransparentAttributeTextField text_weight = new TransparentAttributeTextField(
				character, "weight", 3);

		TransparentLabel label_tg = new TransparentLabel("Barschaft:");
		TransparentAttributeTextField text_tg = new TransparentAttributeTextField(
				character, "tg");

		TransparentLabel label_rz = new TransparentLabel("Resistenz");
		TransparentAttributeTextField text_rz1 = new TransparentAttributeTextField(
				character, "rz1");
		TransparentAttributeTextField text_rz2 = new TransparentAttributeTextField(
				character, "rz2");
		TransparentAttributeTextField text_rz3 = new TransparentAttributeTextField(
				character, "rz3");
		TransparentAttributeTextField text_rz4 = new TransparentAttributeTextField(
				character, "rz4");

		TransparentButton text_image = new TransparentButton("Bild?");
		text_image.setBorder(BorderFactory.createEtchedBorder());

		TransparentLabel label_notes = new TransparentLabel("Sonstiges");
		TransparentAttributeTextArea text_notes = new TransparentAttributeTextArea(
				character, "notes");

		int x = 0;
		int y = 50;
		panel.add(label_titel, x + 10, y + 10, 100, 23);
		panel.add(text_titel, x + 90, y + 10, 200, 23);

		panel.add(label_age, x + 20, y + 40, 100, 23);
		panel.add(text_age, x + 90, y + 40, 40, 23);
		panel.add(new TransparentLabel("Jahre"), x + 132, y + 40, 40, 23);

		panel.add(label_size, x + 20, y + 70, 100, 23);
		panel.add(text_size, x + 90, y + 70, 40, 23);
		panel.add(new TransparentLabel("cm"), x + 132, y + 70, 40, 23);

		panel.add(label_weight, x + 20, y + 100, 100, 23);
		panel.add(text_weight, x + 90, y + 100, 40, 23);
		panel.add(new TransparentLabel("kg"), x + 132, y + 100, 40, 23);

		panel.add(label_tg, x + 20, y + 130, 100, 23);
		panel.add(text_tg, x + 90, y + 130, 40, 23);
		panel.add(new TransparentLabel("TG"), x + 132, y + 130, 40, 23);

		panel.add(label_rz, x + 60, y + 160, 100, 23);
		panel.add(text_rz1, x + 20, y + 190, 23, 23);
		panel.add(text_rz2, x + 60, y + 190, 23, 23);
		panel.add(text_rz3, x + 100, y + 190, 23, 23);
		panel.add(text_rz4, x + 140, y + 190, 23, 23);

		panel.add(text_image, x + 170, y + 40, 120, 173);

		panel.add(label_notes, x + 10, y + 220, 100, 23);
		panel.add(text_notes.getScrollPane(), x + 10, y + 240, 280, 80);

		return panel;
	}

	public JPanel getAttributePanel() {
		TransparentPanel panel = new TransparentPanel(null);

		TransparentLabel header = new TransparentLabel("Attribute");
		header.setFont(new Font("Verdana", Font.BOLD, 16));
		panel.add(header, 10, 10, 300, 50);

		TransparentLabel label_auss = new TransparentLabel("Aussehen:");
		TransparentAttributeTextField text_auss = new TransparentAttributeTextField(
				character, "auss", 2);
		TransparentLabel label_kl = new TransparentLabel("Klugheit:");
		TransparentAttributeTextField text_kl = new TransparentAttributeTextField(
				character, "kl", 2);
		TransparentLabel label_wei = new TransparentLabel("Weisheit:");
		TransparentAttributeTextField text_wei = new TransparentAttributeTextField(
				character, "wei", 2);
		TransparentLabel label_tak = new TransparentLabel("Tapferkeit:");
		TransparentAttributeTextField text_tak = new TransparentAttributeTextField(
				character, "tak", 2);
		TransparentLabel label_ausd = new TransparentLabel("Ausdauer:");
		TransparentAttributeTextField text_ausd = new TransparentAttributeTextField(
				character, "ausd", 2);
		TransparentLabel label_kk = new TransparentLabel("Körperkraft:");
		TransparentAttributeTextField text_kk = new TransparentAttributeTextField(
				character, "kk", 2);
		TransparentLabel label_ge = new TransparentLabel("Gewandheit:");
		TransparentAttributeTextField text_ge = new TransparentAttributeTextField(
				character, "ge", 2);

		int x = 0;
		int y = 50;

		panel.add(label_auss, x + 10, y + 10, 100, 23);
		panel.add(text_auss, x + 90, y + 10, 23, 23);
		panel.add(label_kl, x + 10, y + 40, 100, 23);
		panel.add(text_kl, x + 90, y + 40, 23, 23);
		panel.add(label_wei, x + 10, y + 70, 100, 23);
		panel.add(text_wei, x + 90, y + 70, 23, 23);
		panel.add(label_tak, x + 10, y + 100, 100, 23);
		panel.add(text_tak, x + 90, y + 100, 23, 23);
		panel.add(label_ausd, x + 10, y + 130, 100, 23);
		panel.add(text_ausd, x + 90, y + 130, 23, 23);
		panel.add(label_kk, x + 10, y + 160, 100, 23);
		panel.add(text_kk, x + 90, y + 160, 23, 23);
		panel.add(label_ge, x + 10, y + 190, 100, 23);
		panel.add(text_ge, x + 90, y + 190, 23, 23);

		TransparentLabel label_st = new TransparentLabel("Standhaftigkeit:");
		TransparentAttributeTextField text_st = new TransparentAttributeTextField(
				character, "st", 2);
		TransparentLabel label_ins = new TransparentLabel("Instinkt:");
		TransparentAttributeTextField text_ins = new TransparentAttributeTextField(
				character, "ins", 2);
		TransparentLabel label_pg = new TransparentLabel("Psy. Gesundheit:");
		TransparentAttributeTextField text_pg = new TransparentAttributeTextField(
				character, "pg", 3);
		TransparentLabel label_if = new TransparentLabel("Immunitätsfaktor:");
		TransparentAttributeTextField text_if = new TransparentAttributeTextField(
				character, "if", 2);
		TransparentLabel label_kf = new TransparentLabel(
				"Konzentrationsfaktor:");
		TransparentAttributeTextField text_kf = new TransparentAttributeTextField(
				character, "kf", 2);

		panel.add(label_st, x + 130, y + 10, 140, 23);
		panel.add(text_st, x + 260, y + 10, 23, 23);
		panel.add(label_ins, x + 130, y + 40, 140, 23);
		panel.add(text_ins, x + 260, y + 40, 23, 23);
		panel.add(label_pg, x + 130, y + 70, 140, 23);
		panel.add(text_pg, x + 250, y + 70, 33, 23);
		panel.add(label_if, x + 130, y + 100, 140, 23);
		panel.add(text_if, x + 260, y + 100, 23, 23);
		panel.add(label_kf, x + 130, y + 130, 140, 23);
		panel.add(text_kf, x + 260, y + 130, 23, 23);

		return panel;
	}

	protected String[] getCharacterList() {
		File dir = new File("./");
		File[] filelist = dir.listFiles(new FilenameFilter() {

			public boolean accept(File dir, String name) {
				return name.endsWith(".chr");
			}

		});
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < filelist.length; i++) {
			String temp = filelist[i].getName().replaceAll("\\.chr", "");
			if (!temp.equalsIgnoreCase("") && filelist[i].isFile())
				list.add(temp);
		}
		String[] result = new String[list.size()];
		list.toArray(result);
		return result;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equalsIgnoreCase("PREV")) {
			switchPanel(false);
		} else if (e.getActionCommand().equalsIgnoreCase("NEXT")) {
			switchPanel(true);
		} else if (e.getActionCommand().equalsIgnoreCase("CLEAR")) {
			character = new GameCharacter();
			initPanels();
		} else if (e.getActionCommand().equalsIgnoreCase("LOAD")) {
			String[] list = getCharacterList();
			if (list.length == 0)
				return;
			String s = (String) JOptionPane.showInputDialog(this,
					"Wähle Deinen Charakter", "Name: ",
					JOptionPane.QUESTION_MESSAGE, null, list, list[0]);
			if (s == null)
				return;
			String xml = Utils.readFromFile(s + ".chr");
			try {
				character = GameCharacter.getFromXml(xml);
				initPanels();
			} catch (MarshalException e1) {
				log.error(e1);
			} catch (ValidationException e1) {
				log.error(e1);
			}
		} else if (e.getActionCommand().equalsIgnoreCase("SAVE")) {
			if (character.getAttribute("name").equalsIgnoreCase(""))
				return;
			try {
				String xml = character.writeToXml();
				Utils.writeToFile(character.getAttribute("name") + ".chr", xml,
						"ISO-8859-1");
				initPanels();
			} catch (MarshalException e1) {
				log.error(e1);
			} catch (ValidationException e1) {
				log.error(e1);
			}
		} else
			log.warn("unknown action command");

	}

}
