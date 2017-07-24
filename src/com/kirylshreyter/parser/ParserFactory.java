package com.kirylshreyter.parser;

import com.kirylshreyter.parser.impl.ListParser;
import com.kirylshreyter.parser.impl.ObjectParser;
import com.kirylshreyter.parser.utils.JsonUtils;
import com.kirylshreyter.parser.validation.JsonValidation;

public class ParserFactory {

	@SuppressWarnings("unchecked")
	public static <Type> Type getParser(String inputFile) {
		String fullJsonString = JsonUtils.getInstance().getJsonStringFromFile(inputFile);
		JsonValidation.getInstance().checkIfJsonStringIsValid(fullJsonString);
		if (fullJsonString.indexOf("[") < fullJsonString.indexOf("{")
				&& fullJsonString.lastIndexOf("]") > fullJsonString.lastIndexOf("}")) {
			return (Type) new ListParser();
		} else {
			return (Type) new ObjectParser();
		}
	}

}
