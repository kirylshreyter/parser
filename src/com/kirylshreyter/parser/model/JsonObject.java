package com.kirylshreyter.parser.model;

import java.util.HashMap;

public class JsonObject {

	private HashMap<JsonNameField, JsonValueField> content;

	public JsonObject(JsonNameField nameField, JsonValueField valueField) {
		content = new HashMap<>();
		content.put(nameField, valueField);
	}

	public HashMap<JsonNameField, JsonValueField> getContent() {
		return content;
	}

	public void setContent(HashMap<JsonNameField, JsonValueField> content) {
		this.content = content;
	}

}
