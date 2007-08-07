package de.mb.rdw.model.items;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

/**
 * this is a basic item
 *
 * @author Marco
 *
 */
public class Item implements Serializable {

	protected static HashMap<Integer, Item> items = new HashMap<Integer, Item>();

	static {
		Item next = new Item();
		next.setDescription("");
		next.setId(1);
		next.setName("Fackel");
		next.setWeight(5.0f);

		items.put(next.getId(), next);
	}

	public static Item getItem(int id) {
		Item item = new Item();
		if (Item.items.containsKey(new Integer(id))) {
			item = Item.items.get(new Integer(id));
		}
		return item;
	}

	public static Item[] getList() {
		Item[] result = new Item[Item.items.size()];
		Iterator i = items.values().iterator();
		int c = 0;
		while(i.hasNext())
			result[c++] = (Item)i.next();
		return result;
	}

	protected int id = 0;
	protected String name = "-";
	protected String description = "-";

	protected float weight = 0f;

	protected int price = 0;

	protected int wf = 0;

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
	public int getId() {
		return id;
	}

	/**
	 * @param id Festzulegender id
	 */
	public void setId(int id) {
		this.id = id;
	}

}
