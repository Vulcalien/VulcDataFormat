package vulc.vdf;

class BooleanArrayElement extends Element {

	protected boolean[] value;

	public BooleanArrayElement() {
	}

	public BooleanArrayElement(boolean[] value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

}
