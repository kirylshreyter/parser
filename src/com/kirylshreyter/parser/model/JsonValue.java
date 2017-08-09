package com.kirylshreyter.parser.model;

/**
 * An simple json value which can be a {@link JsonBoolean}, {@link JsonNull},
 * {@link JsonNumber} or {@link JsonString}.
 */
public interface JsonValue<T> extends Json {

	/**
	 * Method gets simple {@link JsonValue}
	 */
	T getSimpleValue();
}
