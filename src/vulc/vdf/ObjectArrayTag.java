package vulc.vdf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

class ObjectArrayTag extends Tag<ObjectTag[]> {

	protected ObjectTag[] value;

	public ObjectArrayTag() {
	}

	public ObjectArrayTag(ObjectTag[] value) {
		this.value = value;
	}

	protected ObjectTag[] get() {
		return value;
	}

	public void serialize(DataOutputStream out) throws IOException {
		out.writeInt(value.length);
		for(int i = 0; i < value.length; i++) {
			value[i].serialize(out);
		}
	}

	public void deserialize(DataInputStream in) throws IOException {
		value = new ObjectTag[in.readInt()];
		for(int i = 0; i < value.length; i++) {
			value[i] = new ObjectTag();
			value[i].deserialize(in);
		}
	}

}
