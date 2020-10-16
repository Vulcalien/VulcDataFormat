package vulc.vdf;

class LongArrayElement extends Element {

	protected long[] value;

	public LongArrayElement() {
	}

	public LongArrayElement(long[] value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

}
