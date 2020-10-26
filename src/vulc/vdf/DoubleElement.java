package vulc.vdf;

public class DoubleElement extends Element {

	protected final double value;

	public DoubleElement(double value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

}
