package vulc.vdf.io;

import vulc.vdf.ObjectElement;

public abstract class TextIO {

	private static final TextReader READER = new TextReader();
	private static final TextWriter WRITER = new TextWriter();

	public static ObjectElement deserialize(String in, ObjectElement obj) {
		return READER.deserializeObject(in, obj);
	}

	public static String stringify(ObjectElement obj, boolean format) {
		StringBuilder builder = new StringBuilder();
		WRITER.serializeObject(builder, obj, format, 0);
		return builder.toString();
	}

}
