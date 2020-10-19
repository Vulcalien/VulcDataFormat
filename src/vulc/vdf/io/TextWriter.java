package vulc.vdf.io;

import static vulc.vdf.io.VDFCodes.*;

import java.io.IOException;
import java.lang.reflect.Array;

import vulc.vdf.ObjectElement;

class TextWriter extends VDFWriter<StringBuilder> {

	public TextWriter() {
		add((value, out) -> out.append((boolean) value), BOOLEAN);
		add((value, out) -> out.append((byte) value), BYTE);
		add((value, out) -> out.append((short) value), SHORT);
		add((value, out) -> out.append((int) value), INT);
		add((value, out) -> out.append((long) value), LONG);
		add((value, out) -> out.append((float) value), FLOAT);
		add((value, out) -> out.append((double) value), DOUBLE);
		add((value, out) -> out.append("'" + clearChar(String.valueOf(value)) + "'"), CHAR);
		add((value, out) -> out.append("\"" + clearString((String) value) + "\""), STRING);

		add(getArrayWriter((value) -> value), BOOLEAN_A);
		add(getArrayWriter((value) -> value), BYTE_A);
		add(getArrayWriter((value) -> value), SHORT_A);
		add(getArrayWriter((value) -> value), INT_A);
		add(getArrayWriter((value) -> value), LONG_A);
		add(getArrayWriter((value) -> value), FLOAT_A);
		add(getArrayWriter((value) -> value), DOUBLE_A);
		add(getArrayWriter((value) -> "'" + clearChar(String.valueOf(value)) + "'"), CHAR_A);
		add(getArrayWriter((value) -> "\"" + clearString((String) value) + "\""), STRING_A);

		add(this::writeObject, OBJECT);
		add(getArrayWriter((value) -> value), OBJECT_A);
	}

	protected void serializeObject(StringBuilder out, ObjectElement obj) throws IOException {
		out.append("{");

		for(String name : obj.keySet()) {
			Object value = obj.getValue(name);

			byte code = CODES.get(value.getClass());

			out.append(TextCodes.TAGS[code]);
			out.append("\"" + clearString(name) + "\"");
			out.append(":");
			serializers[code].serialize(value, out);
			out.append(",");
		}
		if(obj.size() != 0) out.deleteCharAt(out.length() - 1);

		out.append("}");
	}

	private Serializer<StringBuilder> getArrayWriter(StringTransformer transformer) {
		return (value, out) -> {
			out.append("[");

			int length = Array.getLength(value);
			for(int i = 0; i < length; i++) {
				if(i != 0) out.append(",");
				out.append(transformer.transform(Array.get(value, i).toString()));
			}
			out.append("]");
		};
	}

	private void writeObject(Object value, StringBuilder out) throws IOException {
		serializeObject(out, (ObjectElement) value);
	}

	private String clearString(String string) {
		return string.replace("\\", "\\\\")
		             .replace("\t", "\\t")
		             .replace("\b", "\\b")
		             .replace("\n", "\\n")
		             .replace("\r", "\\r")
		             .replace("\f", "\\f")
		             .replace("\"", "\\\"");
	}

	private String clearChar(String charStr) {
		return charStr.replace("\\", "\\\\")
		              .replace("\t", "\\t")
		              .replace("\b", "\\b")
		              .replace("\n", "\\n")
		              .replace("\r", "\\r")
		              .replace("\f", "\\f")
		              .replace("'", "\\'");
	}

	private static interface StringTransformer {

		Object transform(String value);

	}

}
