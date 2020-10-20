package vulc.vdf;

class StringElement extends Element {

	protected final String value;

	public StringElement(String value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

}
