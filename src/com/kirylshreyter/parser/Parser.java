package com.kirylshreyter.parser;

import com.kirylshreyter.parser.model.Json;
import com.kirylshreyter.parser.model.JsonArray;

/**
 * An parser for parsing list or single json object.
 */
public interface Parser {

	/**
	 * Method pars file to {@link Json}.
	 * 
	 * @param filePath
	 *            full path to file with json data.
	 */
	JsonArray parse(String filePath);

}
