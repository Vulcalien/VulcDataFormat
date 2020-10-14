package vulc.vdf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

class FloatElement extends Element {

	protected float value;

	public FloatElement() {
	}

	public FloatElement(float value) {
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
