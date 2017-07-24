package com.kirylshreyter.parser.model.factory;

import com.kirylshreyter.parser.model.JsonValueField;

class JsonStringValueField implements JsonValueField {

	private String value;

	public JsonStringValueField(Object object) {
		this.value = object.toString();
	}

	@Override
	public Object getValue() {
		return this.value;
	}

	@Override
	public void setValue(Object object) {
		this.value = object.toString();
	}

}
