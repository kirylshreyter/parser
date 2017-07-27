package com.kirylshreyter.parser.impl;

import java.util.HashMap;
import java.util.LinkedList;

import com.kirylshreyter.parser.Parser;
import com.kirylshreyter.parser.helpers.JsonHelpers;
import com.kirylshreyter.parser.model.JsonObject;
import com.kirylshreyter.parser.utils.JsonUtil;
import com.kirylshreyter.parser.validation.JsonValidation;

public class ObjectParser implements Parser<JsonObject> {

	private JsonUtil jsonUtil = new JsonUtil();
	private JsonHelpers helpers = new JsonHelpers();

	public JsonObject parse(String inputFile) {
		String fullJsonString = jsonUtil.getJsonStringFromFile(inputFile, "");
		JsonValidation.getInstance().checkIfJsonStringIsValid(fullJsonString);
		String preparedJsonString = jsonUtil.removeLiteralsFromJsonString(fullJsonString);
		LinkedList<String> strings = new LinkedList<>();
		strings = helpers.getMainJsonObject(preparedJsonString, strings);
		JsonObject jsonObject = new JsonObject();
		jsonObject.content = new HashMap<>();
		for (String string : strings) {
			helpers.getJsonObjectFirstLevel(string, jsonObject);
		}
		System.out.println("Object was successfully parsed from file.");
		return jsonObject;
	}

}
