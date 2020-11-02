package vulc.vdf.io.text;

import vulc.vdf.VDFList;
import vulc.vdf.VDFObject;

public abstract class TextIO {

	private static TextReader reader = null;
	private static TextWriter writer = null;

	private static boolean reuseIO = false;

	public static void setReuseIO(boolean flag) {
		reuseIO = flag;
		if(reuseIO) {
			reader = new TextReader();
			writer = new TextWriter();
		} else {
			reader = null;
			writer = null;
		}
	}

	// TODO test all these functions

	public static VDFObject deserialize(String in, VDFObject obj) {
		if(!reuseIO) reader = new TextReader();

		reader.in = new StringAnalyzer(in);
		reader.deserializeObject(obj);

		if(!reuseIO) reader = null;

		return obj;
	}

	public static VDFList deserialize(String in, VDFList list) {
		if(!reuseIO) reader = new TextReader();

		reader.in = new StringAnalyzer(in);
		reader.deserializeList(list);

		if(!reuseIO) reader = null;

		return list;
	}

	public static String stringify(VDFObject obj, boolean format) {
		if(!reuseIO) writer = new TextWriter();

		StringBuilder builder = new StringBuilder();
		writer.out = builder;
		writer.format = format;

		writer.serializeObject(obj);
		if(format) builder.append(TextTokens.LF);

		if(!reuseIO) writer = null;

		return builder.toString();
	}

	public static String stringify(VDFList obj, boolean format) {
		if(!reuseIO) writer = new TextWriter();

		StringBuilder builder = new StringBuilder();
		writer.out = builder;
		writer.format = format;

		writer.serializeList(obj);
		if(format) builder.append(TextTokens.LF);

		if(!reuseIO) writer = null;

		return builder.toString();
	}

}
