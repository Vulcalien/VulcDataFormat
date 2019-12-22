package vulc.vdf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

class DoubleTag extends Tag<Double> {

	private double value;

	public DoubleTag() {
	}

	public DoubleTag(double value) {
		this.value = value;
	}

	protected Double get() {
		return value;
	}

	public void serialize(DataOutputStream out) throws IOException {
		out.writeDouble(value);
	}

	public void deserialize(DataInputStream in) throws IOException {
		this.value = in.readDouble();
	}

}
