package com.kirylshreyter.parser.impl;

import java.util.LinkedList;

import com.kirylshreyter.parser.Parser;
import com.kirylshreyter.parser.helpers.JsonHelpers;
import com.kirylshreyter.parser.model.CustomHashMap;
import com.kirylshreyter.parser.model.JsonNameField;
import com.kirylshreyter.parser.model.JsonObject;
import com.kirylshreyter.parser.model.JsonValueField;
import com.kirylshreyter.parser.utils.JsonUtils;

public class ObjectParser implements Parser {

	private JsonUtils utils = JsonUtils.getInstance();
	private JsonHelpers helpers = new JsonHelpers();

	@SuppressWarnings("unchecked")
	public JsonObject parse(String inputFile) {
		String fullJsonString = utils.getJsonStringFromFile(inputFile);
		String preparedJsonString = utils.removeLiteralsFromJsonString(fullJsonString);
		LinkedList<String> strings = new LinkedList<>();
		strings = helpers.getMainJsonObject(preparedJsonString, strings);
		JsonObject jsonObject = new JsonObject();
		jsonObject.content = new CustomHashMap<JsonNameField, JsonValueField>();
		for (String string : strings) {
			helpers.getJsonObjectFirstLevel(string, jsonObject);
		}
		System.out.println("Object was successfully parsed from file.");
		return jsonObject;
	}

}
