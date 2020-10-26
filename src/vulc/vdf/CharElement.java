package vulc.vdf;

public class CharElement extends Element {

	public final char value;

	public CharElement(char value) {
		this.value = value;
	}

	public Object get() {
		return value;
	}

}
