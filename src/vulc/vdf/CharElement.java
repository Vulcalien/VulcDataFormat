package vulc.vdf;

class CharElement extends Element {

	protected char value;

	public CharElement() {
	}

	public CharElement(char value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

}
