rmdir /S /Q target
mkdir target
javac -d target -sourcepath src src/com/kirylshreyter/parser/impl/ListParser.java src/com/kirylshreyter/parser/impl/ObjectParser.java src/com/kirylshreyter/parser/validation/JsonValidation.java src/com/kirylshreyter/parser/exceptions/NotValidJsonFileToParseException.java src/com/kirylshreyter/parser/ParserFactory.java
pause