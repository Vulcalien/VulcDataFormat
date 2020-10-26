package vulc.vdf;

public class ObjectArrayElement extends Element {

	public final VDFObject[] value;

	public ObjectArrayElement(VDFObject[] value) {
		this.value = value;
	}

	public Object get() {
		return value;
	}

}
