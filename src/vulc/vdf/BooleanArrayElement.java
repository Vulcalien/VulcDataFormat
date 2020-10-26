package vulc.vdf;

public class BooleanArrayElement extends Element {

	public final boolean[] value;

	public BooleanArrayElement(boolean[] value) {
		this.value = value;
	}

	public Object get() {
		return value;
	}

}
