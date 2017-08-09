package com.kirylshreyter.parser.model;

/**
 * JsonBoolean Class
 */
public class JsonBoolean implements JsonValue<Boolean> {

	private boolean value;

	/**
	 * Instantiate {@link JsonBoolean} with {@link Boolean} object
	 * @param object to with instantiate for
	 */
	public JsonBoolean(Boolean object) {
		this.value = object;
	}

	@Override
	public Boolean getSimpleValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

}
