package com.kirylshreyter.parser.model;

import com.kirylshreyter.parser.utils.Constants;

/**
 * JsonNull Class
 */
public class JsonNull implements JsonValue<JsonNull> {

	@Override
	public JsonNull getSimpleValue() {
		return null;
	}

	@Override
	public String toString() {
		return Constants.NULL_STRING;
	}

}
