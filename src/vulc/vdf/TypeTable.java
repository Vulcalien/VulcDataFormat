package vulc.vdf;

abstract class TypeTable {

	private static final int TAG_TYPES = 18;

	private static final Class<?>[] TYPES = new Class<?>[TAG_TYPES];
	private static final byte[] CODES = new byte[TAG_TYPES];

	private static int assignIndex = 0;

	static {
		assign(BooleanTag.class, 0);
		assign(CharTag.class, 1);
		assign(ByteTag.class, 2);
		assign(ShortTag.class, 3);
		assign(IntTag.class, 4);
		assign(LongTag.class, 5);
		assign(FloatTag.class, 6);
		assign(DoubleTag.class, 7);

		assign(BooleanArrayTag.class, 20);
		assign(CharArrayTag.class, 21);
		assign(ByteArrayTag.class, 22);
		assign(ShortArrayTag.class, 23);
		assign(IntArrayTag.class, 24);
		assign(LongArrayTag.class, 25);
		assign(FloatArrayTag.class, 26);
		assign(DoubleArrayTag.class, 27);

		assign(ObjectTag.class, 100);
		assign(ObjectArrayTag.class, 101);
	}

	private static void assign(Class<?> type, int code) {
		TYPES[assignIndex] = type;
		CODES[assignIndex] = (byte) code;
		assignIndex++;
	}

	static Tag<?> getTag(byte code) {
		for(int i = 0; i < CODES.length; i++) {
			if(CODES[i] == code) {
				try {
					return (Tag<?>) (TYPES[i].newInstance());
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		throw new RuntimeException();
	}

	static byte getCode(Class<?> type) {
		for(int i = 0; i < TYPES.length; i++) {
			if(TYPES[i] == type) {
				return CODES[i];
			}
		}
		throw new RuntimeException();
	}

}
