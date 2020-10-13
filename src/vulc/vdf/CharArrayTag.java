package vulc.vdf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

class CharArrayTag extends Tag<char[]> {

	protected char[] value;

	public CharArrayTag() {
	}

	public CharArrayTag(char[] value) {
		this.value = value;
	}

	protected char[] get() {
		return value;
	}

	public void serialize(DataOutputStream out) throws IOException {
		out.writeInt(value.length);
		for(int i = 0; i < value.length; i++) {
			out.writeChar(value[i]);
		}
	}

	public void deserialize(DataInputStream in) throws IOException {
		value = new char[in.readInt()];
		for(int i = 0; i < value.length; i++) {
			value[i] = in.readChar();
		}
	}

}
