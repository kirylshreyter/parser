package com.kirylshreyter.parser.model;

public interface JsonValue<T> extends Iterable<T> {

	T getValue();

}
