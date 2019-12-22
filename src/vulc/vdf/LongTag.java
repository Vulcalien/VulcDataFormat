package vulc.vdf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

class LongTag extends Tag<Long> {

	private long value;

	public LongTag() {
	}

	public LongTag(long value) {
		this.value = value;
	}

	protected Long get() {
		return value;
	}

	public void serialize(DataOutputStream out) throws IOException {
		out.writeLong(value);
	}

	public void deserialize(DataInputStream in) throws IOException {
		this.value = in.readLong();
	}

}
