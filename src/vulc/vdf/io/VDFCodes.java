package vulc.vdf.io;

import java.util.HashMap;

import vulc.vdf.BooleanArrayElement;
import vulc.vdf.BooleanElement;
import vulc.vdf.ByteArrayElement;
import vulc.vdf.ByteElement;
import vulc.vdf.CharArrayElement;
import vulc.vdf.CharElement;
import vulc.vdf.DoubleArrayElement;
import vulc.vdf.DoubleElement;
import vulc.vdf.FloatArrayElement;
import vulc.vdf.FloatElement;
import vulc.vdf.IntArrayElement;
import vulc.vdf.IntElement;
import vulc.vdf.ListArrayElement;
import vulc.vdf.LongArrayElement;
import vulc.vdf.LongElement;
import vulc.vdf.ObjectArrayElement;
import vulc.vdf.ShortArrayElement;
import vulc.vdf.ShortElement;
import vulc.vdf.StringArrayElement;
import vulc.vdf.StringElement;
import vulc.vdf.VDFList;
import vulc.vdf.VDFObject;

public abstract class VDFCodes {

	public static final byte TYPES = 22;

	public static final byte BOOLEAN = 0;
	public static final byte BYTE = 1;
	public static final byte SHORT = 2;
	public static final byte INT = 3;
	public static final byte LONG = 4;
	public static final byte FLOAT = 5;
	public static final byte DOUBLE = 6;
	public static final byte CHAR = 7;
	public static final byte STRING = 8;
	public static final byte OBJECT = 9;
	public static final byte LIST = 10;

	public static final byte BOOLEAN_A = 11;
	public static final byte BYTE_A = 12;
	public static final byte SHORT_A = 13;
	public static final byte INT_A = 14;
	public static final byte LONG_A = 15;
	public static final byte FLOAT_A = 16;
	public static final byte DOUBLE_A = 17;
	public static final byte CHAR_A = 18;
	public static final byte STRING_A = 19;
	public static final byte OBJECT_A = 20;
	public static final byte LIST_A = 21;

	private static final HashMap<Class<?>, Byte> CODES = new HashMap<Class<?>, Byte>();

	static {
		CODES.put(BooleanElement.class, BOOLEAN);
		CODES.put(ByteElement.class, BYTE);
		CODES.put(ShortElement.class, SHORT);
		CODES.put(IntElement.class, INT);
		CODES.put(LongElement.class, LONG);
		CODES.put(FloatElement.class, FLOAT);
		CODES.put(DoubleElement.class, DOUBLE);
		CODES.put(CharElement.class, CHAR);
		CODES.put(StringElement.class, STRING);
		CODES.put(VDFObject.class, OBJECT);
		CODES.put(VDFList.class, LIST);

		CODES.put(BooleanArrayElement.class, BOOLEAN_A);
		CODES.put(ByteArrayElement.class, BYTE_A);
		CODES.put(ShortArrayElement.class, SHORT_A);
		CODES.put(IntArrayElement.class, INT_A);
		CODES.put(LongArrayElement.class, LONG_A);
		CODES.put(FloatArrayElement.class, FLOAT_A);
		CODES.put(DoubleArrayElement.class, DOUBLE_A);
		CODES.put(CharArrayElement.class, CHAR_A);
		CODES.put(StringArrayElement.class, STRING_A);
		CODES.put(ObjectArrayElement.class, OBJECT_A);
		CODES.put(ListArrayElement.class, LIST_A);
	}

	public static byte get(Object key) {
		return CODES.get(key);
	}

}
