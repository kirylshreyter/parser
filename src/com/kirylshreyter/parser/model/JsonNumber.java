package com.kirylshreyter.parser.model;

/**
 * JsonNumber Class
 */
public class JsonNumber implements JsonValue<Number> {

	private Number value;

	/**
	 * Instantiate {@link JsonNumber} with {@link Number} object
	 * 
	 * @param object
	 *            to with instantiate for
	 */
	public JsonNumber(Number object) {
		this.value = object;
	}

	@Override
	public Number getSimpleValue() {
		return this.value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

}
