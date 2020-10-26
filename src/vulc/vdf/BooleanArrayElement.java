package vulc.vdf;

public class BooleanArrayElement extends Element {

	protected final boolean[] value;

	public BooleanArrayElement(boolean[] value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

}
