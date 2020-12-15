package vulc.vdf.io.text;

import vulc.vdf.VDFList;
import vulc.vdf.VDFObject;

public final class TextIO {

	private TextIO() {
	}

	private static TextReader reader = null;
	private static TextWriter writer = null;

	private static boolean reuseIO = false;

	public static void setReuseIO(boolean flag) {
		if(flag == reuseIO) return;

		reuseIO = flag;
		if(reuseIO) {
			reader = new TextReader();
			writer = new TextWriter();
		} else {
			reader = null;
			writer = null;
		}
	}

	// deserialize

	private static <T> void deserialize(String in, Deserializer<T> deserializer) {
		if(!reuseIO) reader = new TextReader();

		reader.in = new StringAnalyzer(in);
		deserializer.deserialize(reader);

		if(!reuseIO) reader = null;
	}

	public static void deserialize(String in, VDFObject obj) {
		deserialize(in, reader -> reader.deserializeObject(obj));
	}

	public static void deserialize(String in, VDFList list) {
		deserialize(in, reader -> reader.deserializeList(list));
	}

	// serialize

	private static <T> void serialize(StringBuilder out, boolean format, Serializer<T> serializer) {
		if(!reuseIO) writer = new TextWriter();

		writer.out = out;
		writer.format = format;

		serializer.serialize(writer);
		if(format) out.append(TextTokens.LF);

		if(!reuseIO) writer = null;
	}

	public static String stringify(VDFObject obj, boolean format) {
		StringBuilder out = new StringBuilder();
		serialize(out, format, writer -> writer.serializeObject(obj));
		return out.toString();
	}

	public static String stringify(VDFList list, boolean format) {
		StringBuilder out = new StringBuilder();
		serialize(out, format, writer -> writer.serializeList(list));
		return out.toString();
	}

	// interfaces

	private interface Deserializer<T> {

		void deserialize(TextReader reader);

	}

	private interface Serializer<T> {

		void serialize(TextWriter writer);

	}

}
