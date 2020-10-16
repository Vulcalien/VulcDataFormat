package vulc.vdf;

import java.util.HashMap;

abstract class TypeTable {

	private static final Class<?>[] TYPES = new Class<?>[20];
	private static final HashMap<Class<?>, Byte> CODES = new HashMap<Class<?>, Byte>();

	private static int assignIndex = 0;

	static {
		assign(BooleanElement.class);
		assign(ByteElement.class);
		assign(ShortElement.class);
		assign(IntElement.class);
		assign(LongElement.class);
		assign(FloatElement.class);
		assign(DoubleElement.class);
		assign(CharElement.class);
		assign(StringElement.class);

		assign(BooleanArrayElement.class);
		assign(ByteArrayElement.class);
		assign(ShortArrayElement.class);
		assign(IntArrayElement.class);
		assign(LongArrayElement.class);
		assign(FloatArrayElement.class);
		assign(DoubleArrayElement.class);
		assign(CharArrayElement.class);
		assign(StringArrayElement.class);

		assign(ObjectElement.class);
		assign(ObjectArrayElement.class);
	}

	private static void assign(Class<?> type) {
		TYPES[assignIndex] = type;
		CODES.put(type, (byte) assignIndex);
		assignIndex++;
	}

	static Element createElement(byte code) {
		try {
			return (Element) (TYPES[code].newInstance());
		} catch(Exception e) {
			e.printStackTrace();
		}
		throw new RuntimeException();
	}

	static byte getCode(Class<?> type) {
		return CODES.get(type);
	}

}
