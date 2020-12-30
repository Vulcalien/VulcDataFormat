package vulc.vdf.io.text;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public final class TextIO {

	private TextIO() {
	}

	/** This string is used for indentation. Its value can be changed. */
	public static String indentationChars = "\t";

	/** This string is used as 'end of line'. Its value can be changed. */
	public static String endOfLine = "\n";

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

	public static Object deserialize(Reader in) throws IOException {
		if(!reuseIO) reader = new TextReader();

		Object element = reader.deserialize(new StringAnalyzer(in));

		if(!reuseIO) reader = null;
		return element;
	}

	public static void serialize(Writer out, Object element, boolean format) throws IOException {
		if(!reuseIO) writer = new TextWriter();
		writer.format = format;

		writer.serialize(out, element);

		if(!reuseIO) writer = null;
	}

}
