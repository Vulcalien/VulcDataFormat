package vulc.vdf;

class ByteElement extends Element {

	protected byte value;

	public ByteElement() {
	}

	public ByteElement(byte value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

}
