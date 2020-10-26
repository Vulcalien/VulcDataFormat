package vulc.vdf;

public class ShortArrayElement extends Element {

	public final short[] value;

	public ShortArrayElement(short[] value) {
		this.value = value;
	}

	public Object get() {
		return value;
	}

}
