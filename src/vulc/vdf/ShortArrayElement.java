package vulc.vdf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

class ShortArrayElement extends Element {

	protected short[] value;

	public ShortArrayElement() {
	}

	public ShortArrayElement(short[] value) {
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
