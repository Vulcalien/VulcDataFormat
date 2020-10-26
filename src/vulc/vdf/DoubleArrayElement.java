package vulc.vdf;

public class DoubleArrayElement extends Element {

	public final double[] value;

	public DoubleArrayElement(double[] value) {
		this.value = value;
	}

	public Object get() {
		return value;
	}

}
