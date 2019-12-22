package vulc.vdf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

class ByteTag extends Tag<Byte> {

	private byte value;

	public ByteTag() {
	}

	public ByteTag(byte value) {
		this.value = value;
	}

	protected Byte get() {
		return value;
	}

	public void serialize(DataOutputStream out) throws IOException {
		out.writeByte(value);
	}

	public void deserialize(DataInputStream in) throws IOException {
		this.value = in.readByte();
	}

}
