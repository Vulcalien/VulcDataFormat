package vulc.vdf;

public class IntElement extends Element {

	public final int value;

	public IntElement(int value) {
		this.value = value;
	}

	public Object get() {
		return value;
	}

}
