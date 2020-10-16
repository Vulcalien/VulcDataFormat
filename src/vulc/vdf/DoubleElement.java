package vulc.vdf;

class DoubleElement extends Element {

	protected double value;

	public DoubleElement() {
	}

	public DoubleElement(double value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

}
