package vulc.vdf;

public class FloatArrayElement extends Element {

	protected final float[] value;

	public FloatArrayElement(float[] value) {
		this.value = value;
	}

	protected Object get() {
		return value;
	}

}
