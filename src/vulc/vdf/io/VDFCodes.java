package vulc.vdf.io;

import java.util.HashMap;

import vulc.vdf.VDFList;
import vulc.vdf.VDFObject;

/**
 * This utility class contains the codes for all VDF types and the number of types.
 * The function {@code get(Class<?> type)} returns the type code associated with a class that
 * represents a VDF type.
 * 
 * @author Vulcalien
 */
public final class VDFCodes {

	private VDFCodes() {
	}

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
		CODES.put(Boolean.class, BOOLEAN);
		CODES.put(Byte.class, BYTE);
		CODES.put(Short.class, SHORT);
		CODES.put(Integer.class, INT);
		CODES.put(Long.class, LONG);
		CODES.put(Float.class, FLOAT);
		CODES.put(Double.class, DOUBLE);
		CODES.put(Character.class, CHAR);
		CODES.put(String.class, STRING);
		CODES.put(VDFObject.class, OBJECT);
		CODES.put(VDFList.class, LIST);

		CODES.put(boolean[].class, BOOLEAN_A);
		CODES.put(byte[].class, BYTE_A);
		CODES.put(short[].class, SHORT_A);
		CODES.put(int[].class, INT_A);
		CODES.put(long[].class, LONG_A);
		CODES.put(float[].class, FLOAT_A);
		CODES.put(double[].class, DOUBLE_A);
		CODES.put(char[].class, CHAR_A);
		CODES.put(String[].class, STRING_A);
		CODES.put(VDFObject[].class, OBJECT_A);
		CODES.put(VDFList[].class, LIST_A);
	}

	/**
	 * Returns the VDF type code.
	 * 
	 * @param   type  the class representing a VDF type
	 * @return  the VDF type code, or {@code null} if the specified class does not represent a VDF
	 *          type
	 */
	public static byte get(Class<?> type) {
		return CODES.get(type);
	}

}
