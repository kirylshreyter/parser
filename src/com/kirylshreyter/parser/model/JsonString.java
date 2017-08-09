package com.kirylshreyter.parser.model;

/**
 * JsonString Class
 */
public class JsonString implements JsonValue<String> {

	private String value;

	/**
	 * Instantiate {@link JsonString} with {@link String} object
	 * 
	 * @param object
	 *            to with instantiate for
	 */
	public JsonString(String object) {
		this.value = object;
	}

	@Override
	public String getSimpleValue() {
		return this.value;
	}

	@Override
	public String toString() {
		return value;
	}

}
