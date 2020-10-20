package vulc.vdf;

class IntArrayElement extends Element {

	protected final int[] value;

	public IntArrayElement(int[] value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

}
