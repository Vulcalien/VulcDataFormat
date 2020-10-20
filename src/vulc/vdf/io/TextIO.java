package vulc.vdf.io;

import java.io.IOException;
import java.io.PrintStream;

import vulc.vdf.ObjectElement;

public abstract class TextIO {

	private static final TextReader READER = new TextReader();
	private static final TextWriter WRITER = new TextWriter();

	// TODO rewrite all this messy code

	public static ObjectElement deserialize(String in, ObjectElement obj) {
		try {
			return READER.deserializeObject(in, obj);
		} catch(TextualVDFSyntaxException e) {
			e.printStackTrace();
		}
		return obj;
	}

	public static void serialize(PrintStream out, ObjectElement obj) {
		out.println(stringify(obj));
	}

	public static String stringify(ObjectElement obj) {
		StringBuilder builder = new StringBuilder();
		try {
			WRITER.serializeObject(builder, obj, false);
		} catch(IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}

}
