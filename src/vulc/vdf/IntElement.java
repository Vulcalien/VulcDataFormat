package vulc.vdf;

class IntElement extends Element {

	protected int value;

	public IntElement() {
	}

	public IntElement(int value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

}
