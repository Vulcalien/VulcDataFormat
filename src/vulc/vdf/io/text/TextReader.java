package vulc.vdf.io.text;

import static vulc.vdf.io.VDFCodes.*;
import static vulc.vdf.io.text.TextTokens.*;

import java.io.IOException;
import java.lang.reflect.Array;

import vulc.vdf.VDFList;
import vulc.vdf.VDFObject;
import vulc.vdf.io.VDFReader;

class TextReader extends VDFReader<StringAnalyzer> {

	protected TextReader() {
		char[] endOfValue = new char[] {
		    CLOSE_OBJECT, CLOSE_LIST,
		    SEPARATOR,
		    WHITESPACE, TAB,
		    CR, LF
		};

		add(() -> Boolean.valueOf(in.readUntil(endOfValue)), BOOLEAN);
		add(() -> in.readNumber(Byte.class, endOfValue, Byte::valueOf), BYTE);
		add(() -> in.readNumber(Short.class, endOfValue, Short::valueOf), SHORT);
		add(() -> in.readNumber(Integer.class, endOfValue, Integer::valueOf), INT);
		add(() -> in.readNumber(Long.class, endOfValue, Long::valueOf), LONG);
		add(() -> Float.valueOf(in.readUntil(endOfValue)), FLOAT);
		add(() -> Double.valueOf(in.readUntil(endOfValue)), DOUBLE);
		add(() -> in.readChar(), CHAR);
		add(() -> in.readString(), STRING);
		add(() -> deserializeObject(new VDFObject()), OBJECT);
		add(() -> deserializeList(new VDFList()), LIST);

		char[] arrayEndOfValue = new char[] {
		    CLOSE_ARRAY, SEPARATOR,
		    WHITESPACE, TAB,
		    CR, LF
		};

		add(getArrayReader(boolean[].class, (array, i) -> array[i] = Boolean.valueOf(in.readUntil(arrayEndOfValue))),
		    BOOLEAN_A);

		add(getArrayReader(byte[].class,
		                   (array, i) -> array[i] = in.readNumber(Byte.class, arrayEndOfValue, Byte::valueOf)),
		    BYTE_A);

		add(getArrayReader(short[].class,
		                   (array, i) -> array[i] = in.readNumber(Short.class, arrayEndOfValue, Short::valueOf)),
		    SHORT_A);

		add(getArrayReader(int[].class,
		                   (array, i) -> array[i] = in.readNumber(Integer.class, arrayEndOfValue, Integer::valueOf)),
		    INT_A);

		add(getArrayReader(long[].class,
		                   (array, i) -> array[i] = in.readNumber(Long.class, arrayEndOfValue, Long::valueOf)),
		    LONG_A);

		add(getArrayReader(float[].class, (array, i) -> array[i] = Float.valueOf(in.readUntil(arrayEndOfValue))),
		    FLOAT_A);

		add(getArrayReader(double[].class, (array, i) -> array[i] = Double.valueOf(in.readUntil(arrayEndOfValue))),
		    DOUBLE_A);

		add(getArrayReader(char[].class, (array, i) -> array[i] = in.readChar()),
		    CHAR_A);

		add(getArrayReader(String[].class, (array, i) -> array[i] = in.readString()),
		    STRING_A);

		add(getArrayReader(VDFObject[].class, (array, i) -> array[i] = deserializeObject(new VDFObject())),
		    OBJECT_A);

		add(getArrayReader(VDFList[].class, (array, i) -> array[i] = deserializeList(new VDFList())),
		    LIST_A);
	}

	public VDFObject deserializeObject(VDFObject obj) throws IOException {
		in.checkToken(OPEN_OBJECT);
		while(true) {
			in.skipWhitespaces();

			if(in.readIf(CLOSE_OBJECT)) break;
			if(in.readIf(SEPARATOR)) continue;

			String type = in.readUntil(WHITESPACE, TAB, CR, LF,
			                           STRING_QUOTE) // int"..."
			                .toLowerCase();
			String name = in.readString();
			in.checkToken(ASSIGN);
			in.skipWhitespaces();

			Byte code = TextCodes.TAG_CODES.get(type);
			if(code == null) throw new VDFParseException("type '" + type + "' does not exist", in.line);
			obj.setElement(name, deserializers[code].deserialize());

			in.skipWhitespaces();

			char token = in.read();
			if(token == CLOSE_OBJECT) break;
			if(token != SEPARATOR) in.missingToken(SEPARATOR);
		}
		return obj;
	}

	public VDFList deserializeList(VDFList list) throws IOException {
		in.checkToken(OPEN_LIST);
		while(true) {
			in.skipWhitespaces();

			if(in.readIf(CLOSE_LIST)) break;
			if(in.readIf(SEPARATOR)) continue;

			// TODO allow array value after type, without spaces
			String type = in.readUntil(WHITESPACE, TAB, CR, LF,
			                           CHAR_QUOTE, STRING_QUOTE) // char'c' or string"str"
			                .toLowerCase();
			in.skipWhitespaces();

			Byte code = TextCodes.TAG_CODES.get(type);
			if(code == null) throw new VDFParseException("type '" + type + "' does not exist", in.line);
			list.addElement(deserializers[code].deserialize());

			in.skipWhitespaces();

			char token = in.read();
			if(token == CLOSE_LIST) break;
			if(token != SEPARATOR) in.missingToken(SEPARATOR);
		}
		return list;
	}

	protected <K> ElementDeserializer getArrayReader(Class<K> type, ArrayElementDeserializer<K> deserializer) {
		return () -> {
			in.checkToken(OPEN_ARRAY);

			int length = 8;
			K array = type.cast(Array.newInstance(type.getComponentType(), length));

			int i = 0;
			while(true) {
				in.skipWhitespaces();

				if(in.readIf(CLOSE_ARRAY)) break;
				if(in.readIf(SEPARATOR)) continue;

				if(i == length) {
					K newArray = type.cast(Array.newInstance(type.getComponentType(), length + 8));
					System.arraycopy(array, 0, newArray, 0, length);
					array = newArray;
					length += 8;
				}

				deserializer.deserialize(array, i++);
				in.skipWhitespaces();

				char token = in.read();
				if(token == CLOSE_ARRAY) break;
				if(token != SEPARATOR) in.missingToken(SEPARATOR);
			}

			K result = type.cast(Array.newInstance(type.getComponentType(), i));
			System.arraycopy(array, 0, result, 0, i);
			return result;
		};
	}

}
