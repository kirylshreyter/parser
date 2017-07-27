package com.kirylshreyter.parser;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.kirylshreyter.parser.model.JsonObject;
import com.kirylshreyter.parser.model.JsonValue;

public class Main {

	public static void main(String[] args) {
		Parser<?> objectParser = ParserFactory.getParser("e:/java/workspaces/Parser/sample_object.json");
		System.out.println("-------------------------");
		JsonObject jsonObject = (JsonObject) objectParser.parse("e:/java/workspaces/parser/sample_object.json");
		JsonValue<?> valueField = jsonObject.content.get("price");

		System.out.println(">>>>>>>" + valueField.getValue());
		Iterator<Entry<String, JsonValue<?>>> itr = jsonObject.content.entrySet().iterator();
		while (itr.hasNext()) {
			Map.Entry<String, JsonValue<?>> entry = itr.next();
			System.out.println(entry.getKey() + ":" + entry.getValue().getValue());
		}
		System.out.println("-------------------------");

		Parser<?> listParser = ParserFactory.getParser("e:/java/workspaces/Parser/sample_list.json");
		@SuppressWarnings("unchecked")
		List<JsonObject> listJsonObject = (List<JsonObject>) listParser
				.parse("e:/java/workspaces/parser/sample_list.json");
		Iterator<JsonObject> listItr = listJsonObject.iterator();
		System.out.println("-------------------------");
		while (listItr.hasNext()) {
			JsonObject jsonObject2 = listItr.next();
			Iterator<Entry<String, JsonValue<?>>> objItr = jsonObject2.content.entrySet().iterator();
			while (objItr.hasNext()) {
				Map.Entry<String, JsonValue<?>> entry = objItr.next();
				System.out.println(entry.getKey() + ":" + entry.getValue().getValue());
			}
			System.out.println("-------------------------");
		}

	}

}
