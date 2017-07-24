package com.kirylshreyter.parser.model;

public class JsonObject {

	public CustomHashMap<JsonNameField, JsonValueField> content;

	public JsonObject() {
		this.content = new CustomHashMap<>();
	}

	public JsonObject(JsonNameField nameField, JsonValueField valueField) {
		this.content = new CustomHashMap<>();
		this.content.put(nameField, valueField);
	}

}
