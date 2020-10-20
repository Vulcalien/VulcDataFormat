package vulc.vdf;

class FloatElement extends Element {

	protected final float value;

	public FloatElement(float value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

}
