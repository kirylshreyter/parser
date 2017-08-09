package com.kirylshreyter.parser.utils;

import static com.kirylshreyter.parser.utils.Constants.BACKSPACE;
import static com.kirylshreyter.parser.utils.Constants.CARRIAGE_RETURN;
import static com.kirylshreyter.parser.utils.Constants.COMMA;
import static com.kirylshreyter.parser.utils.Constants.DOUBLE_QUOTE;
import static com.kirylshreyter.parser.utils.Constants.EMPTY;
import static com.kirylshreyter.parser.utils.Constants.END_ARRAY;
import static com.kirylshreyter.parser.utils.Constants.FALSE_STRING;
import static com.kirylshreyter.parser.utils.Constants.FORMFEED;
import static com.kirylshreyter.parser.utils.Constants.HORIZONTAL_TAB;
import static com.kirylshreyter.parser.utils.Constants.NEWLINE;
import static com.kirylshreyter.parser.utils.Constants.NULL_STRING;
import static com.kirylshreyter.parser.utils.Constants.PLUS;
import static com.kirylshreyter.parser.utils.Constants.START_ARRAY;
import static com.kirylshreyter.parser.utils.Constants.TRUE_STRING;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Class provide utils for json string treatment.
 */
public class JsonUtil {

	private static final String DELETED_SYMBOLS = START_ARRAY + HORIZONTAL_TAB + NEWLINE + CARRIAGE_RETURN + BACKSPACE
			+ FORMFEED + END_ARRAY + PLUS;
	private static final String _0_9 = "[^0-9]";
	private static final String _0_9_0_9_E_E_0_9 = "[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?";
	private static final String D = "\\d+";
	private final static JsonUtil INSTANCE = new JsonUtil();

	private JsonUtil() {

	}

	/**
	 * Method returns {@link JsonUtil} singleton
	 * 
	 * @return {@link #INSTANCE}
	 */
	public static JsonUtil getInstance() {
		return INSTANCE;
	}

	/**
	 * Method remove all literals from json string.
	 * 
	 * @param value
	 *            string for processing.
	 * @return json string without literals.
	 */
	public String removeLiterals(String value) {
		return value.replaceAll(DELETED_SYMBOLS, EMPTY);
	}

	/**
	 * Method reads file line by line in string.
	 * 
	 * @param filePath
	 *            Full path to file which needs to parse.
	 * @param finishValue
	 *            if this parameter is specified, then result string would
	 *            contents line from file with specified char. If untilChar is
	 *            empty(""), then result string would content all file's lines.
	 */
	public String readLines(String filePath, String finishValue) {
		boolean check = finishValue != EMPTY;
		StringBuilder builder = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (check && line.contains(finishValue)) {
					builder.append(line);
					break;
				}
				builder.append(line);
			}
		} catch (Exception e) {
			return null;
		}
		return builder.toString();
	}

	/**
	 * Method removes all spaces, commas, double quotes from string and
	 * determines which type of objects is this string.
	 * 
	 * @param value
	 *            to be cleared.
	 * @return object of defined class.
	 */
	public Object clear(String value) {
		value = value.trim();
		value = value.replaceAll(COMMA, EMPTY);
		value = value.replaceAll(DOUBLE_QUOTE, EMPTY);
		if (value.contentEquals(TRUE_STRING)) {
			return true;
		}
		if (value.contentEquals(FALSE_STRING)) {
			return false;
		}
		if (value.contentEquals(NULL_STRING)) {
			return null;
		}
		String long_regex = D;
		String double_regex = _0_9_0_9_E_E_0_9;
		if (value.matches(long_regex)) {
			value = value.replaceAll(_0_9, EMPTY);
			return Long.valueOf(value);
		}
		if (value.matches(double_regex)) {
			return Double.parseDouble(value);
		}
		return value;
	}

}
