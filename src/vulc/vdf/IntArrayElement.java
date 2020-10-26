package vulc.vdf;

public class IntArrayElement extends Element {

	public final int[] value;

	public IntArrayElement(int[] value) {
		this.value = value;
	}

	public Object get() {
		return value;
	}

}
