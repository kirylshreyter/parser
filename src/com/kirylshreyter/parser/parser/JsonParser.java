package com.kirylshreyter.parser.parser;

import static com.kirylshreyter.parser.utils.Constants.COLON;
import static com.kirylshreyter.parser.utils.Constants.COMMA;
import static com.kirylshreyter.parser.utils.Constants.DOUBLE_QUOTE;
import static com.kirylshreyter.parser.utils.Constants.DOUBLE_QUOTE_CHAR;
import static com.kirylshreyter.parser.utils.Constants.END_ARRAY;
import static com.kirylshreyter.parser.utils.Constants.END_ARRAY_CHAR;
import static com.kirylshreyter.parser.utils.Constants.END_OBJECT;
import static com.kirylshreyter.parser.utils.Constants.END_OBJECT_CHAR;
import static com.kirylshreyter.parser.utils.Constants.FALSE_STRING;
import static com.kirylshreyter.parser.utils.Constants.NULL_STRING;
import static com.kirylshreyter.parser.utils.Constants.START_ARRAY;
import static com.kirylshreyter.parser.utils.Constants.START_ARRAY_CHAR;
import static com.kirylshreyter.parser.utils.Constants.START_OBJECT;
import static com.kirylshreyter.parser.utils.Constants.START_OBJECT_CHAR;
import static com.kirylshreyter.parser.utils.Constants.TRUE_STRING;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.kirylshreyter.parser.factory.JsonValueFactory;
import com.kirylshreyter.parser.model.Json;
import com.kirylshreyter.parser.model.JsonArray;
import com.kirylshreyter.parser.model.JsonObject;
import com.kirylshreyter.parser.model.JsonValue;
import com.kirylshreyter.parser.utils.JsonUtil;

/**
 * Class for parsing json data.
 */
public class JsonParser {

	private JsonUtil utils = JsonUtil.getInstance();

	/**
	 * Method makes a json object represented by string from json string.
	 * 
	 * @param jsonString
	 *            string of json data.
	 * @return json object represented by string.
	 */
	public String makeJsonObjectAsString(String jsonString) {
		int startIndex = jsonString.indexOf(START_OBJECT_CHAR);
		int endIndex = fetchEndObjectIndex(jsonString, startIndex);
		String result = jsonString.substring(startIndex, endIndex);
		return result.substring(result.indexOf(START_OBJECT_CHAR) + 1, result.lastIndexOf(END_OBJECT_CHAR));
	}

	/**
	 * Method pars json string to {@link JsonObject}.
	 * 
	 * @param mainJsonObject
	 *            string which represents single json object.
	 * @param jsonObject
	 *            {@link JsonObject} to define.
	 * @return {@link JsonObject}
	 */
	public JsonObject makeJsonObject(String mainJsonObject, JsonObject jsonObject) {
		int start_name_field_index = fetchStartNameFieldIndex();
		int end_name_field_index = fetchEndNameFieldIndex(mainJsonObject);
		String nameField = getField(mainJsonObject, start_name_field_index, end_name_field_index);
		mainJsonObject = splitByField(mainJsonObject, end_name_field_index);
		int start_value_field_index = fetchStartValueFieldIndex(mainJsonObject);
		int end_value_field_index = fetchEndValueFieldIndex(mainJsonObject);
		Json valueField = getValue(mainJsonObject, start_value_field_index, end_value_field_index);
		mainJsonObject = splitByValue(mainJsonObject, end_value_field_index);
		jsonObject.content.put(nameField, valueField);
		if (mainJsonObject.indexOf(COMMA) != -1) {
			makeJsonObject(mainJsonObject, jsonObject);
		}
		return jsonObject;
	}

	/**
	 * Method pars json string to {@link List} {@link JsonObject}.
	 * 
	 * @param fullJsonString
	 *            string of json data.
	 * @return {@link List} {@link JsonObject}
	 */
	public JsonArray makeJsonArray(String fullJsonString, JsonArray jsonArray) {
		fullJsonString = removeFirstAndLastChar(fullJsonString, START_ARRAY, END_ARRAY);
		fullJsonString = fullJsonString.trim();
		splitArray(fullJsonString, jsonArray);
		return jsonArray;
	}

	private String splitByValue(String mainJsonObject, int end_value_field_index) {
		mainJsonObject = mainJsonObject.substring(end_value_field_index);
		return mainJsonObject;
	}

	private Json getValue(String mainJsonObject, int start_value_field_index, int end_value_field_index) {
		String valueField = mainJsonObject.substring(start_value_field_index, end_value_field_index);
		Json jsonValueField;
		if (valueField.contains(START_OBJECT) && valueField.contains(END_OBJECT)) {
			valueField = removeFirstAndLastChar(valueField, START_OBJECT, END_OBJECT);
			JsonObject jsonObject = new JsonObject();
			jsonObject = makeJsonObject(valueField, jsonObject);
			jsonValueField = jsonObject;
		} else if (valueField.contains(START_ARRAY) && valueField.contains(END_ARRAY)) {
			if (valueField.contains(START_OBJECT) && valueField.contains(END_OBJECT)) {
				valueField = removeFirstAndLastChar(valueField, START_ARRAY, END_ARRAY);
				JsonArray jsonArray = new JsonArray();
				makeJsonArray(valueField, jsonArray);
				jsonValueField = jsonArray;
			}
			valueField = removeFirstAndLastChar(valueField, START_ARRAY, END_ARRAY);
			JsonArray list = new JsonArray();
			splitJsonArray(valueField, list);
			jsonValueField = list;
		} else {
			jsonValueField = JsonValueFactory.getJsonValue(utils.clear(valueField));
		}
		return jsonValueField;
	}

