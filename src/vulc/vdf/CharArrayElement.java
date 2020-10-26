package vulc.vdf;

public class CharArrayElement extends Element {

	public final char[] value;

	public CharArrayElement(char[] value) {
		this.value = value;
	}

	public Object get() {
		return value;
	}

}
