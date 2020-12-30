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
		    CR, LF,
		    EOF
		};

		add(() -> Boolean.valueOf(in.readUntil(endOfValue)), BOOLEAN);
		add(() -> in.readNumber(endOfValue, Byte::valueOf), BYTE);
		add(() -> in.readNumber(endOfValue, Short::valueOf), SHORT);
		add(() -> in.readNumber(endOfValue, Integer::valueOf), INT);
		add(() -> in.readNumber(endOfValue, Long::valueOf), LONG);
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
		                   (array, i) -> array[i] = in.readNumber(arrayEndOfValue, Byte::valueOf)),
		    BYTE_A);

		add(getArrayReader(short[].class,
		                   (array, i) -> array[i] = in.readNumber(arrayEndOfValue, Short::valueOf)),
		    SHORT_A);

		add(getArrayReader(int[].class,
		                   (array, i) -> array[i] = in.readNumber(arrayEndOfValue, Integer::valueOf)),
		    INT_A);

		add(getArrayReader(long[].class,
		                   (array, i) -> array[i] = in.readNumber(arrayEndOfValue, Long::valueOf)),
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

	protected Object deserializeTopLevel(StringAnalyzer in) throws IOException {
		in.skipWhitespaces();
		byte type = in.readTopLevelType();
		in.skipWhitespaces();

		return deserializers[type].deserialize();
	}

	public VDFObject deserializeObject(VDFObject obj) throws IOException {
		in.checkToken(OPEN_OBJECT);
		while(true) {
			in.skipWhitespaces();

			if(in.readIf(CLOSE_OBJECT)) break;
			if(in.readIf(SEPARATOR)) continue;

			byte type = in.readType();
			String key = in.readString();

			in.checkToken(ASSIGN);

			in.skipWhitespaces();
			obj.setElement(key, deserializers[type].deserialize());
			in.skipWhitespaces();

			char token = in.read();
			if(token == CLOSE_OBJECT) break;
			if(token != SEPARATOR) in.missingToken(SEPARATOR);

			in.releaseBuffer(); // after reading an element, release cache
		}
		return obj;
	}

	public VDFList deserializeList(VDFList list) throws IOException {
		in.checkToken(OPEN_LIST);
		while(true) {
			in.skipWhitespaces();

			if(in.readIf(CLOSE_LIST)) break;
			if(in.readIf(SEPARATOR)) continue;

			byte type = in.readType();

			in.skipWhitespaces();
			list.addElement(deserializers[type].deserialize());
			in.skipWhitespaces();

			char token = in.read();
			if(token == CLOSE_LIST) break;
			if(token != SEPARATOR) in.missingToken(SEPARATOR);

			in.releaseBuffer(); // after reading an element, release cache
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
