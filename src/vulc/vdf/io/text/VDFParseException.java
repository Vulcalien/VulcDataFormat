package vulc.vdf.io.text;

public class VDFParseException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public VDFParseException(String arg0, int line) {
		super(arg0 + " <line " + line + ">");
	}

}
