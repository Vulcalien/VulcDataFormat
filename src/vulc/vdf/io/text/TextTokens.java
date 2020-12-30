package vulc.vdf.io.text;

final class TextTokens {

	private TextTokens() {
	}

	protected static final char OPEN_OBJECT = '{';
	protected static final char CLOSE_OBJECT = '}';

	protected static final char OPEN_LIST = '[';
	protected static final char CLOSE_LIST = ']';

	protected static final char OPEN_ARRAY = '[';
	protected static final char CLOSE_ARRAY = ']';

	protected static final char STRING_QUOTE = '"';
	protected static final char CHAR_QUOTE = '\'';

	protected static final char SEPARATOR = ',';
	protected static final char ASSIGN = ':';

	protected static final char WHITESPACE = ' ';
	protected static final char TAB = '\t';

	protected static final char CR = '\r';
	protected static final char LF = '\n';

	protected static final char EOF = (char) -1;

}
