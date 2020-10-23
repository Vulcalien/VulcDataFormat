package vulc.vdf.io.text;

import static vulc.vdf.io.VDFCodes.*;

import java.util.HashMap;

abstract class TextCodes {

	protected static final String[] TAGS = new String[TYPES];
	protected static final HashMap<String, Byte> TAG_CODES = new HashMap<String, Byte>();

	static {
		assign(BOOLEAN, "boolean");
		assign(BYTE, "byte");
		assign(SHORT, "short");
		assign(INT, "int");
		assign(LONG, "long");
		assign(FLOAT, "float");
		assign(DOUBLE, "double");
		assign(CHAR, "char");
		assign(STRING, "string");
		assign(OBJECT, "obj");

		assign(BOOLEAN_A, "boolean[]");
		assign(BYTE_A, "byte[]");
		assign(SHORT_A, "short[]");
		assign(INT_A, "int[]");
		assign(LONG_A, "long[]");
		assign(FLOAT_A, "float[]");
		assign(DOUBLE_A, "double[]");
		assign(CHAR_A, "char[]");
		assign(STRING_A, "string[]");
		assign(OBJECT_A, "obj[]");
	}

	private static void assign(byte code, String tag) {
		TAGS[code] = tag;
		TAG_CODES.put(tag, code);
	}

}
