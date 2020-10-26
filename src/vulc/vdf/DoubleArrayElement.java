package vulc.vdf;

public class DoubleArrayElement extends Element {

	protected final double[] value;

	public DoubleArrayElement(double[] value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

}
