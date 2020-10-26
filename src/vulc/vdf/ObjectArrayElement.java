package vulc.vdf;

public class ObjectArrayElement extends Element {

	protected final ObjectElement[] value;

	public ObjectArrayElement(ObjectElement[] value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

}
