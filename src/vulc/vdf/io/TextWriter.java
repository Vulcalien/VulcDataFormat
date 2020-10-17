package vulc.vdf.io;

import static vulc.vdf.io.VDFCodes.*;

import java.io.IOException;

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

		add(this::writeBooleanArray, BOOLEAN_A);
		add(this::writeByteArray, BYTE_A);
		add(this::writeShortArray, SHORT_A);
		add(this::writeIntArray, INT_A);
		add(this::writeLongArray, LONG_A);
		add(this::writeFloatArray, FLOAT_A);
		add(this::writeDoubleArray, DOUBLE_A);
		add(this::writeCharArray, CHAR_A);
		add(this::writeStringArray, STRING_A);

		add(this::writeObject, OBJECT);
		add(this::writeObjectArray, OBJECT_A);
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
			out.append(","); // TODO don't add last comma
		}
		out.append("}");
	}

	// write

	private void writeBooleanArray(Object value, StringBuilder out) {
		out.append("[");

		for(boolean v : (boolean[]) value) {
			out.append(v);
			out.append(",");
		}
		out.append("]");
	}

	private void writeByteArray(Object value, StringBuilder out) {
		out.append("[");

		for(byte v : (byte[]) value) {
			out.append(v);
			out.append(",");
		}
		out.append("]");
	}

	private void writeShortArray(Object value, StringBuilder out) {
		out.append("[");

		for(short v : (short[]) value) {
			out.append(v);
			out.append(",");
		}
		out.append("]");
	}

	private void writeIntArray(Object value, StringBuilder out) {
		out.append("[");

		for(int v : (int[]) value) {
			out.append(v);
			out.append(",");
		}
		out.append("]");
	}

	private void writeLongArray(Object value, StringBuilder out) {
		out.append("[");

		for(long v : (long[]) value) {
			out.append(v);
			out.append(",");
		}
		out.append("]");
	}

	private void writeFloatArray(Object value, StringBuilder out) {
		out.append("[");

		for(float v : (float[]) value) {
			out.append(v);
			out.append(",");
		}
		out.append("]");
	}

	private void writeDoubleArray(Object value, StringBuilder out) {
		out.append("[");

		for(double v : (double[]) value) {
			out.append(v);
			out.append(",");
		}
		out.append("]");
	}

	private void writeCharArray(Object value, StringBuilder out) {
		out.append("[");

		for(char v : (char[]) value) {
			out.append("'" + clearChar(String.valueOf(v)) + "'");
			out.append(",");
		}
		out.append("]");
	}

	private void writeStringArray(Object value, StringBuilder out) {
		out.append("[");

		for(String v : (String[]) value) {
			out.append("\"" + clearString(v) + "\"");
			out.append(",");
		}
		out.append("]");
	}

	private void writeObject(Object value, StringBuilder out) throws IOException {
		serializeObject(out, (ObjectElement) value);
	}

	private void writeObjectArray(Object value, StringBuilder out) {
		out.append("[");

		for(ObjectElement v : (ObjectElement[]) value) {
			out.append(v);
			out.append(",");
		}
		out.append("]");
	}

	// utils

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

}
