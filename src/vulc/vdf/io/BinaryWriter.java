package vulc.vdf.io;

import static vulc.vdf.io.BinaryCodes.*;

import java.io.DataOutputStream;
import java.io.IOException;

import vulc.vdf.ObjectElement;

class BinaryWriter extends VDFWriter {

	protected BinaryWriter() {
		add((value, out) -> out.writeBoolean((boolean) value), BOOLEAN);
		add((value, out) -> out.writeByte((byte) value), BYTE);
		add((value, out) -> out.writeShort((short) value), SHORT);
		add((value, out) -> out.writeInt((int) value), INT);
		add((value, out) -> out.writeLong((long) value), LONG);
		add((value, out) -> out.writeFloat((float) value), FLOAT);
		add((value, out) -> out.writeDouble((double) value), DOUBLE);
		add((value, out) -> out.writeChar((char) value), CHAR);
		add((value, out) -> out.writeUTF((String) value), STRING);

		add(this::writeBooleanArray, BOOLEAN_A);
		add(this::writeByteArray, BYTE_A);
		add(this::writeShortArray, SHORT_A);
		add(this::writeIntArray, INT_A);
		add(this::writeLongArray, LONG_A);
		add(this::writeFloatArray, FLOAT_A);
		add(this::writeDoubleArray, DOUBLE_A);
		add(this::writeCharArray, CHAR_A);
		add(this::writeStringArray, STRING_A);

		add(this::writeObject, OBJECT);
		add(this::writeObjectArray, OBJECT_A);
	}

	protected void serializeObject(DataOutputStream out, ObjectElement obj) throws IOException {
		for(String name : obj.keySet()) {
			Object value = obj.getValue(name);

			byte code = CODES.get(value.getClass());

			out.writeByte(code);						// write code
			out.writeUTF(name);							// write name

			SERIALIZERS[code].serialize(value, out);	// serialize
		}
		out.writeByte(-1);								// write end mark
	}

	// write

	private void writeBooleanArray(Object value, DataOutputStream out) throws IOException {
		boolean[] array = (boolean[]) value;

		out.writeInt(array.length);
		for(int i = 0; i < array.length; i++) {
			out.writeBoolean(array[i]);
		}
	}

	private void writeByteArray(Object value, DataOutputStream out) throws IOException {
		byte[] array = (byte[]) value;

		out.writeInt(array.length);
		for(int i = 0; i < array.length; i++) {
			out.writeByte(array[i]);
		}
	}

	private void writeShortArray(Object value, DataOutputStream out) throws IOException {
		short[] array = (short[]) value;

		out.writeInt(array.length);
		for(int i = 0; i < array.length; i++) {
			out.writeShort(array[i]);
		}
	}

	private void writeIntArray(Object value, DataOutputStream out) throws IOException {
		int[] array = (int[]) value;

		out.writeInt(array.length);
		for(int i = 0; i < array.length; i++) {
			out.writeInt(array[i]);
		}
	}

	private void writeLongArray(Object value, DataOutputStream out) throws IOException {
		long[] array = (long[]) value;

		out.writeInt(array.length);
		for(int i = 0; i < array.length; i++) {
			out.writeLong(array[i]);
		}
	}

	private void writeFloatArray(Object value, DataOutputStream out) throws IOException {
		float[] array = (float[]) value;

		out.writeInt(array.length);
		for(int i = 0; i < array.length; i++) {
			out.writeFloat(array[i]);
		}
	}

	private void writeDoubleArray(Object value, DataOutputStream out) throws IOException {
		double[] array = (double[]) value;

		out.writeInt(array.length);
		for(int i = 0; i < array.length; i++) {
			out.writeDouble(array[i]);
		}
	}

	private void writeCharArray(Object value, DataOutputStream out) throws IOException {
		char[] array = (char[]) value;

		out.writeInt(array.length);
		for(int i = 0; i < array.length; i++) {
			out.writeChar(array[i]);
		}
	}

	private void writeStringArray(Object value, DataOutputStream out) throws IOException {
		String[] array = (String[]) value;

		out.writeInt(array.length);
		for(int i = 0; i < array.length; i++) {
			out.writeUTF(array[i]);
		}
	}

	private void writeObject(Object value, DataOutputStream out) throws IOException {
		serializeObject(out, (ObjectElement) value);
	}

	private void writeObjectArray(Object value, DataOutputStream out) throws IOException {
		ObjectElement[] array = (ObjectElement[]) value;

		out.writeInt(array.length);
		for(int i = 0; i < array.length; i++) {
			serializeObject(out, array[i]);
		}
	}

}
