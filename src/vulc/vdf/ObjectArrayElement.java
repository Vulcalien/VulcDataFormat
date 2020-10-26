package vulc.vdf;

public class ObjectArrayElement extends Element {

	protected final VDFObject[] value;

	public ObjectArrayElement(VDFObject[] value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

}
