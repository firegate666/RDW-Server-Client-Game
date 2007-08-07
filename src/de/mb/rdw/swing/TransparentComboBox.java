package de.mb.rdw.swing;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import org.apache.log4j.Logger;

public class TransparentComboBox extends JComboBox {
	final static Logger log = Logger.getLogger(TransparentComboBox.class);
	public TransparentComboBox() {
		super();
		initialize();
	}

	public TransparentComboBox(Object[] items) {
		super(items);
		initialize();
	}

	protected void initialize() {
		setOpaque(false);
		setRenderer(new ListCellRenderer() {

			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				TransparentLabel next = new TransparentLabel();
				if (value != null)
					next.setText(value.toString());
				return next;
			}

		});
	}
}


