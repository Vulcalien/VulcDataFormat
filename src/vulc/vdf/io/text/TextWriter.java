package vulc.vdf.io.text;

import static vulc.vdf.io.VDFCodes.*;
import static vulc.vdf.io.text.TextTokens.*;

import java.lang.reflect.Array;

import vulc.vdf.VDFList;
import vulc.vdf.VDFObject;
import vulc.vdf.io.VDFCodes;
import vulc.vdf.io.VDFWriter;

class TextWriter extends VDFWriter<StringBuilder> {

	private final TextSerializer[] serializers = new TextSerializer[VDFCodes.TYPES];

	protected boolean format = false;
	private int indentation = 0;

	protected TextWriter() {
		add(e -> out.append(e), BOOLEAN);
		add(e -> out.append(e), BYTE);
		add(e -> out.append(e), SHORT);
		add(e -> out.append(e), INT);
		add(e -> out.append(e), LONG);
		add(e -> out.append(e), FLOAT);
		add(e -> out.append(e), DOUBLE);
		add(e -> out.append(CHAR_QUOTE + escapeChar((Character) e) + CHAR_QUOTE), CHAR);
		add(e -> out.append(STRING_QUOTE + escapeString((String) e) + STRING_QUOTE), STRING);
		add(e -> serializeObject((VDFObject) e), OBJECT);
		add(e -> serializeList((VDFList) e), LIST);

		add(getArrayWriter(boolean[].class, (array, i) -> out.append(array[i])), BOOLEAN_A);
		add(getArrayWriter(byte[].class, (array, i) -> out.append(array[i])), BYTE_A);
		add(getArrayWriter(short[].class, (array, i) -> out.append(array[i])), SHORT_A);
		add(getArrayWriter(int[].class, (array, i) -> out.append(array[i])), INT_A);
		add(getArrayWriter(long[].class, (array, i) -> out.append(array[i])), LONG_A);
		add(getArrayWriter(float[].class, (array, i) -> out.append(array[i])), FLOAT_A);
		add(getArrayWriter(double[].class, (array, i) -> out.append(array[i])), DOUBLE_A);
		add(getArrayWriter(char[].class, (array, i) -> out.append(CHAR_QUOTE
		                                                          + escapeChar(array[i])
		                                                          + CHAR_QUOTE)),
		    CHAR_A);
		add(getArrayWriter(String[].class, (array, i) -> out.append(STRING_QUOTE
		                                                            + escapeString(array[i])
		                                                            + STRING_QUOTE)),
		    STRING_A);
		add(getArrayWriter(VDFObject[].class, (array, i) -> serializeObject(array[i])), OBJECT_A);
		add(getArrayWriter(VDFList[].class, (array, i) -> serializeList(array[i])), LIST_A);
	}

	private void add(TextSerializer serializer, byte code) {
		serializers[code] = serializer;
	}

	public void serializeObject(VDFObject obj) {
		out.append(OPEN_OBJECT);

		indentation++;
		for(String name : obj.keySet()) {
			Object e = obj.getElement(name);

			if(format) {
				out.append(TextIO.endOfLine);
				addIndentation();
			}

			byte code = VDFCodes.get(e.getClass());

			out.append(TextCodes.TAGS[code]);
			if(format) out.append(WHITESPACE);

			out.append(STRING_QUOTE + escapeString(name) + STRING_QUOTE);

			out.append(ASSIGN);
			if(format) out.append(WHITESPACE);

			serializers[code].serialize(e);
			out.append(SEPARATOR);
		}
		indentation--;

		if(obj.size() != 0) {
			out.deleteCharAt(out.length() - 1);

			if(format) {
				out.append(TextIO.endOfLine);
				addIndentation();
			}
		}
		out.append(CLOSE_OBJECT);
	}

	public void serializeList(VDFList list) {
		out.append(OPEN_LIST);

		indentation++;
		for(Object e : list) {
			if(format) {
				out.append(TextIO.endOfLine);
				addIndentation();
			}

			byte code = VDFCodes.get(e.getClass());

			out.append(TextCodes.TAGS[code]);
			out.append(WHITESPACE);

			serializers[code].serialize(e);
			out.append(SEPARATOR);
		}
		indentation--;

		if(list.size() != 0) {
			out.deleteCharAt(out.length() - 1);

			if(format) {
				out.append(TextIO.endOfLine);
				addIndentation();
			}
		}
		out.append(CLOSE_LIST);
	}

	private <T> TextSerializer getArrayWriter(Class<T> type, ArrayElementSerializer<T> elementSerializer) {
		return array -> {
			out.append(OPEN_ARRAY);

			indentation++;
			int length = Array.getLength(array);
			for(int i = 0; i < length; i++) {
				if(i != 0) out.append(SEPARATOR);

				if(format) {
					out.append(TextIO.endOfLine);
					addIndentation();
				}

				elementSerializer.serialize(type.cast(array), i);
			}
			indentation--;

			if(format) {
				out.append(TextIO.endOfLine);
				addIndentation();
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

	private void addIndentation() {
		for(int i = 0; i < indentation; i++) {
			out.append(TextIO.indentationChars);
		}
	}

	private interface TextSerializer {

		void serialize(Object e);

	}

	private interface ArrayElementSerializer<T> {

		void serialize(T array, int i);

	}

}
