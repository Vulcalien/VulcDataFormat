package vulc.vdf.io.text;

import static vulc.vdf.io.VDFCodes.*;
import static vulc.vdf.io.text.TextTokens.*;

import java.lang.reflect.Array;

import vulc.vdf.ObjectElement;
import vulc.vdf.io.VDFCodes;

class TextReader {

	private final TextDeserializer[] deserializers = new TextDeserializer[VDFCodes.TYPES];

	protected TextReader() {
		char[] endOfValue = new char[] {
		    CLOSE_OBJECT, SEPARATOR,
		    WHITESPACE, TAB,
		    CR, LF
		};

		add((obj, name, in) -> obj.setBoolean(name, Boolean.valueOf(in.readUntil(endOfValue))), BOOLEAN);
		add((obj, name, in) -> obj.setByte(name, in.readNumber(Byte.class, endOfValue, Byte::valueOf)), BYTE);
		add((obj, name, in) -> obj.setShort(name, in.readNumber(Short.class, endOfValue, Short::valueOf)), SHORT);
		add((obj, name, in) -> obj.setInt(name, in.readNumber(Integer.class, endOfValue, Integer::valueOf)), INT);
		add((obj, name, in) -> obj.setLong(name, in.readNumber(Long.class, endOfValue, Long::valueOf)), LONG);
		add((obj, name, in) -> obj.setFloat(name, Float.valueOf(in.readUntil(endOfValue))), FLOAT);
		add((obj, name, in) -> obj.setDouble(name, Double.valueOf(in.readUntil(endOfValue))), DOUBLE);
		add((obj, name, in) -> obj.setChar(name, in.readChar()), CHAR);
		add((obj, name, in) -> obj.setString(name, in.readString()), STRING);
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
		                   (array, i, in) -> array[i] = in.readNumber(Byte.class, arrayEndOfValue, Byte::valueOf),
		                   (obj, name, array) -> obj.setByteArray(name, array)),
		    BYTE_A);

		add(getArrayReader(short[].class,
		                   (array, i, in) -> array[i] = in.readNumber(Short.class, arrayEndOfValue, Short::valueOf),
		                   (obj, name, array) -> obj.setShortArray(name, array)),
		    SHORT_A);

		add(getArrayReader(int[].class,
		                   (array, i, in) -> array[i] = in.readNumber(Integer.class, arrayEndOfValue, Integer::valueOf),
		                   (obj, name, array) -> obj.setIntArray(name, array)),
		    INT_A);

		add(getArrayReader(long[].class,
		                   (array, i, in) -> array[i] = in.readNumber(Long.class, arrayEndOfValue, Long::valueOf),
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
		                   (array, i, in) -> array[i] = in.readChar(),
		                   (obj, name, array) -> obj.setCharArray(name, array)),
		    CHAR_A);

		add(getArrayReader(String[].class,
		                   (array, i, in) -> array[i] = in.readString(),
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

	protected ObjectElement deserializeObject(String in, ObjectElement obj) {
		return deserializeObject(new StringAnalyzer(in), obj);
	}

	private ObjectElement deserializeObject(StringAnalyzer in, ObjectElement obj) {
		in.checkToken(OPEN_OBJECT);
		while(true) {
			in.skipWhitespaces();

			if(in.readIf(CLOSE_OBJECT)) break;
			if(in.readIf(SEPARATOR)) continue;

			String type = in.readUntil(WHITESPACE, TAB, CR, LF, STRING_QUOTE).toLowerCase();
			String name = in.readString();
			in.checkToken(ASSIGN);
			in.skipWhitespaces();

			Byte code = TextCodes.TAG_CODES.get(type);
			if(code == null) throw new VDFParseException("type '" + type + "' does not exist");
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

				if(in.readIf(CLOSE_ARRAY)) break;
				if(in.readIf(SEPARATOR)) continue;

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

	private interface TextDeserializer {

		void deserialize(ObjectElement obj, String name, StringAnalyzer in);

	}

	private interface ArrayElementDeserializer<T> {

		void add(T array, int i, StringAnalyzer in);

	}

	private interface ArrayAdder<T> {

		void add(ObjectElement obj, String name, T array);

	}

}
