package vulc.vdf.io.text;

import static vulc.vdf.io.text.TextTokens.*;

class StringAnalyzer {

	private final String string;

	private int mark = 0;
	protected int line = 1;

	private boolean avoidComments = true;

	protected StringAnalyzer(String string) {
		this.string = string;
	}

	protected char read() {
		checkCanRead(mark);
		char c = string.charAt(mark++);

		if(c == LF) {
			line++;
		} else if(avoidComments && c == '/') {
			char next = next();
			boolean foundComment = true;
			if(next == '/') skipComment(false);
			else if(next == '*') skipComment(true);
			else foundComment = false;

			// read the char that should be returned
			if(foundComment) c = read();
		}
		return c;
	}

	protected char next() {
		checkCanRead(mark);
		return string.charAt(mark);
	}

	protected void unread() {
		mark--;
		if(next() == LF) line--;
	}

	private void checkCanRead(int i) {
		if(i >= string.length()) throw new VDFParseException("End of string", line);
	}

	protected boolean readIf(char c) {
		if(next() == c) {
			read();
			return true;
		}
		return false;
	}

	protected String readUntil(char... until) {
		StringBuilder result = new StringBuilder();

		read_for:
		for(char c = read(); true; c = read()) {
			for(char u : until) {
				if(u == c) break read_for;
			}

			result.append(c);
		}
		unread();

		return result.toString();
	}

	protected void skipWhitespaces() {
		for(char c = read(); true; c = read()) {
			if(c != WHITESPACE && c != TAB
			   && c != CR && c != LF) break;
		}
		unread();
	}

	private void skipComment(boolean multiline) {
		avoidComments = false;

		// consume second character of the comment, (// -> /), (/* -> *)
		read();

		if(multiline) {
			boolean maybeClose = false;
			for(char c = read(); true; c = read()) {
				if(c == '*') {
					maybeClose = true; // a '*' was found: next iteration check if there is a '/'
				} else if(maybeClose) {
					if(c == '/') break; // close the comment
					else maybeClose = false; // the previous '*' was not meant to close the comment
				}
			}
		} else {
			for(char c = read(); true; c = read()) {
				if(c == LF) break;
			}
		}
		avoidComments = true;
	}

	protected void checkToken(char token) {
		skipWhitespaces();
		if(read() != token) missingToken(token);
	}

	protected void missingToken(char token) {
		throw new VDFParseException("token '" + token + "' expected", line);
	}

	protected <T> T readNumber(Class<T> type, char[] endOfValue, NumberReader<T> reader) {
		String string = readUntil(endOfValue);

		int radix = 10;
		if(string.length() > 1 && string.charAt(0) == '0') {
			char c1 = string.charAt(1);
			if(c1 == 'b' || c1 == 'B') {
				radix = 2;
				string = string.substring(2);
			} else if(c1 == 'x' || c1 == 'X') {
				radix = 16;
				string = string.substring(2);
			} else {
				radix = 8;
				string = string.substring(1);
			}
		}
		return reader.read(string, radix);
	}

	protected char readChar() {
		StringBuilder result = new StringBuilder();

		checkToken(CHAR_QUOTE);
		avoidComments = false;
		while(true) {
			result.append(readUntil(CHAR_QUOTE, '\\'));

			char token = read();
			if(token == CHAR_QUOTE) {
				avoidComments = true;
				return unescapeChar(result.toString());
			} else {
				result.append('\\');
				result.append(read());
			}
		}
	}

	protected String readString() {
		StringBuilder result = new StringBuilder();

		checkToken(STRING_QUOTE);
		avoidComments = false;
		while(true) {
			result.append(readUntil(STRING_QUOTE, '\\'));

			char token = read();
			if(token == STRING_QUOTE) {
				avoidComments = true;
				return result.toString();
			} else {
				result.append(unescapeChar("\\" + read()));
			}
		}
	}

	private char unescapeChar(String strChar) {
		if(strChar.equals("\\\\")) return '\\';
		if(strChar.equals("\\t")) return '\t';
		if(strChar.equals("\\b")) return '\b';
		if(strChar.equals("\\n")) return '\n';
		if(strChar.equals("\\r")) return '\r';
		if(strChar.equals("\\f")) return '\f';
		if(strChar.equals("\\'")) return '\'';
		if(strChar.equals("\\\"")) return '\"';

		if(strChar.length() != 1) throw new VDFParseException("Unknown escape sequence "
		                                                      + "'\\" + strChar.charAt(1) + "'", line);
		return strChar.charAt(0);
	}

	protected interface NumberReader<T> {

		T read(String value, int radix);

	}

}
