package vulc.vdf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

class ShortArrayTag extends Tag {

	protected short[] value;

	public ShortArrayTag() {
	}

	public ShortArrayTag(short[] value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

	public void serialize(DataOutputStream out) throws IOException {
		out.writeInt(value.length);
		for(int i = 0; i < value.length; i++) {
			out.writeShort(value[i]);
		}
	}

	public void deserialize(DataInputStream in) throws IOException {
		value = new short[in.readInt()];
		for(int i = 0; i < value.length; i++) {
			value[i] = in.readShort();
		}
	}

}
