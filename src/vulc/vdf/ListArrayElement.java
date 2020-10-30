package vulc.vdf;

public class ListArrayElement extends Element {

	public final VDFList[] value;

	public ListArrayElement(VDFList[] value) {
		this.value = value;
	}

	public Object get() {
		return value;
	}

}
