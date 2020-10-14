package vulc.vdf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

class ShortTag extends Tag {

	protected short value;

	public ShortTag() {
	}

	public ShortTag(short value) {
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
