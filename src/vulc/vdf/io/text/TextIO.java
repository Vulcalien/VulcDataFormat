package vulc.vdf.io.text;

import vulc.vdf.VDFList;
import vulc.vdf.VDFObject;

public abstract class TextIO {

	// TODO somehow delete these after use
	private static TextReader reader = new TextReader();
	private static TextWriter writer = new TextWriter();

	// TODO test all these functions

	public static VDFObject deserialize(String in, VDFObject obj) {
		reader.in = new StringAnalyzer(in);
		return reader.deserializeObject(obj);
	}

	public static VDFList deserialize(String in, VDFList obj) {
		reader.in = new StringAnalyzer(in);
		return reader.deserializeList(obj);
	}

	public static String stringify(VDFObject obj, boolean format) {
		StringBuilder builder = new StringBuilder();
		writer.out = builder;

		writer.serializeObject(obj, format, 0);
		if(format) builder.append(TextTokens.LF);

		return builder.toString();
	}

	public static String stringify(VDFList obj, boolean format) {
		StringBuilder builder = new StringBuilder();
		writer.out = builder;

		writer.serializeList(obj, format, 0);
		if(format) builder.append(TextTokens.LF);

		return builder.toString();
	}

}
