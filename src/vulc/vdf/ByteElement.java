package vulc.vdf;

public class ByteElement extends Element {

	public final byte value;

	public ByteElement(byte value) {
		this.value = value;
	}

	public Object get() {
		return value;
	}

}
