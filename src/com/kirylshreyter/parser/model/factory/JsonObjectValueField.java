package com.kirylshreyter.parser.model.factory;

import com.kirylshreyter.parser.model.JsonObject;
import com.kirylshreyter.parser.model.JsonValueField;

class JsonObjectValueField implements JsonValueField {

	private JsonObject value;

	public JsonObjectValueField(Object object) {
		this.value = (JsonObject) object;
	}

	@Override
	public Object getValue() {
		return this.value;
	}

}
