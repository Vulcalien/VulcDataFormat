package vulc.vdf;

class StringArrayElement extends Element {

	protected String[] value;

	public StringArrayElement() {
	}

	public StringArrayElement(String[] value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

}
