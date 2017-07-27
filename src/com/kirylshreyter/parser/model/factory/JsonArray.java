package com.kirylshreyter.parser.model.factory;

import java.util.Iterator;
import java.util.List;

import com.kirylshreyter.parser.model.JsonValue;

class JsonArray implements JsonValue<List<?>>, Iterable<List<?>> {

	private List<?> content;

	public JsonArray(List<?> object) {
		this.content = object;
	}

	@Override
	public List<?> getValue() {
		return this.content;
	}

	@Override
	public Iterator<List<?>> iterator() {
		return (Iterator<List<?>>) content.iterator();
	}

}
