package vulc.vdf;

class BooleanElement extends Element {

	protected final boolean value;

	public BooleanElement(boolean value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

}
