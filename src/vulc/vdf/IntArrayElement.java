package vulc.vdf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

class IntArrayElement extends Element {

	protected int[] value;

	public IntArrayElement() {
	}

	public IntArrayElement(int[] value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

	public void serialize(DataOutputStream out) throws IOException {
		out.writeInt(value.length);
		for(int i = 0; i < value.length; i++) {
			out.writeInt(value[i]);
		}
	}

	public void deserialize(DataInputStream in) throws IOException {
		value = new int[in.readInt()];
		for(int i = 0; i < value.length; i++) {
			value[i] = in.readInt();
		}
	}

}