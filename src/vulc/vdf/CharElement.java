package vulc.vdf;

class CharElement extends Element {

	protected final char value;

	public CharElement(char value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

}
