package vulc.vdf;

public class ShortArrayElement extends Element {

	protected final short[] value;

	public ShortArrayElement(short[] value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

}
