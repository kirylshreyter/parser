package com.kirylshreyter.parser.helpers;

import java.util.HashMap;
import java.util.LinkedList;

import com.kirylshreyter.parser.model.JsonObject;
import com.kirylshreyter.parser.utils.JsonUtils;

public class JsonHelpers {

	JsonUtils utils = JsonUtils.getInstance();

	/**
	 * Method returns list of json objects represented by strings. If this
	 * method is used for getting single json object, the returned object finded
	 * on 0 index position.
	 */
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

	public HashMap<String, Object> getAllFieldsWithNamesAndValues(String mainJsonObject, HashMap<String, Object> map) {
		int cutIndex = getEndNameFieldIndex(mainJsonObject);
		String nameField = mainJsonObject.substring(getStartNameFieldIndex(), getEndNameFieldIndex(mainJsonObject) - 1);
		mainJsonObject = mainJsonObject.substring(cutIndex);
		nameField = utils.clear(nameField).toString();
		cutIndex = getEndValueFieldIndex(mainJsonObject);
		String valueField = mainJsonObject.substring(getStartValueFieldIndex(mainJsonObject),
				getEndValueFieldIndex(mainJsonObject));
		mainJsonObject = mainJsonObject.substring(cutIndex);
		Object valueFieldObject = utils.clear(valueField);
		map.put(nameField, valueFieldObject);
		if (mainJsonObject.indexOf(",") != -1) {
			getAllFieldsWithNamesAndValues(mainJsonObject, map);
		}
		return map;
	}

	private int getStartNameFieldIndex() {
		return 1;
	}

	private int getEndNameFieldIndex(String string) {
		return string.indexOf(":");
	}

	private int getStartValueFieldIndex(String string) {
		return string.indexOf("\"");
	}

	private int getEndValueFieldIndex(String string) {
		if (string.indexOf(",") != -1) {
			return string.indexOf(",");
		} else {
			return string.length();
		}
	}

	public String findLastObject(String jsonString) {
		int start_index = jsonString.indexOf('{');
		int end_index = 0;
		for (int i = start_index + 1, brace_counter = 1; i < jsonString.length() && brace_counter > 0; i++) {
			if (jsonString.charAt(i) == '{') {
				brace_counter++;
			}
			if (jsonString.charAt(i) == '}') {
				brace_counter--;
			}
			end_index = i + 1;
		}
		String name = "";
		if (jsonString.substring(0, start_index).contains("\"")) {
			String part = jsonString.substring(0, start_index);
			if (part.contains(",")) {
				part = part.substring(part.lastIndexOf(","));
			}
			name = part.substring(part.indexOf('"', 0) + 1, part.indexOf('"', part.indexOf('"', 0) + 1));
		}

		jsonString = jsonString.substring(start_index, end_index);
		jsonString = jsonString.substring(jsonString.indexOf('{') + 1, jsonString.lastIndexOf('}'));
		jsonString = jsonString.substring(jsonString.indexOf('"'));

		if (jsonString.indexOf("{") != -1) {
			jsonString = findLastObject(jsonString);
			return jsonString;
		}
		return name + "$_$" + jsonString;
	}

	public JsonObject mainHelper(String jsonString) {

		String obj = findLastObject(jsonString);
		System.out.println(obj);
		String objName = obj.substring(0, obj.lastIndexOf("$_$"));
		System.out.println(objName);
		String objValue = obj.substring(obj.indexOf("$_$") + 3, obj.length());
		System.out.println(objValue);
		getListOfObjects(obj);

		return null;
	}

	private LinkedList<Object> getListOfObjects(String obj) {
		if (obj.contains("\"")) {
			String name = obj.substring(obj.indexOf("\"") + 1, obj.indexOf("\"", obj.indexOf("\"") + 1));
			System.out.println("NAME: " + name);
			obj = obj.substring(obj.indexOf(":") + 1);
			String value = "";
			if (obj.indexOf("\"") < obj.indexOf(",")) {
				value = obj.substring(obj.indexOf("\"") + 1, obj.indexOf("\"", obj.indexOf("\"") + 1));
				System.out.println("VALUE: " + value);
				obj = obj.substring(obj.indexOf("\"", obj.indexOf("\"") + 1) + 1);
				System.out.println(">>>" + obj);
			}
			

		}
		return null;
	}

}