package com.kirylshreyter.parser;

public interface Parser<T> {

	T parse(String filePath);

}
