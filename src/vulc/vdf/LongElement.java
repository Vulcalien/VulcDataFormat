package vulc.vdf;

public class LongElement extends Element {

	public final long value;

	public LongElement(long value) {
		this.value = value;
	}

	public Object get() {
		return value;
	}

}
