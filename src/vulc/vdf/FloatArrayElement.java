package vulc.vdf;

class FloatArrayElement extends Element {

	protected float[] value;

	public FloatArrayElement() {
	}

	public FloatArrayElement(float[] value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

}
