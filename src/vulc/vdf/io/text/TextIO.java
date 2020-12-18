package vulc.vdf.io.text;

import java.io.IOException;
import java.io.Reader;

import vulc.vdf.VDFList;
import vulc.vdf.VDFObject;

public final class TextIO {

	private TextIO() {
	}

	/** This string is used for indentation. Its value can be changed. */
	public static String indentationChars = "\t";

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

	private static <T> void deserialize(Reader in, Deserializer<T> deserializer) throws IOException {
		if(!reuseIO) reader = new TextReader();

		reader.in = new StringAnalyzer(in);
		deserializer.deserialize(reader);

		if(!reuseIO) reader = null;
	}

	public static void deserialize(Reader in, VDFObject obj) throws IOException {
		deserialize(in, reader -> reader.deserializeObject(obj));
	}

	public static void deserialize(Reader in, VDFList list) throws IOException {
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

		void deserialize(TextReader reader) throws IOException;

	}

	private interface Serializer<T> {

		void serialize(TextWriter writer);

	}

}
