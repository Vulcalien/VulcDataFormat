package vulc.vdf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

class LongElement extends Element {

	protected long value;

	public LongElement() {
	}

	public LongElement(long value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

	public void serialize(DataOutputStream out) throws IOException {
		out.writeLong(value);
	}

	public void deserialize(DataInputStream in) throws IOException {
		this.value = in.readLong();
	}

}
