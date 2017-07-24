package com.kirylshreyter.parser.helpers;

import java.util.LinkedList;

import com.kirylshreyter.parser.model.JsonNameField;
import com.kirylshreyter.parser.model.JsonObject;
import com.kirylshreyter.parser.model.JsonValueField;
import com.kirylshreyter.parser.model.factory.JsonValueFieldFactory;
import com.kirylshreyter.parser.utils.JsonUtils;

public class JsonHelpers {

	JsonUtils utils = JsonUtils.getInstance();

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

	public JsonObject getJsonObjectFirstLevel(String mainJsonObject, JsonObject jsonObject) {
		int cutIndex = getEndNameFieldIndex(mainJsonObject);
		String nameField = mainJsonObject.substring(getStartNameFieldIndex(), getEndNameFieldIndex(mainJsonObject) - 1);
		mainJsonObject = mainJsonObject.substring(cutIndex + 1);
		nameField = utils.clear(nameField).toString();
		JsonNameField jsonNameField = new JsonNameField(nameField);
		cutIndex = getEndValueFieldIndex(mainJsonObject);
		String valueField = mainJsonObject.substring(getStartValueFieldIndex(mainJsonObject),
				getEndValueFieldIndex(mainJsonObject));
		JsonValueField jsonValueField;
		if (valueField.contains("{") && valueField.contains("}")) {
			valueField = valueField.replaceAll("\\{", "").replaceAll("\\}", "");
			JsonObject jsonObject2 = new JsonObject();
			jsonValueField = JsonValueFieldFactory.getJsonValueField(getJsonObjectSecondLevel(valueField, jsonObject2));
		} else {
			Object valueFieldObject = utils.clear(valueField);
			jsonValueField = JsonValueFieldFactory.getJsonValueField(valueFieldObject);
		}
		mainJsonObject = mainJsonObject.substring(cutIndex);
		jsonObject.content.put(jsonNameField, jsonValueField);
		if (mainJsonObject.indexOf(",") != -1) {
			getJsonObjectFirstLevel(mainJsonObject, jsonObject);
		}
		return jsonObject;
	}

	private int getStartNameFieldIndex() {
		return 1;
	}

	private int getEndNameFieldIndex(String string) {
		return string.indexOf(":");
	}

	private int getStartValueFieldIndex(String string) {
		if (string.indexOf("\"") < string.indexOf(",")
				&& (string.indexOf("{") == -1 || string.indexOf("{") > string.indexOf("\""))) {
			return string.indexOf("\"");
		} else if (string.indexOf("{") < string.indexOf("\"") && string.indexOf("{") != -1) {
			return string.indexOf("{");
		}
		return 0;
	}

	private int getEndValueFieldIndex(String string) {
		if ((string.indexOf("{") < string.indexOf("\"")) && (string.indexOf("{") < string.indexOf(","))
				&& (string.indexOf("{") != -1)) {
			return string.indexOf("}") + 1;
		}
		if (string.indexOf(",") != -1) {
			return string.indexOf(",");
		} else {
			return string.length();
		}
	}

	public JsonObject getJsonObjectSecondLevel(String mainJsonObject, JsonObject jsonObject) {
		int cutIndex = getEndNameFieldIndex(mainJsonObject);
		String nameField = mainJsonObject.substring(getStartNameFieldIndex(), getEndNameFieldIndex(mainJsonObject) - 1);
		mainJsonObject = mainJsonObject.substring(cutIndex + 1);
		nameField = utils.clear(nameField).toString();
		JsonNameField jsonNameField = new JsonNameField(nameField);
		cutIndex = getEndValueFieldIndex(mainJsonObject);
		String valueField = mainJsonObject.substring(getStartValueFieldIndex(mainJsonObject),
				getEndValueFieldIndex(mainJsonObject));
		JsonValueField jsonValueField;
		mainJsonObject = mainJsonObject.substring(cutIndex);
		Object valueFieldObject = utils.clear(valueField);
		jsonValueField = JsonValueFieldFactory.getJsonValueField(valueFieldObject);
		jsonObject.content.put(jsonNameField, jsonValueField);
		if (mainJsonObject.indexOf(",") != -1) {
			getJsonObjectFirstLevel(mainJsonObject, jsonObject);
		}
		return jsonObject;
	}

}