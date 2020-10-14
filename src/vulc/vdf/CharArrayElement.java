package vulc.vdf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

class CharArrayElement extends Element {

	protected char[] value;

	public CharArrayElement() {
	}

	public CharArrayElement(char[] value) {
		this.value = value;
	}

	protected Object get() {
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
