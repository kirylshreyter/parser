package com.kirylshreyter.parser.impl;

import java.util.LinkedList;
import java.util.List;

import com.kirylshreyter.parser.Parser;
import com.kirylshreyter.parser.helpers.JsonHelpers;
import com.kirylshreyter.parser.model.CustomHashMap;
import com.kirylshreyter.parser.model.JsonNameField;
import com.kirylshreyter.parser.model.JsonObject;
import com.kirylshreyter.parser.model.JsonValueField;
import com.kirylshreyter.parser.utils.JsonUtils;

public class ListParser implements Parser {

	private JsonUtils utils = JsonUtils.getInstance();
	private JsonHelpers helpers = new JsonHelpers();

	@SuppressWarnings("unchecked")
	public List<JsonObject> parse(String inputFile) {
		String fullJsonString = utils.getJsonStringFromFile(inputFile);
		String preparedJsonString = utils.removeLiteralsFromJsonString(fullJsonString);
		LinkedList<String> strings = new LinkedList<>();
		strings = helpers.getMainJsonObject(preparedJsonString, strings);
		List<JsonObject> result = new LinkedList<>();
		for (String string : strings) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.content = new CustomHashMap<JsonNameField, JsonValueField>();
			helpers.getJsonObjectFirstLevel(string, jsonObject);
			result.add(jsonObject);
		}
		System.out.println("Objects list was successfully parsed from file.");
		return result;
	}

}
