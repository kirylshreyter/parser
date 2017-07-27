package com.kirylshreyter.parser.model.factory;

import java.util.List;

import com.kirylshreyter.parser.model.JsonValue;

class JsonArray implements JsonValue<List<?>> {

	private List<?> content;

	public JsonArray(List<?> object) {
		this.content = object;
	}

	@Override
	public List<?> getValue() {
		return this.content;
	}

}
