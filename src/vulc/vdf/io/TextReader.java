package vulc.vdf.io;

import static vulc.vdf.io.TextTokens.*;
import static vulc.vdf.io.VDFCodes.*;

import java.lang.reflect.Array;

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
		add((obj, name, in) -> obj.setByte(name, readNumber(Byte.class, in, endOfValue, Byte::valueOf)), BYTE);
		add((obj, name, in) -> obj.setShort(name, readNumber(Short.class, in, endOfValue, Short::valueOf)), SHORT);
		add((obj, name, in) -> obj.setInt(name, readNumber(Integer.class, in, endOfValue, Integer::valueOf)), INT);
		add((obj, name, in) -> obj.setLong(name, readNumber(Long.class, in, endOfValue, Long::valueOf)), LONG);
		add((obj, name, in) -> obj.setFloat(name, Float.valueOf(in.readUntil(endOfValue))), FLOAT);
		add((obj, name, in) -> obj.setDouble(name, Double.valueOf(in.readUntil(endOfValue))), DOUBLE);
		add((obj, name, in) -> obj.setChar(name, readChar(in)), CHAR);
		add((obj, name, in) -> obj.setString(name, readString(in)), STRING);
		add((obj, name, in) -> obj.setObject(name, deserializeObject(in, new ObjectElement())), OBJECT);

		char[] arrayEndOfValue = new char[] {
		    CLOSE_ARRAY, SEPARATOR,
		    WHITESPACE, TAB,
		    CR, LF
		};

		add(getArrayReader(boolean[].class,
		                   (array, i, in) -> array[i] = Boolean.valueOf(in.readUntil(arrayEndOfValue)),
		                   (obj, name, array) -> obj.setBooleanArray(name, array)),
		    BOOLEAN_A);

		add(getArrayReader(byte[].class,
		                   (array, i, in) -> array[i] = readNumber(Byte.class, in, arrayEndOfValue, Byte::valueOf),
		                   (obj, name, array) -> obj.setByteArray(name, array)),
		    BYTE_A);

		add(getArrayReader(short[].class,
		                   (array, i, in) -> array[i] = readNumber(Short.class, in, arrayEndOfValue, Short::valueOf),
		                   (obj, name, array) -> obj.setShortArray(name, array)),
		    SHORT_A);

		add(getArrayReader(int[].class,
		                   (array, i, in) -> array[i] = readNumber(Integer.class, in,
		                                                           arrayEndOfValue, Integer::valueOf),
		                   (obj, name, array) -> obj.setIntArray(name, array)),
		    INT_A);

		add(getArrayReader(long[].class,
		                   (array, i, in) -> array[i] = readNumber(Long.class, in, arrayEndOfValue, Long::valueOf),
		                   (obj, name, array) -> obj.setLongArray(name, array)),
		    LONG_A);

		add(getArrayReader(float[].class,
		                   (array, i, in) -> array[i] = Float.valueOf(in.readUntil(arrayEndOfValue)),
		                   (obj, name, array) -> obj.setFloatArray(name, array)),
		    FLOAT_A);

		add(getArrayReader(double[].class,
		                   (array, i, in) -> array[i] = Double.valueOf(in.readUntil(arrayEndOfValue)),
		                   (obj, name, array) -> obj.setDoubleArray(name, array)),
		    DOUBLE_A);

		add(getArrayReader(char[].class,
		                   (array, i, in) -> array[i] = readChar(in),
		                   (obj, name, array) -> obj.setCharArray(name, array)),
		    CHAR_A);

		add(getArrayReader(String[].class,
		                   (array, i, in) -> array[i] = readString(in),
		                   (obj, name, array) -> obj.setStringArray(name, array)),
		    STRING_A);

		add(getArrayReader(ObjectElement[].class,
		                   (array, i, in) -> array[i] = deserializeObject(in, new ObjectElement()),
		                   (obj, name, array) -> obj.setObjectArray(name, array)),
		    OBJECT_A);
	}

	private void add(TextDeserializer deserializer, byte code) {
		deserializers[code] = deserializer;
	}

	protected ObjectElement deserializeObject(String in, ObjectElement obj) throws TextualVDFSyntaxException {
		return deserializeObject(new StringAnalyzer(in), obj);
	}

	private ObjectElement deserializeObject(StringAnalyzer in, ObjectElement obj) throws TextualVDFSyntaxException {
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

			String type = in.readUntil(WHITESPACE, TAB, CR, LF, STRING_QUOTE).toLowerCase();
			String name = readString(in);
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

	private <T, E> TextDeserializer getArrayReader(Class<T> type,
	                                               ArrayElementDeserializer<T> elementDeserializer,
	                                               ArrayAdder<T> arrayAdder) {
		return (obj, name, in) -> {
			in.checkToken(OPEN_ARRAY);

			int length = 8;
			T array = type.cast(Array.newInstance(type.getComponentType(), length));

			int i = 0;
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

				if(i == length) {
					T newArray = type.cast(Array.newInstance(type.getComponentType(), length + 8));
					System.arraycopy(array, 0, newArray, 0, length);
					array = newArray;
					length += 8;
				}

				elementDeserializer.add(array, i++, in);
				in.skipWhitespaces();

				char token = in.read();
				if(token == CLOSE_ARRAY) break;
				if(token != SEPARATOR) in.missingToken(SEPARATOR);
			}

			T result = type.cast(Array.newInstance(type.getComponentType(), i));
			System.arraycopy(array, 0, result, 0, i);
			arrayAdder.add(obj, name, result);
		};
	}

	private <T> T readNumber(Class<T> type, StringAnalyzer in,
	                         char[] endOfValue, NumberReader<T> reader) throws TextualVDFSyntaxException {
		String string = in.readUntil(endOfValue);

		int radix = 10;
		if(string.length() > 1 && string.charAt(0) == '0') {
			char c1 = string.charAt(1);
			if(c1 == 'b' || c1 == 'B') {
				radix = 2;
				string = string.substring(2);
			} else if(c1 == 'x' || c1 == 'X') {
				radix = 16;
				string = string.substring(2);
			} else {
				radix = 8;
				string = string.substring(1);
			}
		}
		return reader.read(string, radix);
	}

	private char readChar(StringAnalyzer in) throws TextualVDFSyntaxException {
		StringBuilder result = new StringBuilder();

		in.checkToken(CHAR_QUOTE);
		while(true) {
			result.append(in.readUntil(CHAR_QUOTE, '\\'));

			char token = in.read();
			if(token == CHAR_QUOTE) {
				return unescapeChar(result.toString());
			} else {
				result.append('\\');
				result.append(in.read());
			}
		}
	}

	private String readString(StringAnalyzer in) throws TextualVDFSyntaxException {
		StringBuilder result = new StringBuilder();

		in.checkToken(STRING_QUOTE);
		while(true) {
			result.append(in.readUntil(STRING_QUOTE, '\\'));

			char token = in.read();
			if(token == STRING_QUOTE) {
				return result.toString();
			} else {
				result.append(unescapeChar("\\" + in.read()));
			}
		}
	}

	private char unescapeChar(String strChar) throws TextualVDFSyntaxException {
		if(strChar.equals("\\\\")) return '\\';
		if(strChar.equals("\\t")) return '\t';
		if(strChar.equals("\\b")) return '\b';
		if(strChar.equals("\\n")) return '\n';
		if(strChar.equals("\\r")) return '\r';
		if(strChar.equals("\\f")) return '\f';
		if(strChar.equals("\\'")) return '\'';
		if(strChar.equals("\\\"")) return '\"';

		if(strChar.length() != 1) throw new TextualVDFSyntaxException("char error"); // TODO better error message
		return strChar.charAt(0);
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

	private interface ArrayElementDeserializer<T> {

		void add(T array, int i, StringAnalyzer in) throws TextualVDFSyntaxException;

	}

	private interface ArrayAdder<T> {

		void add(ObjectElement obj, String name, T array);

	}

	private interface NumberReader<T> {

		T read(String value, int radix);

	}

}
