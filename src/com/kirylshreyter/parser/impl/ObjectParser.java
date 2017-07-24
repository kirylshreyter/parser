package com.kirylshreyter.parser.impl;

import com.kirylshreyter.parser.Parser;
import com.kirylshreyter.parser.helpers.JsonHelpers;
import com.kirylshreyter.parser.model.JsonObject;
import com.kirylshreyter.parser.utils.JsonUtils;

public class ObjectParser implements Parser {

	@SuppressWarnings("unchecked")
	public JsonObject parse(String inputFile) {
		JsonUtils utils = JsonUtils.getInstance();
		JsonHelpers helpers = new JsonHelpers();
		String fullJsonString = utils.getJsonStringFromFile(inputFile);
		String preparedJsonString = utils.removeLiteralsFromJsonString(fullJsonString);
		helpers.mainHelper(preparedJsonString);
		//String hhh = helpers.findLastObject(preparedJsonString);
	//	System.out.println("HHH " + hhh);
		return null;
	}

}
