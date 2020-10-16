package vulc.vdf;

class DoubleArrayElement extends Element {

	protected double[] value;

	public DoubleArrayElement() {
	}

	public DoubleArrayElement(double[] value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

}
