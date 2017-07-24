package com.kirylshreyter.parser;

public interface Parser {

	<Type> Type parse(String inputFile);

}
