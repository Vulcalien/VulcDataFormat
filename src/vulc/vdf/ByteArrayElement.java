package vulc.vdf;

public class ByteArrayElement extends Element {

	protected final byte[] value;

	public ByteArrayElement(byte[] value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

}
