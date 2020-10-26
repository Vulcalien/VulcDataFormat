package vulc.vdf;

public class ByteArrayElement extends Element {

	public final byte[] value;

	public ByteArrayElement(byte[] value) {
		this.value = value;
	}

	public Object get() {
		return value;
	}

}
