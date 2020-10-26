package vulc.vdf;

public class ShortElement extends Element {

	protected final short value;

	public ShortElement(short value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

}
