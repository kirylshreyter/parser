package com.kirylshreyter.parser.model.factory;

import com.kirylshreyter.parser.model.JsonValue;

class JsonString implements JsonValue<String> {

	private String value;

	public JsonString(Object object) {
		this.value = object.toString();
	}

	@Override
	public String getValue() {
		return this.value;
	}
}
