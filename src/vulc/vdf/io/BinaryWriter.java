package vulc.vdf.io;

import static vulc.vdf.io.BinaryCodes.*;

import java.io.DataOutputStream;
import java.io.IOException;

import vulc.vdf.ObjectElement;

abstract class BinaryWriter {

	private static final Serializer[] SERIALIZERS = new Serializer[TYPES];

	static {
		add((value, out) -> out.writeBoolean((boolean) value), BOOLEAN);
		add((value, out) -> out.writeByte((byte) value), BYTE);
		add((value, out) -> out.writeShort((short) value), SHORT);
		add((value, out) -> out.writeInt((int) value), INT);
		add((value, out) -> out.writeLong((long) value), LONG);
		add((value, out) -> out.writeFloat((float) value), FLOAT);
		add((value, out) -> out.writeDouble((double) value), DOUBLE);
		add((value, out) -> out.writeChar((char) value), CHAR);
		add((value, out) -> out.writeUTF((String) value), STRING);

		add(BinaryWriter::writeBooleanArray, BOOLEAN_A);
		add(BinaryWriter::writeByteArray, BYTE_A);
		add(BinaryWriter::writeShortArray, SHORT_A);
		add(BinaryWriter::writeIntArray, INT_A);
		add(BinaryWriter::writeLongArray, LONG_A);
		add(BinaryWriter::writeFloatArray, FLOAT_A);
		add(BinaryWriter::writeDoubleArray, DOUBLE_A);
		add(BinaryWriter::writeCharArray, CHAR_A);
		add(BinaryWriter::writeStringArray, STRING_A);

		add(BinaryWriter::writeObject, OBJECT);
		add(BinaryWriter::writeObjectArray, OBJECT_A);
	}

	private static void add(Serializer serializer, byte code) {
		SERIALIZERS[code] = serializer;
	}

	protected static void serializeObject(DataOutputStream out, ObjectElement obj) throws IOException {
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

	private static void writeBooleanArray(Object value, DataOutputStream out) throws IOException {
		boolean[] array = (boolean[]) value;

		out.writeInt(array.length);
		for(int i = 0; i < array.length; i++) {
			out.writeBoolean(array[i]);
		}
	}

	private static void writeByteArray(Object value, DataOutputStream out) throws IOException {
		byte[] array = (byte[]) value;

		out.writeInt(array.length);
		for(int i = 0; i < array.length; i++) {
			out.writeByte(array[i]);
		}
	}

	private static void writeShortArray(Object value, DataOutputStream out) throws IOException {
		short[] array = (short[]) value;

		out.writeInt(array.length);
		for(int i = 0; i < array.length; i++) {
			out.writeShort(array[i]);
		}
	}

	private static void writeIntArray(Object value, DataOutputStream out) throws IOException {
		int[] array = (int[]) value;

		out.writeInt(array.length);
		for(int i = 0; i < array.length; i++) {
			out.writeInt(array[i]);
		}
	}

	private static void writeLongArray(Object value, DataOutputStream out) throws IOException {
		long[] array = (long[]) value;

		out.writeInt(array.length);
		for(int i = 0; i < array.length; i++) {
			out.writeLong(array[i]);
		}
	}

	private static void writeFloatArray(Object value, DataOutputStream out) throws IOException {
		float[] array = (float[]) value;

		out.writeInt(array.length);
		for(int i = 0; i < array.length; i++) {
			out.writeFloat(array[i]);
		}
	}

	private static void writeDoubleArray(Object value, DataOutputStream out) throws IOException {
		double[] array = (double[]) value;

		out.writeInt(array.length);
		for(int i = 0; i < array.length; i++) {
			out.writeDouble(array[i]);
		}
	}

	private static void writeCharArray(Object value, DataOutputStream out) throws IOException {
		char[] array = (char[]) value;

		out.writeInt(array.length);
		for(int i = 0; i < array.length; i++) {
			out.writeChar(array[i]);
		}
	}

	private static void writeStringArray(Object value, DataOutputStream out) throws IOException {
		String[] array = (String[]) value;

		out.writeInt(array.length);
		for(int i = 0; i < array.length; i++) {
			out.writeUTF(array[i]);
		}
	}

	private static void writeObject(Object value, DataOutputStream out) throws IOException {
		serializeObject(out, (ObjectElement) value);
	}

	private static void writeObjectArray(Object value, DataOutputStream out) throws IOException {
		ObjectElement[] array = (ObjectElement[]) value;

		out.writeInt(array.length);
		for(int i = 0; i < array.length; i++) {
			serializeObject(out, array[i]);
		}
	}

}
