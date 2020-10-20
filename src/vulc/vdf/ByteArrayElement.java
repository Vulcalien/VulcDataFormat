package vulc.vdf;

class ByteArrayElement extends Element {

	protected final byte[] value;

	public ByteArrayElement(byte[] value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

}
