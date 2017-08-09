package com.kirylshreyter.parser.factory;

import com.kirylshreyter.parser.model.JsonBoolean;
import com.kirylshreyter.parser.model.JsonNull;
import com.kirylshreyter.parser.model.JsonNumber;
import com.kirylshreyter.parser.model.JsonString;
import com.kirylshreyter.parser.model.JsonValue;

/**
 * Class defining which {@link JsonValue} to use.
 */
public class JsonValueFactory {

	/**
	 * Method defining which {@link JsonValue} to use based on provided object.
	 * 
	 * @param object
	 *            for define.
	 * @throws IllegalArgumentException if no one {@link JsonValue} does not fill
	 * @return {@link JsonValue}
	 */
	public static JsonValue<?> getJsonValue(Object object) {
		if (object == null) {
			return new JsonNull();
		}
		if (object instanceof java.lang.Number) {
			return new JsonNumber((Number) object);
		}
		if (object instanceof java.lang.String) {
			return new JsonString((String) object);
		}
		if (object instanceof java.lang.Boolean) {
			return new JsonBoolean((Boolean) object);
		}
		throw new IllegalArgumentException("Invalid type");
	}

}
