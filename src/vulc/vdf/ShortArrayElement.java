package vulc.vdf;

class ShortArrayElement extends Element {

	protected short[] value;

	public ShortArrayElement() {
	}

	public ShortArrayElement(short[] value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

}
