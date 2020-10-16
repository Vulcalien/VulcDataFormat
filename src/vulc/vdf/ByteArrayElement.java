package vulc.vdf;

class ByteArrayElement extends Element {

	protected byte[] value;

	public ByteArrayElement() {
	}

	public ByteArrayElement(byte[] value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

}
