package com.kirylshreyter.parser.model.factory;

import com.kirylshreyter.parser.model.JsonValue;

public class JsonNull implements JsonValue<String> {

	@Override
	public String getValue() {
		return "null";
	}

}
