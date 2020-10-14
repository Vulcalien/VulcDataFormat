package vulc.vdf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

class ShortElement extends Element {

	protected short value;

	public ShortElement() {
	}

	public ShortElement(short value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

	public void serialize(DataOutputStream out) throws IOException {
		out.writeShort(value);
	}

	public void deserialize(DataInputStream in) throws IOException {
		this.value = in.readShort();
	}

}
