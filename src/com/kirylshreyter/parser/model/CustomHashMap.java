package com.kirylshreyter.parser.model;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("hiding")
public class CustomHashMap<JsonNameField, JsonValueField> extends HashMap<JsonNameField, JsonValueField>
		implements Map<JsonNameField, JsonValueField> {

	private static final long serialVersionUID = -117730751078877089L;

	@Override
	public JsonValueField get(Object key) {
		for (Map.Entry<JsonNameField, JsonValueField> entry : entrySet()) {
			String entryKeyString = entry.getKey().toString();
			String keyString = key.toString();
			if (entryKeyString.contentEquals(keyString)) {
				return entry.getValue();
			}
		}
		return null;
	}
}
