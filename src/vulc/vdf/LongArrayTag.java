package vulc.vdf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

class LongArrayTag extends Tag {

	protected long[] value;

	public LongArrayTag() {
	}

	public LongArrayTag(long[] value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

	public void serialize(DataOutputStream out) throws IOException {
		out.writeInt(value.length);
		for(int i = 0; i < value.length; i++) {
			out.writeLong(value[i]);
		}
	}

	public void deserialize(DataInputStream in) throws IOException {
		value = new long[in.readInt()];
		for(int i = 0; i < value.length; i++) {
			value[i] = in.readLong();
		}
	}

}
