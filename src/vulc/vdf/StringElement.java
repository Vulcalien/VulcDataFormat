package vulc.vdf;

public class StringElement extends Element {

	public final String value;

	public StringElement(String value) {
		this.value = value;
	}

	public Object get() {
		return value;
	}

}
