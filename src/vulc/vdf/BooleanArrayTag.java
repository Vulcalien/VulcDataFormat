package vulc.vdf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

class BooleanArrayTag extends Tag<boolean[]> {

	private boolean[] value;

	public BooleanArrayTag() {
	}

	public BooleanArrayTag(boolean[] value) {
		this.value = value;
	}

	protected boolean[] get() {
		return value;
	}

	public void serialize(DataOutputStream out) throws IOException {
		out.writeInt(value.length);
		for(int i = 0; i < value.length; i++) {
			out.writeBoolean(value[i]);
		}
	}

	public void deserialize(DataInputStream in) throws IOException {
		value = new boolean[in.readInt()];
		for(int i = 0; i < value.length; i++) {
			value[i] = in.readBoolean();
		}
	}

}
