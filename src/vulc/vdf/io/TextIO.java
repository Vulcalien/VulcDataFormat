package vulc.vdf.io;

import java.io.IOException;
import java.io.PrintWriter;

import vulc.vdf.ObjectElement;

public abstract class TextIO {

//	private static final TextReader READER = new TextReader();
	private static final TextWriter WRITER = new TextWriter();

//	public static ObjectElement deserialize(DataInputStream in, ObjectElement obj) throws IOException {
//		return null;
//	}
//
//	public static ObjectElement deserialize(DataInputStream in) throws IOException {
//		return null;
//	}

	public static void serialize(PrintWriter out, ObjectElement obj) throws IOException {
		out.println(stringify(obj));
	}

	public static String stringify(ObjectElement obj) {
		StringBuilder builder = new StringBuilder();
		try {
			WRITER.serializeObject(builder, obj);
		} catch(IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}

}
