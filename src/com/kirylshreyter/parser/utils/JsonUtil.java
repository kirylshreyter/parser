package com.kirylshreyter.parser.utils;

import java.io.BufferedReader;
import java.io.FileReader;

public class JsonUtil {

	public String removeLiteralsFromJsonString(String jsonString) {
		String regex = "[\t\n\r\b\f]+";
		jsonString = jsonString.replaceAll(regex, "");
		return jsonString;
	}

	/**
	 * Method reads file line by line in string.
	 * 
	 * @param filePath
	 *            Fullpath to file which needs to parse.
	 * @param untilChar
	 *            if this parameter is specified, then result string would
	 *            contents line from file with specified char. If untilChar
	 *            is empty(""), then result string would content all file's
	 *            lines.
	 */
	public String getJsonStringFromFile(String filePath, String untilChar) {
		StringBuilder builder = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String tempString;
			while ((tempString = br.readLine()) != null) {
				if (untilChar!="" && tempString.contains(untilChar)) {
					builder.append(tempString);
					return builder.toString();
				}
				builder.append(tempString);
			}
		} catch (Exception e) {
			return null;
		}
		return builder.toString();
	}

	public Object clear(String string) {
		string = string.trim();
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

}
