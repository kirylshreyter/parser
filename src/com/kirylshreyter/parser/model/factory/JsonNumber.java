package com.kirylshreyter.parser.model.factory;

import java.util.Iterator;

import com.kirylshreyter.parser.model.JsonValue;

class JsonNumber implements JsonValue<Number> {

	private Number value;

	public JsonNumber(Number object) {
		this.value = object;
	}

	@Override
	public Number getValue() {
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		return this.value;
	}

	@Override
	public Iterator<Number> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
}
