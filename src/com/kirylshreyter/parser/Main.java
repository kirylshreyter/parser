package com.kirylshreyter.parser;

public class Main {

	public static void main(String[] args) {

		Parser parser = ParserFactory.getParser("e:/java/workspaces/Parser/sample.json");
		parser.parse("e:/java/workspaces/parser/sample.json");

	}

}
