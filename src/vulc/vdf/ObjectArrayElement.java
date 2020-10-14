package vulc.vdf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

class ObjectArrayElement extends Element {

	protected ObjectElement[] value;

	public ObjectArrayElement() {
	}

	public ObjectArrayElement(ObjectElement[] value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

	public void serialize(DataOutputStream out) throws IOException {
		out.writeInt(value.length);
		for(int i = 0; i < value.length; i++) {
			value[i].serialize(out);
		}
	}

	public void deserialize(DataInputStream in) throws IOException {
		value = new ObjectElement[in.readInt()];
		for(int i = 0; i < value.length; i++) {
			value[i] = new ObjectElement();
			value[i].deserialize(in);
		}
	}

}
