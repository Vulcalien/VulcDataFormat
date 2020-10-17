package vulc.vdf.io;

import static vulc.vdf.io.VDFCodes.*;

abstract class TextCodes {

	protected static final String[] TAGS = new String[TYPES];

	static {
		TAGS[BOOLEAN] = "boolean";
		TAGS[BYTE] = "byte";
		TAGS[SHORT] = "short";
		TAGS[INT] = "int";
		TAGS[LONG] = "long";
		TAGS[FLOAT] = "float";
		TAGS[DOUBLE] = "double";
		TAGS[CHAR] = "char";
		TAGS[STRING] = "string";

		TAGS[BOOLEAN_A] = "boolean[]";
		TAGS[BYTE_A] = "byte[]";
		TAGS[SHORT_A] = "short[]";
		TAGS[INT_A] = "int[]";
		TAGS[LONG_A] = "long[]";
		TAGS[FLOAT_A] = "float[]";
		TAGS[DOUBLE_A] = "double[]";
		TAGS[CHAR_A] = "char[]";
		TAGS[STRING_A] = "string[]";

		TAGS[OBJECT] = "obj";
		TAGS[OBJECT_A] = "obj[]";
	}

}
