package vulc.vdf;

public class ShortElement extends Element {

	public final short value;

	public ShortElement(short value) {
		this.value = value;
	}

	public Object get() {
		return value;
	}

}
