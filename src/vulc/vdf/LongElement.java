package vulc.vdf;

class LongElement extends Element {

	protected long value;

	public LongElement() {
	}

	public LongElement(long value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

}
