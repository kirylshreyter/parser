package com.kirylshreyter.parser.model.factory;

import com.kirylshreyter.parser.model.JsonValueField;

class JsonNumberValueField implements JsonValueField {
	
	private Number value;
	
	public JsonNumberValueField(Object object) {
		this.value = (Number) object;
	}

	@Override
	public Object getValue() {
		return this.value;
	}

}
