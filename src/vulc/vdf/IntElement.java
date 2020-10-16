package vulc.vdf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

class IntElement extends Element {

	protected int value;

	public IntElement() {
	}

	public IntElement(int value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

	public void serialize(DataOutputStream out) throws IOException {
		out.writeInt(value);
	}

	public void deserialize(DataInputStream in) throws IOException {
		this.value = in.readInt();
	}

}