package com.kirylshreyter.parser.model.factory;

import java.util.List;

import com.kirylshreyter.parser.model.JsonValue;

public class JsonValueFactory {

	public static JsonValue<?> getJsonValue(Object object) {
		if (object == null) {
			return new JsonNull();
		} else if (object instanceof java.util.List<?>) {
			return new JsonArray((List<?>) object);
		} else if (object instanceof java.lang.Number) {
			return new JsonNumber((Number) object);
		} else if (object instanceof java.lang.Object) {
			return new JsonString(object);
		} else if (object instanceof java.lang.Boolean) {
			return new JsonBoolean((Boolean) object);
		}
		return null;
	}

}
