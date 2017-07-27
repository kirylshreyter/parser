package com.kirylshreyter.parser.impl;

import java.util.List;

import com.kirylshreyter.parser.Parser;
import com.kirylshreyter.parser.helpers.JsonHelpers;
import com.kirylshreyter.parser.model.JsonObject;
import com.kirylshreyter.parser.utils.JsonUtil;
import com.kirylshreyter.parser.validation.JsonValidation;

public class ListParser implements Parser<List<JsonObject>> {

	private JsonUtil utils = new JsonUtil();
	private JsonHelpers helpers = new JsonHelpers();

	public List<JsonObject> parse(String inputFile) {
		String fullJsonString = utils.getJsonStringFromFile(inputFile, "");
		JsonValidation.getInstance().checkIfJsonStringIsValid(fullJsonString);
		List<JsonObject> result = helpers.getJsonObjectList(fullJsonString);
		System.out.println("Objects list was successfully parsed from file.");
		return result;
	}

}
