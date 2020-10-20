package vulc.vdf.io;

import java.io.IOException;
import java.io.PrintStream;

import vulc.vdf.ObjectElement;

public abstract class TextIO {

	private static final TextReader READER = new TextReader();
	private static final TextWriter WRITER = new TextWriter();

	public static ObjectElement deserialize(String in, ObjectElement obj) {
		try {
			return READER.deserializeObject(in, obj);
		} catch(TextualVDFSyntaxException e) {
			e.printStackTrace();
		}
		return obj;
	}

	public static void serialize(PrintStream out, ObjectElement obj, boolean format) {
		out.println(stringify(obj, format));
	}

	public static String stringify(ObjectElement obj, boolean format) {
		StringBuilder builder = new StringBuilder();
		try {
			WRITER.serializeObject(builder, obj, format, 0);
		} catch(IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}

}
