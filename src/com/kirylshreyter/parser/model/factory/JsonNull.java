package com.kirylshreyter.parser.model.factory;

import java.util.Iterator;

import com.kirylshreyter.parser.model.JsonValue;

public class JsonNull implements JsonValue<String> {

	@Override
	public String getValue() {
		return "null";
	}

	@Override
	public Iterator<String> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

}
