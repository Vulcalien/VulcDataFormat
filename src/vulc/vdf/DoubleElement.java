package vulc.vdf;

public class DoubleElement extends Element {

	public final double value;

	public DoubleElement(double value) {
		this.value = value;
	}

	public Object get() {
		return value;
	}

}
