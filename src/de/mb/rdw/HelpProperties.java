package de.mb.rdw;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import de.mb.util.Utils;

public class HelpProperties {
	final static Logger log = Logger.getLogger(HelpProperties.class);
	private static final String BUNDLE_NAME = "resource.properties.help"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private HelpProperties() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
