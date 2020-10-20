package vulc.vdf.io;

import static vulc.vdf.io.TextTokens.*;
import static vulc.vdf.io.VDFCodes.*;

import java.io.IOException;
import java.lang.reflect.Array;

import vulc.vdf.ObjectElement;

class TextWriter {

	private final TextSerializer[] serializers = new TextSerializer[VDFCodes.TYPES];

	protected TextWriter() {
		add((value, out) -> out.append((boolean) value), BOOLEAN);
		add((value, out) -> out.append((byte) value), BYTE);
		add((value, out) -> out.append((short) value), SHORT);
		add((value, out) -> out.append((int) value), INT);
		add((value, out) -> out.append((long) value), LONG);
		add((value, out) -> out.append((float) value), FLOAT);
		add((value, out) -> out.append((double) value), DOUBLE);
		add((value, out) -> out.append(CHAR_QUOTE + escapeChar((char) value) + CHAR_QUOTE), CHAR);
		add((value, out) -> out.append(STRING_QUOTE + escapeString((String) value) + STRING_QUOTE), STRING);
		add((value, out) -> serializeObject(out, (ObjectElement) value), OBJECT);

		add(getArrayWriter(boolean[].class, (array, i, out) -> out.append(array[i])), BOOLEAN_A);
		add(getArrayWriter(byte[].class, (array, i, out) -> out.append(array[i])), BYTE_A);
		add(getArrayWriter(short[].class, (array, i, out) -> out.append(array[i])), SHORT_A);
		add(getArrayWriter(int[].class, (array, i, out) -> out.append(array[i])), INT_A);
		add(getArrayWriter(long[].class, (array, i, out) -> out.append(array[i])), LONG_A);
		add(getArrayWriter(float[].class, (array, i, out) -> out.append(array[i])), FLOAT_A);
		add(getArrayWriter(double[].class, (array, i, out) -> out.append(array[i])), DOUBLE_A);
		add(getArrayWriter(char[].class, (array, i, out) -> out.append(CHAR_QUOTE
		                                                               + escapeChar(array[i])
		                                                               + CHAR_QUOTE)),
		    CHAR_A);
		add(getArrayWriter(String[].class, (array, i, out) -> out.append(STRING_QUOTE
		                                                                 + escapeString(array[i])
		                                                                 + STRING_QUOTE)),
		    STRING_A);
		add(getArrayWriter(ObjectElement[].class, (array, i, out) -> out.append(array[i])), OBJECT_A);
	}

	private void add(TextSerializer serializer, byte code) {
		serializers[code] = serializer;
	}

	protected void serializeObject(StringBuilder out, ObjectElement obj) throws IOException {
		out.append(OPEN_OBJECT);

		for(String name : obj.keySet()) {
			Object value = obj.getValue(name);

			byte code = CODES.get(value.getClass());

			out.append(TextCodes.TAGS[code]);
			out.append(STRING_QUOTE + escapeString(name) + STRING_QUOTE);
			out.append(ASSIGN);
			serializers[code].serialize(value, out);
			out.append(SEPARATOR);
		}
		if(obj.size() != 0) out.deleteCharAt(out.length() - 1);

		out.append(CLOSE_OBJECT);
	}

	private <T> TextSerializer getArrayWriter(Class<T> type, ArrayElementSerializer<T> elementSerializer) {
		return (value, out) -> {
			out.append(OPEN_ARRAY);

			int length = Array.getLength(value);
			for(int i = 0; i < length; i++) {
				if(i != 0) out.append(SEPARATOR);
				elementSerializer.serialize(type.cast(value), i, out);
			}
			out.append(CLOSE_ARRAY);
		};
	}

	private String escapeString(String string) {
		StringBuilder result = new StringBuilder();
		for(int i = 0; i < string.length(); i++) {
			result.append(escapeStringChar(string.charAt(i)));
		}
		return result.toString();
	}

	private String escapeChar(char c) {
		if(c == '\\') return "\\\\";
		if(c == '\t') return "\\t";
		if(c == '\b') return "\\b";
		if(c == '\n') return "\\n";
		if(c == '\r') return "\\r";
		if(c == '\f') return "\\f";
		if(c == '\'') return "\\'";

		return String.valueOf(c);
	}

	private String escapeStringChar(char c) {
		if(c == '\\') return "\\\\";
		if(c == '\t') return "\\t";
		if(c == '\b') return "\\b";
		if(c == '\n') return "\\n";
		if(c == '\r') return "\\r";
		if(c == '\f') return "\\f";
		if(c == '\"') return "\\\"";

		return String.valueOf(c);
	}

	private interface TextSerializer {

		void serialize(Object value, StringBuilder out) throws IOException;

	}

	private interface ArrayElementSerializer<T> {

		void serialize(T array, int i, StringBuilder out);

	}

}
