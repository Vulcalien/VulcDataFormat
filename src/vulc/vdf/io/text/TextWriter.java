package vulc.vdf.io.text;

import static vulc.vdf.io.VDFCodes.*;
import static vulc.vdf.io.text.TextTokens.*;

import java.lang.reflect.Array;

import vulc.vdf.BooleanElement;
import vulc.vdf.ByteElement;
import vulc.vdf.CharElement;
import vulc.vdf.DoubleElement;
import vulc.vdf.Element;
import vulc.vdf.FloatElement;
import vulc.vdf.IntElement;
import vulc.vdf.LongElement;
import vulc.vdf.ShortElement;
import vulc.vdf.StringElement;
import vulc.vdf.VDFObject;
import vulc.vdf.io.VDFCodes;

class TextWriter {

	private final TextSerializer[] serializers = new TextSerializer[VDFCodes.TYPES];

	protected TextWriter() {
		add((e, out, format, ind) -> out.append(((BooleanElement) e).value), BOOLEAN);
		add((e, out, format, ind) -> out.append(((ByteElement) e).value), BYTE);
		add((e, out, format, ind) -> out.append(((ShortElement) e).value), SHORT);
		add((e, out, format, ind) -> out.append(((IntElement) e).value), INT);
		add((e, out, format, ind) -> out.append(((LongElement) e).value), LONG);
		add((e, out, format, ind) -> out.append(((FloatElement) e).value), FLOAT);
		add((e, out, format, ind) -> out.append(((DoubleElement) e).value), DOUBLE);
		add((e, out, format, ind) -> out.append(CHAR_QUOTE + escapeChar(((CharElement) e).value) + CHAR_QUOTE), CHAR);
		add((e, out, format, ind) -> out.append(STRING_QUOTE + escapeString(((StringElement) e).value) + STRING_QUOTE),
		    STRING);
		add((e, out, format, ind) -> serializeObject(out, (VDFObject) e, format, ind), OBJECT);

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
		add(getArrayWriter(VDFObject[].class, (array, i, out) -> out.append(array[i])), OBJECT_A);
	}

	private void add(TextSerializer serializer, byte code) {
		serializers[code] = serializer;
	}

	protected void serializeObject(StringBuilder out, VDFObject obj, boolean format, int ind) {
		out.append(OPEN_OBJECT);

		ind++;

		for(String name : obj.keySet()) {
			Element e = obj.getElement(name);

			if(format) {
				out.append(LF);
				addIndentation(ind, out);
			}

			byte code = VDFCodes.get(e.getClass());

			out.append(TextCodes.TAGS[code]);
			if(format) out.append(WHITESPACE);

			out.append(STRING_QUOTE + escapeString(name) + STRING_QUOTE);

			out.append(ASSIGN);
			if(format) out.append(WHITESPACE);

			serializers[code].serialize(e, out, format, ind);

			out.append(SEPARATOR);
		}
		if(obj.size() != 0) out.deleteCharAt(out.length() - 1);

		if(format) {
			out.append(LF);
			addIndentation(ind - 1, out);
		}
		out.append(CLOSE_OBJECT);
	}

	private <T> TextSerializer getArrayWriter(Class<T> type, ArrayElementSerializer<T> elementSerializer) {
		return (e, out, format, ind) -> {
			out.append(OPEN_ARRAY);

			ind++;

			Object array = e.get();
			int length = Array.getLength(array);
			for(int i = 0; i < length; i++) {
				if(i != 0) out.append(SEPARATOR);

				if(format) {
					out.append(LF);
					addIndentation(ind, out);
				}

				elementSerializer.serialize(type.cast(array), i, out);
			}
			if(format) {
				out.append(LF);
				addIndentation(ind - 1, out);
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

	private void addIndentation(int n, StringBuilder out) {
		for(int i = 0; i < n; i++) {
			out.append(TAB);
		}
	}

	private interface TextSerializer {

		void serialize(Element e, StringBuilder out, boolean format, int ind);

	}

	private interface ArrayElementSerializer<T> {

		void serialize(T array, int i, StringBuilder out);

	}

}
