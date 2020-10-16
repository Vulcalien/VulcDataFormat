package vulc.vdf;

class FloatElement extends Element {

	protected float value;

	public FloatElement() {
	}

	public FloatElement(float value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

}
