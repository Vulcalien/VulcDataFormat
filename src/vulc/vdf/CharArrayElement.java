package vulc.vdf;

public class CharArrayElement extends Element {

	protected final char[] value;

	public CharArrayElement(char[] value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

}
