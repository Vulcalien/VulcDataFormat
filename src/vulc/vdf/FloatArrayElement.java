package vulc.vdf;

public class FloatArrayElement extends Element {

	public final float[] value;

	public FloatArrayElement(float[] value) {
		this.value = value;
	}

	public Object get() {
		return value;
	}

}