	private String splitByField(String mainJsonObject, int end_name_field_index) {
		mainJsonObject = mainJsonObject.substring(end_name_field_index + 1);
		return mainJsonObject;
	}

	private String getField(String mainJsonObject, int start_name_field_index, int end_name_field_index) {
		String nameField = utils.clear(mainJsonObject.substring(start_name_field_index, end_name_field_index))
				.toString().trim();
		return nameField;
	}

	private JsonArray splitArray(String fullJsonString, JsonArray array) {
		Character start_char = fullJsonString.charAt(0);
		int start_index = 0;
		if (start_char == START_OBJECT_CHAR) {
			return parseJsonObject(fullJsonString, array, start_index);
		}
		if (start_char == START_ARRAY_CHAR) {
			return parseJsonArray(fullJsonString, array, start_index);
		}
		if (start_char == DOUBLE_QUOTE_CHAR) {
			return parseJsonString(fullJsonString, array, start_index);
		} else if (checkIfSubstringExists(fullJsonString, 0, 4)
				&& fullJsonString.substring(0, 4).contentEquals(TRUE_STRING)) {
			return parseJsonBoolean(fullJsonString, array, start_index, 4);
		} else if (checkIfSubstringExists(fullJsonString, 0, 5)
				&& fullJsonString.substring(0, 5).contentEquals(FALSE_STRING)) {
			return parseJsonBoolean(fullJsonString, array, start_index, 5);
		} else if (checkIfSubstringExists(fullJsonString, 0, NULL_STRING.length())
				&& fullJsonString.substring(0, NULL_STRING.length()).contentEquals(NULL_STRING)) {
			return parseJsonNull(fullJsonString, array, start_index);
		} else if (fullJsonString.isEmpty()) {
			return array;
		} else {
			int end_index;
			if (fullJsonString.indexOf(COMMA, start_index + 1) != -1) {
				end_index = fullJsonString.indexOf(COMMA, start_index + 1);
			} else {
				end_index = fullJsonString.length();
			}
			return parseJsonValue(fullJsonString, array, start_index, end_index);
		}
	}

	private JsonArray parseJsonValue(String fullJsonString, JsonArray array, int start_index, int end_index) {
		String jsonNumberString = fullJsonString.substring(start_index, end_index);
		JsonValue<?> jsonValue = JsonValueFactory.getJsonValue(utils.clear(jsonNumberString));
		array.add(jsonValue);
		checkIfArrayHaveNextValue(fullJsonString, array, end_index);
		return array;
	}

	private JsonArray parseJsonNull(String fullJsonString, JsonArray array, int start_index) {
		int end_index = NULL_STRING.length();
		return parseJsonValue(fullJsonString, array, start_index, end_index);
	}

	private JsonArray parseJsonBoolean(String fullJsonString, JsonArray array, int start_index, int end_index) {
		return parseJsonValue(fullJsonString, array, start_index, end_index);
	}

	private JsonArray parseJsonString(String fullJsonString, JsonArray array, int start_index) {
		int end_index = fullJsonString.indexOf(DOUBLE_QUOTE, start_index + 1);
		String jsonStringObject = fullJsonString.substring(start_index + 1, end_index);
		JsonValue<?> jsonValue = JsonValueFactory.getJsonValue(utils.clear(jsonStringObject));
		array.add(jsonValue);
		checkIfArrayHaveNextValue(fullJsonString, array, end_index);
		return array;
	}

	private JsonArray parseJsonArray(String fullJsonString, JsonArray array, int start_index) {
		int end_index = fetchEndArrayIndex(fullJsonString, start_index);
		String jsonArrayObject = fullJsonString.substring(start_index, end_index);
		JsonArray jsonArray = new JsonArray();
		makeJsonArray(jsonArrayObject, jsonArray);
		array.add(jsonArray);
		checkIfArrayHaveNextValue(fullJsonString, array, end_index);
		return array;
	}

	private JsonArray parseJsonObject(String fullJsonString, JsonArray array, int start_index) {
		int end_index = fetchEndObjectIndex(fullJsonString, start_index);
		String jsonObjectStr = fullJsonString.substring(start_index, end_index);
		JsonObject jsonObject = new JsonObject();
		makeJsonObject(jsonObjectStr, jsonObject);
		array.add(jsonObject);
		checkIfArrayHaveNextValue(fullJsonString, array, end_index);
		return array;
	}

