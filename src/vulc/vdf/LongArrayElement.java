package vulc.vdf;

class LongArrayElement extends Element {

	protected final long[] value;

	public LongArrayElement(long[] value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

}
