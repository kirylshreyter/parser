package com.kirylshreyter.parser.model.factory;

import com.kirylshreyter.parser.model.JsonValue;

class JsonNumber implements JsonValue<Number> {

	private Number value;

	public JsonNumber(Number object) {
		this.value = object;
	}

	@Override
	public Number getValue() {
		return this.value;
	}
}
