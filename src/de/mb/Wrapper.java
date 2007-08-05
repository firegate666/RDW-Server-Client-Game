package de.mb;

import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;

import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;

public class Wrapper implements Serializable {
	protected int id;

	protected String name;

	/**
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            Festzulegender id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            Festzulegender name
	 */
	public void setName(String name) {
		this.name = name;
	}

	public void print() {
		System.out.println("Wrapper ID: " + getId() + ", Name: " + getName());
	}

	public static Wrapper getFromXml(String xml) throws MarshalException,
			ValidationException {
		StringReader reader = new StringReader(xml);
		return (Wrapper) Unmarshaller.unmarshal(Wrapper.class, reader);
	}

	public String writeToXml() throws MarshalException, ValidationException {
		StringWriter writer = new StringWriter();

		Marshaller.marshal(this, writer);
		return writer.getBuffer().toString();
	}

}