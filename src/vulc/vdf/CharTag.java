package vulc.vdf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

class CharTag extends Tag<Character> {

	protected char value;

	public CharTag() {
	}

	public CharTag(char value) {
		this.value = value;
	}

	protected Character get() {
		return value;
	}

	public void serialize(DataOutputStream out) throws IOException {
		out.writeChar(value);
	}

	public void deserialize(DataInputStream in) throws IOException {
		this.value = in.readChar();
	}

}
