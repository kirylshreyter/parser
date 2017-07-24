package com.kirylshreyter.parser.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class JsonUtils {
	
	private static long nameFieldCount = 0;
	private static long valueFieldCount = 0;

	private static final JsonUtils INSTANCE = new JsonUtils();

	private JsonUtils() {

	}

	public static JsonUtils getInstance() {
		return INSTANCE;
	}

	public String removeLiteralsFromJsonString(String jsonString) {
		String regex = "[\t\n\r\b\f]+";
		jsonString = jsonString.replaceAll(regex, "");
		return jsonString;
	}

	/**
	 * Method reads file line by line in string.
	 * 
	 * @param sourceFile
	 *            Fullpath to file which needed to parse.
	 */
	public String getJsonStringFromFile(String sourceFile) {
		StringBuilder builder = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(sourceFile))) {
			String tempString;
			while ((tempString = br.readLine()) != null) {
				builder.append(tempString);
			}
		} catch (Exception e) {
			return null;
		}
		return builder.toString();
	}

	public Object clear(String string) {
		string = string.replaceAll("\"", "");
		if (string.contentEquals("true")) {
			return true;
		}
		if (string.contentEquals("false")) {
			return false;
		}
		if (string.contentEquals("null")) {
			return null;
		}
		if (string.matches("\\d+")) {
			string = string.replaceAll("[^0-9]", "");
			return Long.valueOf(string);
		} else if (string.matches("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?")) {
			return Double.parseDouble(string);
		}
		return string;
	}

	public String getResultStringFromHash(HashMap<String, String> map) {
		StringBuilder result = new StringBuilder();
		Iterator<Entry<String, String>> mapIterator = map.entrySet().iterator();
		result.append("{\n");
		Entry<String, String> temp;
		while (mapIterator.hasNext()) {
			temp = mapIterator.next();
			result.append("\t\"");
			result.append(temp.getKey());
			result.append("\":\"");
			result.append(temp.getValue());
			result.append("\"");
			if (mapIterator.hasNext()) {
				result.append(",\n");
			} else {
				result.append("\n}");
			}
		}
		return result.toString();
	}

	public HashMap<String, String> getAllFieldsWithNamesAndValues(Object object)
			throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		HashMap<String, String> map = new HashMap<>();
		Field fields[] = object.getClass().getFields();
		for (Field field : fields) {
			map.put(getFieldName(field, object), getFieldValue(field, object));
		}
		return map;

	}

	private String getFieldName(Field field, Object object) {
		return field.getName();
	}

	private String getFieldValue(Field field, Object object) throws IllegalArgumentException, IllegalAccessException {
		Object a = field.get(object);
		if (a == null)
			a = "null";
		return a.toString();
	}
	
	public String generateNameFieldCount() {
		String result = "$#_N_" + nameFieldCount++;
		return result;
	}

	public String generateValueFieldCount() {
		String result = "$#_V_" + valueFieldCount++;
		return result;
	}

}
