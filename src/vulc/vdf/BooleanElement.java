package vulc.vdf;

class BooleanElement extends Element {

	protected boolean value;

	public BooleanElement() {
	}

	public BooleanElement(boolean value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

}
