package de.mb.rdw.model.items;

import java.io.Serializable;

/**
 * this is a basic item
 * 
 * @author Marco
 *
 */
public class Item implements Serializable {

	protected String id;
	protected String name;
	protected String description;

	protected float weight;

	protected int price;

	protected int wf;

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name Festzulegender name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * @param price Festzulegender price
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * @return weight
	 */
	public float getWeight() {
		return weight;
	}

	/**
	 * @param weight Festzulegender weight
	 */
	public void setWeight(float weight) {
		this.weight = weight;
	}

	/**
	 * @return wf
	 */
	public int getWf() {
		return wf;
	}

	/**
	 * @param wf Festzulegender wf
	 */
	public void setWf(int wf) {
		this.wf = wf;
	}

	/**
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description Festzulegender description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id Festzulegender id
	 */
	public void setId(String id) {
		this.id = id;
	}

}
