package vulc.vdf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

class BooleanTag extends Tag<Boolean> {

	protected boolean value;

	public BooleanTag() {
	}

	public BooleanTag(boolean value) {
		this.value = value;
	}

	public Boolean get() {
		return value;
	}

	public void serialize(DataOutputStream out) throws IOException {
		out.writeBoolean(value);
	}

	public void deserialize(DataInputStream in) throws IOException {
		this.value = in.readBoolean();
	}

}
