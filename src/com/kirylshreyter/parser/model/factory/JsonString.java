package com.kirylshreyter.parser.model.factory;

import java.util.Iterator;

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

	@Override
	public Iterator<String> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
}
