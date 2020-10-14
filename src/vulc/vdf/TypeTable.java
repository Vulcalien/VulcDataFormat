package vulc.vdf;

import java.util.HashMap;

abstract class TypeTable {

	private static final Class<?>[] TYPES = new Class<?>[20];
	private static final HashMap<Class<?>, Byte> CODES = new HashMap<Class<?>, Byte>();

	private static int assignIndex = 0;

	static {
		assign(BooleanTag.class);
		assign(CharTag.class);
		assign(ByteTag.class);
		assign(ShortTag.class);
		assign(IntTag.class);
		assign(LongTag.class);
		assign(FloatTag.class);
		assign(DoubleTag.class);
		assign(StringTag.class);

		assign(BooleanArrayTag.class);
		assign(CharArrayTag.class);
		assign(ByteArrayTag.class);
		assign(ShortArrayTag.class);
		assign(IntArrayTag.class);
		assign(LongArrayTag.class);
		assign(FloatArrayTag.class);
		assign(DoubleArrayTag.class);
		assign(StringArrayTag.class);

		assign(ObjectTag.class);
		assign(ObjectArrayTag.class);
	}

	private static void assign(Class<?> type) {
		TYPES[assignIndex] = type;
		CODES.put(type, (byte) assignIndex);
		assignIndex++;
	}

	static Tag getTag(byte code) {
		try {
			return (Tag) (TYPES[code].newInstance());
		} catch(Exception e) {
			e.printStackTrace();
		}
		throw new RuntimeException();
	}

	static byte getCode(Class<?> type) {
		return CODES.get(type);
	}

}
