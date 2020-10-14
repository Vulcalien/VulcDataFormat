package vulc.vdf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

class IntTag extends Tag {

	protected int value;

	public IntTag() {
	}

	public IntTag(int value) {
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
