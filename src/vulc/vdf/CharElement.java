package vulc.vdf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

class CharElement extends Element {

	protected char value;

	public CharElement() {
	}

	public CharElement(char value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

	public void serialize(DataOutputStream out) throws IOException {
		out.writeChar(value);
	}

	public void deserialize(DataInputStream in) throws IOException {
		this.value = in.readChar();
	}

}
