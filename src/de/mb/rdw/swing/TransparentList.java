package de.mb.rdw.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.border.EtchedBorder;

import org.apache.log4j.Logger;

import de.mb.rdw.model.items.Item;

public class TransparentList extends JList {
	final static Logger log = Logger.getLogger(TransparentList.class);

	protected JScrollPane scrollPane;

	public TransparentList() {
		super();
		initialize();
	}

	public TransparentList(Object[] items) {
		super();
		initialize();
		DefaultListModel model = new DefaultListModel();
		for (int i = 0; i < items.length; i++)
			model.addElement(items[i]);
		setModel(model);
	}

	protected void initialize() {
		scrollPane = new JScrollPane();
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.getViewport().setView(this);
		scrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		setOpaque(false);
		setBorder(new EtchedBorder());
		setCellRenderer(new ListCellRenderer() {

			public Component getListCellRendererComponent(JList list,
					Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				TransparentPanel panel = new TransparentPanel(new BorderLayout());
				TransparentLabel next = new TransparentLabel();
				TransparentLabel num = new TransparentLabel();
				TransparentLabel weight = new TransparentLabel();

				if (value != null) {
					Item temp = (Item)value;
					num.setText("1 x ");
					num.setMinimumSize(new Dimension(23, 23));
					next.setText(temp.getName());
					weight.setText(temp.getWeight()+" kg");
				}

				panel.add(num, BorderLayout.WEST);
				panel.add(next, BorderLayout.CENTER);
				panel.add(weight, BorderLayout.EAST);

				if (isSelected)
					panel.setBorder(BorderFactory.createEtchedBorder());
				return panel;
			}

		});

		addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
				if ((getSelectedIndex() > 0)
						&& ((e.getKeyCode() == KeyEvent.VK_BACK_SPACE) || (e
								.getKeyCode() == KeyEvent.VK_DELETE))) {
					((DefaultListModel) getModel())
							.removeElementAt(getSelectedIndex());
				}

			}

			public void keyTyped(KeyEvent e) {
			}

		});

	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}
}
