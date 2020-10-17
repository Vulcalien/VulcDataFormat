package vulc.vdf.io;

import java.util.HashMap;

import vulc.vdf.ObjectElement;

abstract class VDFCodes {

	protected static final byte TYPES = 20;

	protected static final byte BOOLEAN = 0;
	protected static final byte BYTE = 1;
	protected static final byte SHORT = 2;
	protected static final byte INT = 3;
	protected static final byte LONG = 4;
	protected static final byte FLOAT = 5;
	protected static final byte DOUBLE = 6;
	protected static final byte CHAR = 7;
	protected static final byte STRING = 8;

	protected static final byte BOOLEAN_A = 9;
	protected static final byte BYTE_A = 10;
	protected static final byte SHORT_A = 11;
	protected static final byte INT_A = 12;
	protected static final byte LONG_A = 13;
	protected static final byte FLOAT_A = 14;
	protected static final byte DOUBLE_A = 15;
	protected static final byte CHAR_A = 16;
	protected static final byte STRING_A = 17;

	protected static final byte OBJECT = 18;
	protected static final byte OBJECT_A = 19;

	protected static final HashMap<Class<?>, Byte> CODES = new HashMap<Class<?>, Byte>();

	static {
		CODES.put(Boolean.class, BOOLEAN);
		CODES.put(Byte.class, BYTE);
		CODES.put(Short.class, SHORT);
		CODES.put(Integer.class, INT);
		CODES.put(Long.class, LONG);
		CODES.put(Float.class, FLOAT);
		CODES.put(Double.class, DOUBLE);
		CODES.put(Character.class, CHAR);
		CODES.put(String.class, STRING);

		CODES.put(boolean[].class, BOOLEAN_A);
		CODES.put(byte[].class, BYTE_A);
		CODES.put(short[].class, SHORT_A);
		CODES.put(int[].class, INT_A);
		CODES.put(long[].class, LONG_A);
		CODES.put(float[].class, FLOAT_A);
		CODES.put(double[].class, DOUBLE_A);
		CODES.put(char[].class, CHAR_A);
		CODES.put(String[].class, STRING_A);

		CODES.put(ObjectElement.class, OBJECT);
		CODES.put(ObjectElement[].class, OBJECT_A);
	}

}
