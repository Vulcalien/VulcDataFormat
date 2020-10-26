package vulc.vdf;

public class LongArrayElement extends Element {

	public final long[] value;

	public LongArrayElement(long[] value) {
		this.value = value;
	}

	public Object get() {
		return value;
	}

}
