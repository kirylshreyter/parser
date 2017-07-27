package com.kirylshreyter.parser.helpers;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.kirylshreyter.parser.model.JsonObject;
import com.kirylshreyter.parser.model.JsonValue;
import com.kirylshreyter.parser.model.factory.JsonValueFactory;
import com.kirylshreyter.parser.utils.JsonUtil;

public class JsonHelpers {

	JsonUtil utils = new JsonUtil();

	public LinkedList<String> getMainJsonObject(String editedJsonString, LinkedList<String> objectsList) {
		int start_index = editedJsonString.indexOf('{');
		int end_index = 0;
		for (int i = start_index + 1, brace_counter = 1; i < editedJsonString.length() && brace_counter > 0; i++) {
			if (editedJsonString.charAt(i) == '{') {
				brace_counter++;
			}
			if (editedJsonString.charAt(i) == '}') {
				brace_counter--;
			}
			end_index = i + 1;
		}
		String jsonObject = editedJsonString.substring(start_index, end_index);
		jsonObject = jsonObject.substring(jsonObject.indexOf('{') + 1, jsonObject.lastIndexOf('}'));
		objectsList.add(jsonObject);
		editedJsonString = editedJsonString.substring(end_index);
		if (editedJsonString.indexOf("{") != -1) {
			getMainJsonObject(editedJsonString, objectsList);
		}
		return objectsList;
	}

	public JsonObject getJsonObject(String mainJsonObject, JsonObject jsonObject) {
		int cutIndex = getEndNameFieldIndex(mainJsonObject);
		String nameField = mainJsonObject.substring(getStartNameFieldIndex(), getEndNameFieldIndex(mainJsonObject) - 1);
		mainJsonObject = mainJsonObject.substring(cutIndex + 1);
		nameField = utils.clear(nameField).toString();
		String jsonNameField = nameField;
		cutIndex = getEndValueFieldIndex(mainJsonObject);
		String valueField = mainJsonObject.substring(getStartValueFieldIndex(mainJsonObject),
				getEndValueFieldIndex(mainJsonObject));
		JsonValue<?> jsonValueField;
		if (valueField.contains("{") && valueField.contains("}")) {
			valueField = removeFirstAndLastCharOfPresented(valueField, "{", "}");
			JsonObject jsonObject2 = new JsonObject();
			jsonValueField = JsonValueFactory.getJsonValue(getJsonObject(valueField, jsonObject2));
		} else if (valueField.contains("[") && valueField.contains("]")) {
			if (valueField.contains("{") && valueField.contains("}")) {
				valueField = removeFirstAndLastCharOfPresented(valueField, "[", "]");
				jsonValueField = JsonValueFactory.getJsonValue(getJsonObjectList(valueField));
			}
			valueField = removeFirstAndLastCharOfPresented(valueField, "[", "]");
			List<JsonValue<?>> list = new LinkedList<>();
			splitJsonArray(valueField, list);
			jsonValueField = JsonValueFactory.getJsonValue(list);
		} else {
			Object valueFieldObject = utils.clear(valueField);
			jsonValueField = JsonValueFactory.getJsonValue(valueFieldObject);
		}
		mainJsonObject = mainJsonObject.substring(cutIndex);
		jsonObject.content.put(jsonNameField, jsonValueField);
		if (mainJsonObject.indexOf(",") != -1) {
			getJsonObject(mainJsonObject, jsonObject);
		}
		return jsonObject;
	}

	private String removeFirstAndLastCharOfPresented(String valueField, String firstChar, String lastChar) {
		StringBuilder builder = new StringBuilder(valueField);
		int opBracePos = builder.indexOf(firstChar);
		builder.deleteCharAt(opBracePos);
		int clBracePos = builder.lastIndexOf(lastChar);
		builder.deleteCharAt(clBracePos);
		valueField = builder.toString();
		return valueField;
	}

	private void splitJsonArray(String valueField, List<JsonValue<?>> list) {
		if (valueField.indexOf(",") != -1) {
			String val = valueField.substring(0, valueField.indexOf(","));
			valueField = valueField.substring(valueField.indexOf(",") + 1);
			JsonValue<?> jsonValue = JsonValueFactory.getJsonValue(utils.clear(val));
			list.add(jsonValue);
			splitJsonArray(valueField, list);
		} else {
			String val = valueField.substring(0, valueField.length());
			JsonValue<?> jsonValue = JsonValueFactory.getJsonValue(utils.clear(val));
			list.add(jsonValue);
		}
	}

	private int getStartNameFieldIndex() {
		return 1;
	}

	private int getEndNameFieldIndex(String string) {
		return string.indexOf(":");
	}

	private boolean isAbsent(int index) {
		if (index == -1)
			return true;
		return false;
	}

	private int getStartValueFieldIndex(String string) {
		int comma_index = string.indexOf(",");
		int doublequote_index = string.indexOf("\"");
		int bracket_index = string.indexOf("[");
		int bracer_index = string.indexOf("{");
		if (!isAbsent(comma_index)) {
			String temp = string.substring(0, comma_index);
			if (temp.indexOf("\"") == -1 && temp.indexOf("{") == -1 && temp.indexOf("[") == -1) {
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

	private int getEndValueFieldIndex(String string) {

		if ((string.indexOf("{") < string.indexOf("\"")) && (string.indexOf("{") < string.indexOf(","))
				&& (string.indexOf("{") != -1)) {
			return string.indexOf("}") + 1;
		}
		if ((string.indexOf("[") < string.indexOf("\"")) && (string.indexOf("[") < string.indexOf(","))
				&& (string.indexOf("[") < string.indexOf("{")) && (string.indexOf("[") != -1)) {
			return string.indexOf("]") + 1;
		}
		if (string.indexOf(",") != -1) {
			return string.indexOf(",");
		} else {
			return string.length();
		}
	}

	public List<JsonObject> getJsonObjectList(String fullJsonString) {
		String preparedJsonString = utils.removeLiteralsFromJsonString(fullJsonString);
		LinkedList<String> strings = new LinkedList<>();
		strings = getMainJsonObject(preparedJsonString, strings);
		List<JsonObject> result = new LinkedList<>();
		for (String string : strings) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.content = new HashMap<>();
			getJsonObject(string, jsonObject);
			result.add(jsonObject);
		}
		return result;
	}

}