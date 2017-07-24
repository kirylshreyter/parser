package com.kirylshreyter.parser.model.factory;

import java.util.List;

import com.kirylshreyter.parser.model.JsonObject;
import com.kirylshreyter.parser.model.JsonValueField;

class JsonListValueField implements JsonValueField {

	private List<JsonObject> value;

	@SuppressWarnings("unchecked")
	public JsonListValueField(Object object) {
		this.value = (List<JsonObject>) object;
	}

	@Override
	public Object getValue() {
		return this.value;
	}

}
