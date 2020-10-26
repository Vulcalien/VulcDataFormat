package vulc.vdf;

public class LongElement extends Element {

	protected final long value;

	public LongElement(long value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

}
