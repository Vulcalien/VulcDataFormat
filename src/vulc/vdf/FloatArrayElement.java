package vulc.vdf;

class FloatArrayElement extends Element {

	protected final float[] value;

	public FloatArrayElement(float[] value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

}
