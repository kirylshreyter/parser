package com.kirylshreyter.parser.model;

import java.util.LinkedList;
import java.util.List;

/**
 * JsonArray Class. </br>
 * Use {@link LinkedList} as data store
 */
public class JsonArray implements Json {
	private List<Json> content;

	/**
	 * Instantiate {@link JsonArray} with {@link LinkedList}
	 */
	public JsonArray() {
		this.content = new LinkedList<Json>();
	}

	/**
	 * Method returns {@link List} of json values
	 * @return {@link List} of elements
	 */
	public List<Json> getValues() {
		return this.content;
	}

	/**
	 * Get {@link Json} element by index from store
	 * 
	 * @param index
	 * @return {@link Json} element
	 * @see LinkedList#get(int)
	 */
	public Json get(int index) {
		return content.get(index);
	}

	/**
	 * Method returns a size of {@link JsonArray}
	 * @return {@link Integer}
	 */
	public int size() {
		return content.size();
	}

	/**
	 * Method adds a {@link Json} to {@link JsonArray}
	 * @param json to add
	 */
	public void add(Json json) {
		content.add(json);
	}
}
