package vulc.vdf;

public class BooleanElement extends Element {

	public final boolean value;

	public BooleanElement(boolean value) {
		this.value = value;
	}

	public Object get() {
		return value;
	}

}
