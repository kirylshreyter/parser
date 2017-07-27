package com.kirylshreyter.parser;

import com.kirylshreyter.parser.impl.ListParser;
import com.kirylshreyter.parser.impl.ObjectParser;
import com.kirylshreyter.parser.utils.JsonUtil;

public class ParserFactory {

	public static Parser<?> getParser(String inputFile) {
		JsonUtil util = new JsonUtil();
		String fullJsonString = util.getJsonStringFromFile(inputFile, "{");
		if ((fullJsonString.indexOf("[") < fullJsonString.indexOf("{")) && fullJsonString.indexOf("[") != -1) {
			return new ListParser();
		}
		return new ObjectParser();
	}

}
