package com.kirylshreyter.parser;

import com.kirylshreyter.parser.manager.JsonManager;
import com.kirylshreyter.parser.model.Json;
import com.kirylshreyter.parser.model.JsonArray;

public class Main {

	public static void main(String[] args) {
		Parser parser = new JsonManager();
		System.out.println("-------------------------");
		JsonArray json = parser.parse("e:/java/workspaces/parser/sample_object.json");
		for (Json string : json.getValues()) {
			System.out.println(string);
		}
	}
}