	private boolean checkIfSubstringExists(String stringToCheck, int startSubstringIndex, int endSubstringIndex) {
		try {
			stringToCheck.substring(startSubstringIndex, endSubstringIndex);
			return true;
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
	}

	private void checkIfArrayHaveNextValue(String fullJsonString, JsonArray array, int end_index) {
		if (fullJsonString.indexOf(COMMA, end_index) != -1) {
			fullJsonString = fullJsonString.substring(fullJsonString.indexOf(COMMA, end_index) + 1);
			fullJsonString = fullJsonString.trim();
			splitArray(fullJsonString, array);
		}
	}

	private String removeFirstAndLastChar(String valueField, String firstChar, String lastChar) {
		StringBuilder builder = new StringBuilder(valueField);
		int opBracePos = builder.indexOf(firstChar);
		builder.deleteCharAt(opBracePos);
		int clBracePos = builder.lastIndexOf(lastChar);
		builder.deleteCharAt(clBracePos);
		valueField = builder.toString();
		return valueField;
	}

	private void splitJsonArray(String valueField, JsonArray list) {
		if (valueField.indexOf(COMMA) != -1) {
			String val = valueField.substring(0, valueField.indexOf(COMMA));
			valueField = valueField.substring(valueField.indexOf(COMMA) + 1);
			JsonValue<?> jsonValue = JsonValueFactory.getJsonValue(utils.clear(val));
			list.add(jsonValue);
			splitJsonArray(valueField, list);
		} else {
			String val = valueField.substring(0, valueField.length());
			JsonValue<?> jsonValue = JsonValueFactory.getJsonValue(utils.clear(val));
			list.add(jsonValue);
		}
	}

	private int fetchStartNameFieldIndex() {
		return 1;
	}

	private int fetchEndNameFieldIndex(String string) {
		return string.indexOf(COLON);
	}

	private boolean isAbsent(int index) {
		if (index == -1)
			return true;
		return false;
	}

	private int fetchStartValueFieldIndex(String string) {
		int comma_index = string.indexOf(COMMA);
		int doublequote_index = string.indexOf(DOUBLE_QUOTE);
		int bracket_index = string.indexOf(START_ARRAY);
		int bracer_index = string.indexOf(START_OBJECT);
		if (!isAbsent(comma_index)) {
			String temp = string.substring(0, comma_index);
			if (temp.indexOf(DOUBLE_QUOTE) == -1 && temp.indexOf(START_OBJECT) == -1
					&& temp.indexOf(START_ARRAY) == -1) {
				return 0;
			}
		}
		List<Integer> indexes = new LinkedList<>();
		if (!isAbsent(doublequote_index)) {
			indexes.add(doublequote_index);
		}
		if (!isAbsent(bracket_index)) {
			indexes.add(bracket_index);
		}
		if (!isAbsent(bracer_index)) {
			indexes.add(bracer_index);
		}
		if (!indexes.isEmpty()) {
			return Collections.min(indexes);
		}

		return 0;
	}

	private int fetchEndValueFieldIndex(String string) {
		int character_index = fetchStartValueFieldIndex(string);
		Character character = string.charAt(character_index);
		if (character == START_OBJECT_CHAR) {
			return fetchEndObjectIndex(string, character_index);
		} else if (character == START_ARRAY_CHAR) {
			return fetchEndArrayIndex(string, character_index);
		} else if (character == DOUBLE_QUOTE_CHAR) {
			return string.indexOf(DOUBLE_QUOTE, character_index + 1);
		} else if (checkIfSubstringExists(string, 0, 4) && string.substring(0, 4).contentEquals(TRUE_STRING)) {
			return 4;
		} else if (checkIfSubstringExists(string, 0, 5) && string.substring(0, 5).contentEquals(FALSE_STRING)) {
			return 5;
		} else if (checkIfSubstringExists(string, 0, 4) && string.substring(0, 4).contentEquals(NULL_STRING)) {
			return 4;
		} else {
			int end_index;
			if (string.indexOf(COMMA, character_index + 1) != -1) {
				end_index = string.indexOf(COMMA, character_index + 1);
			} else {
				end_index = string.length();
			}
			return end_index;
		}

	}

	private int fetchEndObjectIndex(String editedJsonString, int startIndex) {
		int end_index = 0;
		for (int index = startIndex + 1, braceCounter = 1; index < editedJsonString.length()
				&& braceCounter > 0; index++) {
			if (START_OBJECT_CHAR.equals(editedJsonString.charAt(index))) {
				braceCounter++;
			}
			if (editedJsonString.charAt(index) == END_OBJECT_CHAR) {
				braceCounter--;
			}
			end_index = index + 1;
		}
		return end_index;
	}

	private int fetchEndArrayIndex(String editedJsonString, int start_index) {
		int end_index = 0;
		for (int i = start_index + 1, brace_counter = 1; i < editedJsonString.length() && brace_counter > 0; i++) {
			if (editedJsonString.charAt(i) == START_ARRAY_CHAR) {
				brace_counter++;
			}
			if (editedJsonString.charAt(i) == END_ARRAY_CHAR) {
				brace_counter--;
			}
			end_index = i + 1;
		}
		return end_index;
	}

}