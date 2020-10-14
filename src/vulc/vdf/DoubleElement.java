package vulc.vdf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

class DoubleElement extends Element {

	protected double value;

	public DoubleElement() {
	}

	public DoubleElement(double value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

	public void serialize(DataOutputStream out) throws IOException {
		out.writeDouble(value);
	}

	public void deserialize(DataInputStream in) throws IOException {
		this.value = in.readDouble();
	}

}
