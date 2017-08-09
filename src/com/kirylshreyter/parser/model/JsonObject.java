package com.kirylshreyter.parser.model;

import static java.util.Collections.unmodifiableCollection;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link JsonObject} {@link Class}
 */
public class JsonObject implements Json {

	/**
	 * Map which contains {@link String} as keys and {@link JsonValue} as
	 * values.
	 */
	public Map<String, Json> content;

	/**
	 * Instantiate {@link JsonObject} with {@link HashMap}.
	 */
	public JsonObject() {
		this.content = new HashMap<String, Json>();
	}

	/**
	 * Method compose {@link List} of keys from {@link #content}.
	 * 
	 * @return null if content is empty or {@link List} keys of {@link #content}
	 *         if not.
	 */
	public Collection<String> getFields() {
		return unmodifiableCollection(content.keySet());
	}

	/**
	 * Method compose {@link List} of values from {@link #content}.
	 * 
	 * @return null if content is empty or {@link List} values of
	 *         {@link #content} if not.
	 */
	public Collection<Json> getValues() {
		return unmodifiableCollection(content.values());
	}

	/**
	 * Method gets value from json object by key. If key not exist method
	 * returns null.
	 * 
	 * @param field
	 *            to find
	 */
	public Json getValue(String field) {
		return content.get(field);
	}

}
