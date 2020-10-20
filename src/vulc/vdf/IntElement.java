package vulc.vdf;

class IntElement extends Element {

	protected final int value;

	public IntElement(int value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

}
