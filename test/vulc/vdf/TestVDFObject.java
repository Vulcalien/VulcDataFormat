package vulc.vdf;

import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestVDFObject {

	private VDFObject obj = new VDFObject();

	@BeforeEach
	void setUp() throws Exception {
		obj.setBoolean("boolean", true);
		obj.setByte("byte", Byte.MAX_VALUE);
		obj.setShort("short", Short.MAX_VALUE);
		obj.setInt("int", Integer.MAX_VALUE);
		obj.setLong("long", Long.MAX_VALUE);
		obj.setFloat("float", Float.MAX_VALUE);
		obj.setDouble("double", Double.MAX_VALUE);
		obj.setChar("char", 'c');
		obj.setString("string", "test_str");

		// inner object
		obj.setObject("inner_obj", new VDFObject());
		obj.getObject("inner_obj").setInt("inner_int", Integer.MAX_VALUE);

		obj.setBooleanArray("boolean_a", new boolean[] {false, true});
		obj.setByteArray("byte_a", new byte[] {Byte.MIN_VALUE, Byte.MAX_VALUE});
		obj.setShortArray("short_a", new short[] {Short.MIN_VALUE, Short.MAX_VALUE});
		obj.setIntArray("int_a", new int[] {Integer.MIN_VALUE, Integer.MAX_VALUE});
		obj.setLongArray("long_a", new long[] {Long.MIN_VALUE, Long.MAX_VALUE});
		obj.setFloatArray("float_a", new float[] {Float.MIN_VALUE, Float.MAX_VALUE});
		obj.setDoubleArray("double_a", new double[] {Double.MIN_VALUE, Double.MAX_VALUE});
		obj.setCharArray("char_a", new char[] {'\n', ' ', '\b'});
		obj.setStringArray("str_a", new String[] {"test0", "test1"});

		obj.setObjectArray("obj_a", new VDFObject[] {new VDFObject(), new VDFObject()});
		obj.getObjectArray("obj_a")[1].setString("in_objarr_str", "in array's obj test");
	}

	@Test
	void binaryIO() throws IOException {
		byte[] buffer;

		try(ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			obj.serialize(out);
			buffer = out.toByteArray();
		}

		VDFObject deserializedObj = new VDFObject();
		try(ByteArrayInputStream in = new ByteArrayInputStream(buffer)) {
			deserializedObj.deserialize(in);
		}

		String error = compare(obj, deserializedObj);
		if(error != null) {
			System.out.println("==='binaryIO' FAIL LOG===");

			System.out.println(error);

			System.out.println("---original---");
			System.out.println(obj.toString(true));

			System.out.println("---deserialized---");
			System.out.println(deserializedObj.toString(true));

			System.out.println("==========");

			fail("Binary: serialize/deserialize do not work");
		}
	}

	@Test
	void textIO() {
		textIO(false);
		textIO(true);
	}

	private void textIO(boolean format) {
		String string = obj.toString(format);
		VDFObject parsedObj = new VDFObject().parse(string);

		String error = compare(obj, parsedObj);
		if(error == null && !string.equals(parsedObj.toString(format))) {
			error = "compare passed, but toString is different";
		}

		if(error != null) {
			System.out.println("==='textIO" + (format ? "" : " not") + " formatted' FAIL LOG===");

			System.out.println(error);

			System.out.println("---original---");
			System.out.println(string);

			System.out.println("---parsed---");
			System.out.println(parsedObj.toString(format));

			System.out.println("==========");

			fail("Text" + (format ? "" : " not") + " formatted: toString/parse do not work");
		}
	}

	// returns: error message or null if o0 equals o1
	private String compare(VDFObject o0, VDFObject o1) {
		for(String name : o0.keySet()) {
			Object v0, v1;
			try {
				v0 = o0.getElement(name).get();
				v1 = o1.getElement(name).get();
			} catch(NoSuchElementException e) {
				return "element " + name + " is missing";
			}

			if(v0.getClass().isArray() && v1.getClass().isArray()) {
				int len = Array.getLength(v0);

				if(Array.getLength(v1) != len) return name + ": array length is different";

				for(int i = 0; i < len; i++) {
					Object x0 = Array.get(v0, i);
					Object x1 = Array.get(v1, i);
					if(x0 instanceof VDFObject && x1 instanceof VDFObject) {
						String result = compare((VDFObject) x0, (VDFObject) x1);
						if(result != null) return name + "[" + i + "]: " + result;
					} else {
						if(!x0.equals(x1)) return name + "[" + i + "] is different";
					}
				}
			} else if(v0 instanceof VDFObject && v1 instanceof VDFObject) {
				String result = compare((VDFObject) v0, (VDFObject) v1);
				if(result != null) return name + ": " + result;
			} else {
				if(!v0.equals(v1)) return name + " is different";
			}
		}
		return null;
	}

}
