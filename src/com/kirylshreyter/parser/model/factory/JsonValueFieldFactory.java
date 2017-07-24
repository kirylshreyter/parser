package com.kirylshreyter.parser.model.factory;

import com.kirylshreyter.parser.model.JsonValueField;

public class JsonValueFieldFactory {

	public static JsonValueField getJsonValueField(Object object) {
		if (object == null) {
			return new JsonStringValueField("null");
		} else if (object instanceof java.util.ArrayList<?>) {
			return new JsonListValueField(object);
		} else if (object.getClass().getSuperclass() == Number.class) {
			return new JsonNumberValueField(object);
		} else if (object.getClass() == String.class) {
			return new JsonStringValueField(object);
		} else if (object.getClass() == Boolean.class) {
			return new JsonBooleanValueField(object);
		}else if (object instanceof java.util.Map<?,?>) {
			return new JsonArrayValueField(object);
		}
		return null;
	}

}
