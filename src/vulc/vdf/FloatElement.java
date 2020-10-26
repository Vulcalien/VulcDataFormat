package vulc.vdf;

public class FloatElement extends Element {

	public final float value;

	public FloatElement(float value) {
		this.value = value;
	}

	public Object get() {
		return value;
	}

}
