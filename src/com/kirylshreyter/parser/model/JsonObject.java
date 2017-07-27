package com.kirylshreyter.parser.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class JsonObject {

	public Map<String, JsonValue<?>> content;

	public JsonObject() {
		this.content = new HashMap<String, JsonValue<?>>();
	}

	public JsonObject(String nameField, JsonValue<?> valueField) {
		this.content = new HashMap<String, JsonValue<?>>();
		this.content.put(nameField, valueField);
	}

	List<String> getFileds() {
		if (isEmpty()) {
			return null;
		}
		List<String> resultList = new LinkedList<>();
		for (String key : this.content.keySet()) {
			resultList.add(key);
		}
		return resultList;
	}

	List<JsonValue<?>> getValues() {
		if (isEmpty()) {
			return null;
		}
		List<JsonValue<?>> resultList = new LinkedList<>();
		for (JsonValue<?> value : this.content.values()) {
			resultList.add(value);
		}
		return resultList;
	}

	private boolean isEmpty() {
		if (this.content.isEmpty())
			return true;
		return false;
	}

}
