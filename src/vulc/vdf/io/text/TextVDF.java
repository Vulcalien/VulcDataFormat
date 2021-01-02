package vulc.vdf.io.text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

public final class TextVDF {

	private TextVDF() {
	}

	/** This string is used for indentation. Its value can be changed. */
	public static String indentationChars = "\t";

	/** This string is used as 'end of line'. Its value can be changed. */
	public static String endOfLine = "\n";

	private static TextReader reader = null;
	private static TextWriter writer = null;

	private static boolean reuseIO = false;

	public static boolean getReuseIO() {
		return reuseIO;
	}

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

	// wrapper functions

	public static Object fromString(String string) {
		try(StringReader in = new StringReader(string)) {
			return deserialize(in);
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static Object deserialize(File file) throws IOException {
		try(Reader in = new BufferedReader(new FileReader(file))) {
			return deserialize(in);
		}
	}

	public static String toString(Object element, boolean format) {
		try(StringWriter out = new StringWriter()) {
			serialize(out, element, format);
			return out.toString();
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void serialize(File file, Object element, boolean format) throws IOException {
		try(Writer out = new BufferedWriter(new FileWriter(file))) {
			serialize(out, element, format);
		}
	}

}
