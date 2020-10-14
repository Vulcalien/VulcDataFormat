package vulc.vdf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

class FloatTag extends Tag {

	protected float value;

	public FloatTag() {
	}

	public FloatTag(float value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

	public void serialize(DataOutputStream out) throws IOException {
		out.writeFloat(value);
	}

	public void deserialize(DataInputStream in) throws IOException {
		this.value = in.readFloat();
	}

}
