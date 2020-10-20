package vulc.vdf.io;

import static vulc.vdf.io.TextTokens.*;
import static vulc.vdf.io.VDFCodes.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import vulc.vdf.ObjectElement;

public class TextReader {

	private final TextDeserializer[] deserializers = new TextDeserializer[VDFCodes.TYPES];

	protected TextReader() {
		char[] endOfValue = new char[] {
		    CLOSE_OBJECT, SEPARATOR,
		    WHITESPACE, TAB,
		    CR, LF
		};

		add((obj, name, in) -> obj.setBoolean(name, Boolean.valueOf(in.readUntil(endOfValue))), BOOLEAN);
		add((obj, name, in) -> obj.setByte(name, Byte.valueOf(in.readUntil(endOfValue))), BYTE);
		add((obj, name, in) -> obj.setShort(name, Short.valueOf(in.readUntil(endOfValue))), SHORT);
		add((obj, name, in) -> obj.setInt(name, Integer.valueOf(in.readUntil(endOfValue))), INT);
		add((obj, name, in) -> obj.setLong(name, Long.valueOf(in.readUntil(endOfValue))), LONG);
		add((obj, name, in) -> obj.setFloat(name, Float.valueOf(in.readUntil(endOfValue))), FLOAT);
		add((obj, name, in) -> obj.setDouble(name, Double.valueOf(in.readUntil(endOfValue))), DOUBLE);

		// TODO unescape
//		add((obj, name, in) -> obj.setChar(name, Character.valueOf(in.readUntil(endOfValue))), CHAR);
//		add((obj, name, in) -> obj.setString(name, in.readUntil(endOfValue)), STRING);
//		add((obj, name, in) -> obj.setObject(/*...*/), OBJECT);

		ArrayElementReader defaultReader = (in) -> in.readUntil(endOfValue);

		add(getArrayReader(boolean[].class,
		                   defaultReader,
		                   (array, i, value) -> array[i] = Boolean.valueOf(value),
		                   (obj, name, array) -> obj.setBooleanArray(name, array)),
		    BOOLEAN_A);

		add(getArrayReader(byte[].class,
		                   defaultReader,
		                   (array, i, value) -> array[i] = Byte.valueOf(value),
		                   (obj, name, array) -> obj.setByteArray(name, array)),
		    BYTE_A);

		add(getArrayReader(short[].class,
		                   defaultReader,
		                   (array, i, value) -> array[i] = Byte.valueOf(value),
		                   (obj, name, array) -> obj.setShortArray(name, array)),
		    SHORT_A);

		// TODO it's boring
	}

	private void add(TextDeserializer deserializer, byte code) {
		deserializers[code] = deserializer;
	}

	protected ObjectElement deserializeObject(String inStr, ObjectElement obj) throws TextualVDFSyntaxException {
		StringAnalyzer in = new StringAnalyzer(inStr);

		in.checkToken(OPEN_OBJECT);
		while(true) {
			in.skipWhitespaces();
			if(in.next() == CLOSE_OBJECT) {
				in.read();
				break;
			}
			if(in.next() == SEPARATOR) {
				in.read();
				continue;
			}

			String type = in.readUntil(WHITESPACE, STRING_QUOTE).toLowerCase(); // TODO test performance: tolowercase
			in.checkToken(STRING_QUOTE);
			String name = in.readUntil(STRING_QUOTE);
			in.read();
			in.checkToken(ASSIGN);
			in.skipWhitespaces();

			Byte code = TextCodes.TAG_CODES.get(type);
			if(code == null) throw new TextualVDFSyntaxException("type '" + type + "' does not exist");
			deserializers[code].deserialize(obj, name, in);

			in.skipWhitespaces();

			char token = in.read();
			if(token == CLOSE_OBJECT) break;
			if(token != SEPARATOR) in.missingToken(SEPARATOR);
		}
		return obj;
	}

	private <T> TextDeserializer getArrayReader(Class<T> type,
	                                            ArrayElementReader elementReader,
	                                            ArrayElementAdder<T> elementAdder,
	                                            ArrayAdder<T> arrayAdder) {
		return (obj, name, in) -> {
			in.checkToken(OPEN_ARRAY);

			List<String> stringValues = new ArrayList<String>();
			while(true) {
				in.skipWhitespaces();
				if(in.next() == CLOSE_ARRAY) {
					in.read();
					break;
				}
				if(in.next() == SEPARATOR) {
					in.read();
					continue;
				}

				stringValues.add(elementReader.read(in));
				in.skipWhitespaces();

				char token = in.read();
				if(token == CLOSE_ARRAY) break;
				if(token != SEPARATOR) in.missingToken(SEPARATOR);
			}

			int length = stringValues.size();

			T array = type.cast(Array.newInstance(type.getComponentType(), length));
			for(int i = 0; i < length; i++) {
				elementAdder.add(array, i, stringValues.get(i));
			}
			arrayAdder.add(obj, name, array);
		};
	}

	private static class StringAnalyzer {

		private final String string;

		private int mark = 0;

		private StringAnalyzer(String string) {
			this.string = string;
		}

		private char read() throws TextualVDFSyntaxException {
			if(mark >= string.length()) throw new TextualVDFSyntaxException("End of string");
			return string.charAt(mark++);
		}

		private char next() throws TextualVDFSyntaxException {
			char c = read();
			mark--;
			return c;
		}

		private String readUntil(char... until) throws TextualVDFSyntaxException {
			StringBuilder result = new StringBuilder();

			boolean hasRead = false;
			read_for:
			for(char c = read(); true; c = read()) {
				hasRead = true;

				for(char u : until) {
					if(u == c) break read_for;
				}

				result.append(c);
			}
			if(hasRead) mark--;

			return result.toString();
		}

		private void skipWhitespaces() throws TextualVDFSyntaxException {
			boolean hasRead = false;
			for(char c = read(); true; c = read()) {
				hasRead = true;

				if(c != WHITESPACE && c != TAB
				   && c != CR && c != LF) break;
			}
			if(hasRead) mark--;
		}

		private void checkToken(char token) throws TextualVDFSyntaxException {
			skipWhitespaces();
			if(read() != token) missingToken(token);
		}

		private void missingToken(char token) throws TextualVDFSyntaxException {
			throw new TextualVDFSyntaxException("token '" + token + "' expected");
		}

	}

	private interface TextDeserializer {

		void deserialize(ObjectElement obj, String name, StringAnalyzer in) throws TextualVDFSyntaxException;

	}

	private interface ArrayElementReader {

		String read(StringAnalyzer in) throws TextualVDFSyntaxException;

	}

	private interface ArrayElementAdder<T> {

		void add(T array, int i, String value);

	}

	private interface ArrayAdder<T> {

		void add(ObjectElement obj, String name, T array);

	}

}
