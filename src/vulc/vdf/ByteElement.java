package vulc.vdf;

public class ByteElement extends Element {

	protected final byte value;

	public ByteElement(byte value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

}
