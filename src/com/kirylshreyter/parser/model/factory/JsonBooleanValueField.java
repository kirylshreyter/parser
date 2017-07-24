package com.kirylshreyter.parser.model.factory;

import com.kirylshreyter.parser.model.JsonValueField;

class JsonBooleanValueField implements JsonValueField {

	private Boolean value;

	public JsonBooleanValueField(Object object) {
		this.value = (Boolean) object;
	}

	@Override
	public Object getValue() {
		return this.value;
	}

	@Override
	public void setValue(Object object) {
		this.value = (Boolean) object;
	}

}
