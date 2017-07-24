package com.kirylshreyter.parser.model.factory;

import java.util.HashMap;

import com.kirylshreyter.parser.model.JsonNameField;
import com.kirylshreyter.parser.model.JsonValueField;

class JsonArrayValueField implements JsonValueField {

	private HashMap<JsonNameField, JsonValueField> content;

	@SuppressWarnings("unchecked")
	public JsonArrayValueField(Object object) {
		this.content = (HashMap<JsonNameField, JsonValueField>) object;
	}

	@Override
	public Object getValue() {
		return this.content;
	}

}
