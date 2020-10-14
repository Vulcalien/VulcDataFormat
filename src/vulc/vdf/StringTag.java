package vulc.vdf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

class StringTag extends Tag {

	protected String value;

	public StringTag() {
	}

	public StringTag(String value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

	public void serialize(DataOutputStream out) throws IOException {
		out.writeUTF(value);
	}

	public void deserialize(DataInputStream in) throws IOException {
		this.value = in.readUTF();
	}

}
