package vulc.vdf;

class IntArrayElement extends Element {

	protected int[] value;

	public IntArrayElement() {
	}

	public IntArrayElement(int[] value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

}
