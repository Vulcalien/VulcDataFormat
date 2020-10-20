package vulc.vdf;

class StringArrayElement extends Element {

	protected final String[] value;

	public StringArrayElement(String[] value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

}
