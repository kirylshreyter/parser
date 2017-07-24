package com.kirylshreyter.parser;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.kirylshreyter.parser.model.JsonNameField;
import com.kirylshreyter.parser.model.JsonObject;
import com.kirylshreyter.parser.model.JsonValueField;

public class Main {

	public static void main(String[] args) {
		Parser objectParser = ParserFactory.getParser("e:/java/workspaces/Parser/sample_object.json");
		System.out.println("-------------------------");
		JsonObject jsonObject = objectParser.parse("e:/java/workspaces/parser/sample_object.json");
		JsonValueField valueField = jsonObject.content.get("price");
		System.out.println(">>>>>>>"+valueField);
		Iterator<Entry<JsonNameField, JsonValueField>> itr = jsonObject.content.entrySet().iterator();
		while (itr.hasNext()) {
			Map.Entry<JsonNameField, JsonValueField> entry = itr.next();
			System.out.println(entry.getKey().getName() + ":" + entry.getValue().getValue());
		}
		System.out.println("-------------------------");

		Parser listParser = ParserFactory.getParser("e:/java/workspaces/Parser/sample_list.json");
		List<JsonObject> listJsonObject = listParser.parse("e:/java/workspaces/parser/sample_list.json");
		Iterator<JsonObject> listItr = listJsonObject.iterator();
		System.out.println("-------------------------");
		while (listItr.hasNext()) {
			JsonObject jsonObject2 = listItr.next();
			Iterator<Entry<JsonNameField, JsonValueField>> objItr = jsonObject2.content.entrySet().iterator();
			while (objItr.hasNext()) {
				Map.Entry<JsonNameField, JsonValueField> entry = objItr.next();
				System.out.println(entry.getKey().getName() + ":" + entry.getValue().getValue());
			}
			System.out.println("-------------------------");
		}

	}

}
