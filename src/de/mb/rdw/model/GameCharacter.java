package de.mb.rdw.model;

import java.io.Serializable;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;

import sync4j.syncclient.common.XMLHashMapParser;
import de.mb.rdw.RdWClient;

/**
 * basic character
 *
 * @author mbehnke
 *
 */
public class GameCharacter implements Serializable {

	transient final static Logger log = Logger.getLogger(RdWClient.class);

	protected HashMap <String, String> attributes = new HashMap<String, String>();

	public String getAttribute(String key) {
		String value = "";
		if (attributes.containsKey(key))
			value = (String) attributes.get(key);
		return value;
	}

	public void setAttribute(String key, String value) {
		attributes.put(key, value);
	}

	public boolean hasMainAttributesSet() {
		return !getAttribute("auss").equalsIgnoreCase("")
				&& !getAttribute("kl").equalsIgnoreCase("")
				&& !getAttribute("wei").equalsIgnoreCase("")
				&& !getAttribute("tak").equalsIgnoreCase("")
				&& !getAttribute("ausd").equalsIgnoreCase("")
				&& !getAttribute("kk").equalsIgnoreCase("")
				&& !getAttribute("ge").equalsIgnoreCase("");
	}

	/**
	 * create new object from xml
	 *
	 * @param xml
	 * @return
	 * @throws MarshalException
	 * @throws ValidationException
	 */
	public static GameCharacter getFromXml(String xml) throws MarshalException,
			ValidationException {
		GameCharacter temp = new GameCharacter();
		temp.attributes = (HashMap<String, String>) XMLHashMapParser.toMap(xml);
		return temp;
	}

	/**
	 * get xml represenation of this object
	 *
	 * @return
	 * @throws MarshalException
	 * @throws ValidationException
	 */
	public String writeToXml() throws MarshalException, ValidationException {
		return XMLHashMapParser.toXML(attributes);
	}

}
