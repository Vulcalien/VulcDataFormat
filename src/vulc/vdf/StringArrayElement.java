package vulc.vdf;

public class StringArrayElement extends Element {

	public final String[] value;

	public StringArrayElement(String[] value) {
		this.value = value;
	}

	public Object get() {
		return value;
	}

}
