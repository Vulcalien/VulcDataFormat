package vulc.vdf;

class ObjectArrayElement extends Element {

	protected ObjectElement[] value;

	public ObjectArrayElement() {
	}

	public ObjectArrayElement(ObjectElement[] value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

}
