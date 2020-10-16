package vulc.vdf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

class DoubleArrayElement extends Element {

	protected double[] value;

	public DoubleArrayElement() {
	}

	public DoubleArrayElement(double[] value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

	public void serialize(DataOutputStream out) throws IOException {
		out.writeInt(value.length);
		for(int i = 0; i < value.length; i++) {
			out.writeDouble(value[i]);
		}
	}

	public void deserialize(DataInputStream in) throws IOException {
		value = new double[in.readInt()];
		for(int i = 0; i < value.length; i++) {
			value[i] = in.readDouble();
		}
	}

}