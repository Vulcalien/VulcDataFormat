package vulc.vdf;

class ShortElement extends Element {

	protected short value;

	public ShortElement() {
	}

	public ShortElement(short value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

}
