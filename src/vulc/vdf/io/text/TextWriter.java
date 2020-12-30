package vulc.vdf.io.text;

import static vulc.vdf.io.VDFCodes.*;
import static vulc.vdf.io.text.TextTokens.*;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Array;

import vulc.vdf.VDFList;
import vulc.vdf.VDFObject;
import vulc.vdf.io.VDFCodes;
import vulc.vdf.io.VDFWriter;

class TextWriter extends VDFWriter<Writer> {

	protected boolean format = false;
	private int indentation = 0;

	protected TextWriter() {
		add(e -> out.append(e.toString()), BOOLEAN);
		add(e -> out.append(e.toString()), BYTE);
		add(e -> out.append(e.toString()), SHORT);
		add(e -> out.append(e.toString()), INT);
		add(e -> out.append(e.toString()), LONG);
		add(e -> out.append(e.toString()), FLOAT);
		add(e -> out.append(e.toString()), DOUBLE);
		add(e -> out.append(CHAR_QUOTE + escapeChar((Character) e) + CHAR_QUOTE), CHAR);
		add(e -> out.append(STRING_QUOTE + escapeString((String) e) + STRING_QUOTE), STRING);
		add(e -> serializeObject((VDFObject) e), OBJECT);
		add(e -> serializeList((VDFList) e), LIST);

		add(getArrayWriter(boolean[].class, (array, i) -> out.append(Boolean.toString(array[i]))), BOOLEAN_A);
		add(getArrayWriter(byte[].class, (array, i) -> out.append(Byte.toString(array[i]))), BYTE_A);
		add(getArrayWriter(short[].class, (array, i) -> out.append(Short.toString(array[i]))), SHORT_A);
		add(getArrayWriter(int[].class, (array, i) -> out.append(Integer.toString(array[i]))), INT_A);
		add(getArrayWriter(long[].class, (array, i) -> out.append(Long.toString(array[i]))), LONG_A);
		add(getArrayWriter(float[].class, (array, i) -> out.append(Float.toString(array[i]))), FLOAT_A);
		add(getArrayWriter(double[].class, (array, i) -> out.append(Double.toString(array[i]))), DOUBLE_A);
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

	private void serializeObject(VDFObject obj) throws IOException {
		out.append(OPEN_OBJECT);

		indentation++;
		boolean isFirst = true;
		for(String key : obj.keySet()) {
			Object e = obj.getElement(key);

			if(!isFirst) {
				out.append(SEPARATOR);
			} else {
				isFirst = false;
			}

			if(format) {
				out.append(TextIO.endOfLine);
				addIndentation();
			}

			byte code = VDFCodes.get(e);

			out.append(TextCodes.TAGS[code]);
			if(format) out.append(WHITESPACE);

			out.append(STRING_QUOTE + escapeString(key) + STRING_QUOTE);

			out.append(ASSIGN);
			if(format) out.append(WHITESPACE);

			serializers[code].serialize(e);
		}
		indentation--;

		if(format && obj.size() != 0) {
			out.append(TextIO.endOfLine);
			addIndentation();
		}
		out.append(CLOSE_OBJECT);
	}

	private void serializeList(VDFList list) throws IOException {
		out.append(OPEN_LIST);

		indentation++;
		boolean isFirst = true;
		for(Object e : list) {
			if(!isFirst) {
				out.append(SEPARATOR);
			} else {
				isFirst = false;
			}

			if(format) {
				out.append(TextIO.endOfLine);
				addIndentation();
			}

			byte code = VDFCodes.get(e);

			out.append(TextCodes.TAGS[code]);
			out.append(WHITESPACE);

			serializers[code].serialize(e);
		}
		indentation--;

		if(format && list.size() != 0) {
			out.append(TextIO.endOfLine);
			addIndentation();
		}
		out.append(CLOSE_LIST);
	}

	protected <K> ElementSerializer getArrayWriter(Class<K> type, ArrayElementSerializer<K> serializer) {
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

				serializer.serialize(type.cast(array), i);
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

	private void addIndentation() throws IOException {
		for(int i = 0; i < indentation; i++) {
			out.append(TextIO.indentationChars);
		}
	}

}
