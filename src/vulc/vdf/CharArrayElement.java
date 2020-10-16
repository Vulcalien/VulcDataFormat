package vulc.vdf;

class CharArrayElement extends Element {

	protected char[] value;

	public CharArrayElement() {
	}

	public CharArrayElement(char[] value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

}
