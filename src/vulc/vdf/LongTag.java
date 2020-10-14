package vulc.vdf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

class LongTag extends Tag {

	protected long value;

	public LongTag() {
	}

	public LongTag(long value) {
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
