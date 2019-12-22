package vulc.vdf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

class ByteArrayTag extends Tag<byte[]> {

	private byte[] value;

	public ByteArrayTag() {
	}

	public ByteArrayTag(byte[] value) {
		this.value = value;
	}

	protected byte[] get() {
		return value;
	}

	public void serialize(DataOutputStream out) throws IOException {
		out.writeInt(value.length);
		for(int i = 0; i < value.length; i++) {
			out.writeByte(value[i]);
		}
	}

	public void deserialize(DataInputStream in) throws IOException {
		value = new byte[in.readInt()];
		for(int i = 0; i < value.length; i++) {
			value[i] = in.readByte();
		}
	}

}
