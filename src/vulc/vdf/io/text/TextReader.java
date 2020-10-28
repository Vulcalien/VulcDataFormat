package vulc.vdf.io.text;

import static vulc.vdf.io.VDFCodes.*;
import static vulc.vdf.io.text.TextTokens.*;

import java.lang.reflect.Array;

import vulc.vdf.BooleanArrayElement;
import vulc.vdf.BooleanElement;
import vulc.vdf.ByteArrayElement;
import vulc.vdf.ByteElement;
import vulc.vdf.CharArrayElement;
import vulc.vdf.CharElement;
import vulc.vdf.DoubleArrayElement;
import vulc.vdf.DoubleElement;
import vulc.vdf.Element;
import vulc.vdf.FloatArrayElement;
import vulc.vdf.FloatElement;
import vulc.vdf.IntArrayElement;
import vulc.vdf.IntElement;
import vulc.vdf.LongArrayElement;
import vulc.vdf.LongElement;
import vulc.vdf.ObjectArrayElement;
import vulc.vdf.ShortArrayElement;
import vulc.vdf.ShortElement;
import vulc.vdf.StringArrayElement;
import vulc.vdf.StringElement;
import vulc.vdf.VDFObject;
import vulc.vdf.io.VDFCodes;

class TextReader {

	private final ElementDeserializer[] deserializers = new ElementDeserializer[VDFCodes.TYPES];

	protected TextReader() {
		char[] endOfValue = new char[] {
		    CLOSE_OBJECT, SEPARATOR,
		    WHITESPACE, TAB,
		    CR, LF
		};

		add(in -> new BooleanElement(Boolean.valueOf(in.readUntil(endOfValue))), BOOLEAN);
		add(in -> new ByteElement(in.readNumber(Byte.class, endOfValue, Byte::valueOf)), BYTE);
		add(in -> new ShortElement(in.readNumber(Short.class, endOfValue, Short::valueOf)), SHORT);
		add(in -> new IntElement(in.readNumber(Integer.class, endOfValue, Integer::valueOf)), INT);
		add(in -> new LongElement(in.readNumber(Long.class, endOfValue, Long::valueOf)), LONG);
		add(in -> new FloatElement(Float.valueOf(in.readUntil(endOfValue))), FLOAT);
		add(in -> new DoubleElement(Double.valueOf(in.readUntil(endOfValue))), DOUBLE);
		add(in -> new CharElement(in.readChar()), CHAR);
		add(in -> new StringElement(in.readString()), STRING);
		add(in -> deserializeObject(in, new VDFObject()), OBJECT);

		char[] arrayEndOfValue = new char[] {
		    CLOSE_ARRAY, SEPARATOR,
		    WHITESPACE, TAB,
		    CR, LF
		};

		add(getArrayReader(boolean[].class, BooleanArrayElement::new,
		                   (array, i, in) -> array[i] = Boolean.valueOf(in.readUntil(arrayEndOfValue))),
		    BOOLEAN_A);

		add(getArrayReader(byte[].class, ByteArrayElement::new,
		                   (array, i, in) -> array[i] = in.readNumber(Byte.class, arrayEndOfValue, Byte::valueOf)),
		    BYTE_A);

		add(getArrayReader(short[].class, ShortArrayElement::new,
		                   (array, i, in) -> array[i] = in.readNumber(Short.class, arrayEndOfValue, Short::valueOf)),
		    SHORT_A);

		add(getArrayReader(int[].class, IntArrayElement::new,
		                   (array, i, in) -> array[i] =
		                           in.readNumber(Integer.class, arrayEndOfValue, Integer::valueOf)),
		    INT_A);

		add(getArrayReader(long[].class, LongArrayElement::new,
		                   (array, i, in) -> array[i] = in.readNumber(Long.class, arrayEndOfValue, Long::valueOf)),
		    LONG_A);

		add(getArrayReader(float[].class, FloatArrayElement::new,
		                   (array, i, in) -> array[i] = Float.valueOf(in.readUntil(arrayEndOfValue))),
		    FLOAT_A);

		add(getArrayReader(double[].class, DoubleArrayElement::new,
		                   (array, i, in) -> array[i] = Double.valueOf(in.readUntil(arrayEndOfValue))),
		    DOUBLE_A);

		add(getArrayReader(char[].class, CharArrayElement::new,
		                   (array, i, in) -> array[i] = in.readChar()),
		    CHAR_A);

		add(getArrayReader(String[].class, StringArrayElement::new,
		                   (array, i, in) -> array[i] = in.readString()),
		    STRING_A);

		add(getArrayReader(VDFObject[].class, ObjectArrayElement::new,
		                   (array, i, in) -> array[i] = deserializeObject(in, new VDFObject())),
		    OBJECT_A);
	}

	private void add(ElementDeserializer deserializer, byte code) {
		deserializers[code] = deserializer;
	}

	protected VDFObject deserializeObject(String in, VDFObject obj) {
		return deserializeObject(new StringAnalyzer(in), obj);
	}

	private VDFObject deserializeObject(StringAnalyzer in, VDFObject obj) {
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
			if(code == null) throw new VDFParseException("type '" + type + "' does not exist", in.line);
			obj.setElement(name, deserializers[code].deserialize(in));

			in.skipWhitespaces();

			char token = in.read();
			if(token == CLOSE_OBJECT) break;
			if(token != SEPARATOR) in.missingToken(SEPARATOR);
		}
		return obj;
	}

	private <T, E> ElementDeserializer getArrayReader(Class<T> type,
	                                                  ArrayMaker<T> arrayMaker,
	                                                  ArrayElementDeserializer<T> elementDeserializer) {
		return (in) -> {
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
			return arrayMaker.make(result);
		};
	}

	private interface ElementDeserializer {

		Element deserialize(StringAnalyzer in);

	}

	private interface ArrayMaker<T> {

		Element make(T array);

	}

	private interface ArrayElementDeserializer<T> {

		void add(T array, int i, StringAnalyzer in);

	}

}
