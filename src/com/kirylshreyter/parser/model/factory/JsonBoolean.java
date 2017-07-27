package com.kirylshreyter.parser.model.factory;

import com.kirylshreyter.parser.model.JsonValue;

class JsonBoolean implements JsonValue<Boolean> {

	private boolean value;

	public JsonBoolean(Boolean object) {
		this.value = object;
	}

	@Override
	public Boolean getValue() {
		return this.value;
	}
}
