package vulc.vdf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

class FloatArrayElement extends Element {

	protected float[] value;

	public FloatArrayElement() {
	}

	public FloatArrayElement(float[] value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

	public void serialize(DataOutputStream out) throws IOException {
		out.writeInt(value.length);
		for(int i = 0; i < value.length; i++) {
			out.writeFloat(value[i]);
		}
	}

	public void deserialize(DataInputStream in) throws IOException {
		value = new float[in.readInt()];
		for(int i = 0; i < value.length; i++) {
			value[i] = in.readFloat();
		}
	}

}
