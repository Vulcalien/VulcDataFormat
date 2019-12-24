package vulc.vdf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class StringArrayTag extends Tag<String[]> {

	private String[] value;

	public StringArrayTag() {
	}

	public StringArrayTag(String[] value) {
		this.value = value;
	}

	protected String[] get() {
		return value;
	}

	public void serialize(DataOutputStream out) throws IOException {
		out.writeInt(value.length);
		for(int i = 0; i < value.length; i++) {
			out.writeUTF(value[i]);
		}
	}

	public void deserialize(DataInputStream in) throws IOException {
		value = new String[in.readInt()];
		for(int i = 0; i < value.length; i++) {
			value[i] = in.readUTF();
		}
	}

}
