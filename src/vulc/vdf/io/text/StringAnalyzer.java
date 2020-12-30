package vulc.vdf.io.text;

import static vulc.vdf.io.text.TextTokens.*;

import java.io.IOException;
import java.io.Reader;

import vulc.vdf.io.VDFCodes;

class StringAnalyzer {

	private final Reader reader;

	private final StringBuilder buffer = new StringBuilder();
	private int pos = 0;
	private int line = 1;

	private boolean avoidComments = true;

	protected StringAnalyzer(Reader reader) {
		this.reader = reader;
	}

	// reads and consumes the next character from buffer
	protected char read() throws IOException {
		checkCanRead();

		int initialPos = pos;
		char c = buffer.charAt(pos++);

		if(c == LF) {
			line++;
		} else if(avoidComments && c == '/') {
			checkCanRead();
			char next = buffer.charAt(pos++);

			char replacingChar = 0;
			boolean foundComment = true;
			if(next == '/') {
				replacingChar = LF;
				skipComment(false);
			} else if(next == '*') {
				replacingChar = WHITESPACE;
				skipComment(true);
			} else {
				foundComment = false;
				unread();
			}

			if(foundComment) {
				// delete the comment stored in the buffer
				pos = initialPos;
				buffer.setLength(pos);

				buffer.append(replacingChar);
				c = replacingChar;
				pos++;
			}
		}
		return c;
	}

	private void unread() {
		pos--;
		if(buffer.charAt(pos) == LF) line--;
	}

	protected void releaseBuffer() {
		pos = 0;
		buffer.setLength(0);
	}

	private void checkCanRead() throws IOException {
		if(pos >= buffer.length()) {
			int c = reader.read();
			buffer.append((char) c);
		}
	}

	protected boolean readIf(char c) throws IOException {
		if(read() == c) {
			return true;
		}
		unread();
		return false;
	}

	protected String readUntil(char... until) throws IOException {
		StringBuilder result = new StringBuilder();

		read_loop:
		while(true) {
			char c = read();
			for(char u : until) {
				if(u == c) {
					unread();
					break read_loop;
				}
			}
			if(c == EOF) throw new VDFParseException("Cannot read more characters", line);
			result.append(c);
		}
		return result.toString();
	}

	protected byte readTopLevelType() throws IOException {
		char next = read();
		unread();
		if(next == OPEN_OBJECT) return VDFCodes.OBJECT;
		if(next == OPEN_LIST) return VDFCodes.LIST;
		return readType();
	}

	protected byte readType() throws IOException {
		String type = readUntil(WHITESPACE, TAB, CR, LF,
		                        CHAR_QUOTE, STRING_QUOTE).toLowerCase();

		Byte code = TextCodes.TAG_CODES.get(type);
		if(code == null) throw new VDFParseException("type '" + type + "' does not exist", line);
		return code;
	}

	protected void skipWhitespaces() throws IOException {
		while(true) {
			char c = read();
			if(c != WHITESPACE && c != TAB
			   && c != CR && c != LF) {
				unread();
				break;
			}
		}
	}

	private void skipComment(boolean multiline) throws IOException {
		avoidComments = false;

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
				if(c == LF) {
					break;
				}
			}
		}
		avoidComments = true;
	}

	protected void checkToken(char token) throws IOException {
		skipWhitespaces();
		if(read() != token) missingToken(token);
	}

	protected void missingToken(char token) {
		throw new VDFParseException("token '" + token + "' expected", line);
	}

	protected <T> T readNumber(char[] endOfValue, NumberReader<T> reader) throws IOException {
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

	protected char readChar() throws IOException {
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

	protected String readString() throws IOException {
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
