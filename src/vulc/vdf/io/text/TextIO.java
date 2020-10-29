package vulc.vdf.io.text;

import vulc.vdf.VDFObject;

public abstract class TextIO {

	private static final TextReader READER = new TextReader();
	private static final TextWriter WRITER = new TextWriter();

	public static VDFObject deserialize(String in, VDFObject obj) {
		return READER.deserializeObject(in, obj);
	}

	public static String stringify(VDFObject obj, boolean format) {
		StringBuilder builder = new StringBuilder();

		WRITER.serializeObject(builder, obj, format, 0);
		if(format) builder.append(TextTokens.LF);

		return builder.toString();
	}

}
