package com.kirylshreyter.parser.manager;

import static com.kirylshreyter.parser.utils.Constants.EMPTY;
import static com.kirylshreyter.parser.utils.Constants.START_ARRAY;
import static com.kirylshreyter.parser.utils.Constants.START_OBJECT;

import com.kirylshreyter.parser.Parser;
import com.kirylshreyter.parser.model.JsonArray;
import com.kirylshreyter.parser.model.JsonObject;
import com.kirylshreyter.parser.parser.JsonParser;
import com.kirylshreyter.parser.utils.JsonUtil;

/**
 * Class for parsing json data which is represented by array of json objects.
 */
public class JsonManager implements Parser {

	private JsonUtil utils = JsonUtil.getInstance();
	private JsonParser parser = new JsonParser();

	@Override
	public JsonArray parse(String fileName) {
		JsonArray result = new JsonArray();
		String fileData = utils.readLines(fileName, EMPTY);
		fileData = utils.removeLiterals(fileData);
		int indexOfArray = fileData.indexOf(START_ARRAY);
		int indexOfObject = fileData.indexOf(START_OBJECT);
		if (indexOfArray == -1 || indexOfObject != -1 && indexOfObject < indexOfArray) {
			JsonObject jsonObject = new JsonObject();
			result.add(parser.makeJsonObject(parser.makeJsonObjectAsString(fileData), jsonObject));
			System.out.println("Parsing was successfully finished.");
			return result;
		}
		parser.makeJsonArray(fileData, result);
		System.out.println("Parsing was successfully finished.");
		return result;

	}
}
