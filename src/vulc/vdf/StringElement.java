package vulc.vdf;

class StringElement extends Element {

	protected String value;

	public StringElement() {
	}

	public StringElement(String value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

}
