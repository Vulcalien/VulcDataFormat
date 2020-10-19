package vulc.vdf.io;

import static vulc.vdf.io.VDFCodes.*;

import java.io.DataInputStream;
import java.io.IOException;

import vulc.vdf.ObjectElement;

class BinaryReader {

	private final BinaryDeserializer[] deserializers = new BinaryDeserializer[VDFCodes.TYPES];

	private void add(BinaryDeserializer deserializer, byte code) {
		deserializers[code] = deserializer;
	}

	protected BinaryReader() {
		add((obj, name, in) -> obj.setBoolean(name, in.readBoolean()), BOOLEAN);
		add((obj, name, in) -> obj.setByte(name, in.readByte()), BYTE);
		add((obj, name, in) -> obj.setShort(name, in.readShort()), SHORT);
		add((obj, name, in) -> obj.setInt(name, in.readInt()), INT);
		add((obj, name, in) -> obj.setLong(name, in.readLong()), LONG);
		add((obj, name, in) -> obj.setFloat(name, in.readFloat()), FLOAT);
		add((obj, name, in) -> obj.setDouble(name, in.readDouble()), DOUBLE);
		add((obj, name, in) -> obj.setChar(name, in.readChar()), CHAR);
		add((obj, name, in) -> obj.setString(name, in.readUTF()), STRING);

		add(this::readBooleanArray, BOOLEAN_A);
		add(this::readByteArray, BYTE_A);
		add(this::readShortArray, SHORT_A);
		add(this::readIntArray, INT_A);
		add(this::readLongArray, LONG_A);
		add(this::readFloatArray, FLOAT_A);
		add(this::readDoubleArray, DOUBLE_A);
		add(this::readCharArray, CHAR_A);
		add(this::readStringArray, STRING_A);

		add(this::readObject, OBJECT);
		add(this::readObjectArray, OBJECT_A);
	}

	protected ObjectElement deserializeObject(DataInputStream in, ObjectElement obj) throws IOException {
		byte code;
		while((code = in.readByte()) != -1) {				// read code, until end mark (-1) is found
			String name = in.readUTF();						// read name

			deserializers[code].deserialize(obj, name, in);	// deserialize and add element to object
		}
		return obj;
	}

	// read

	private void readBooleanArray(ObjectElement obj, String name, DataInputStream in) throws IOException {
		boolean[] value = new boolean[in.readInt()];
		for(int i = 0; i < value.length; i++) {
			value[i] = in.readBoolean();
		}
		obj.setBooleanArray(name, value);
	}

	private void readByteArray(ObjectElement obj, String name, DataInputStream in) throws IOException {
		byte[] value = new byte[in.readInt()];
		for(int i = 0; i < value.length; i++) {
			value[i] = in.readByte();
		}
		obj.setByteArray(name, value);
	}

	private void readShortArray(ObjectElement obj, String name, DataInputStream in) throws IOException {
		short[] value = new short[in.readInt()];
		for(int i = 0; i < value.length; i++) {
			value[i] = in.readShort();
		}
		obj.setShortArray(name, value);
	}

	private void readIntArray(ObjectElement obj, String name, DataInputStream in) throws IOException {
		int[] value = new int[in.readInt()];
		for(int i = 0; i < value.length; i++) {
			value[i] = in.readInt();
		}
		obj.setIntArray(name, value);
	}

	private void readLongArray(ObjectElement obj, String name, DataInputStream in) throws IOException {
		long[] value = new long[in.readInt()];
		for(int i = 0; i < value.length; i++) {
			value[i] = in.readLong();
		}
		obj.setLongArray(name, value);
	}

	private void readFloatArray(ObjectElement obj, String name, DataInputStream in) throws IOException {
		float[] value = new float[in.readInt()];
		for(int i = 0; i < value.length; i++) {
			value[i] = in.readFloat();
		}
		obj.setFloatArray(name, value);
	}

	private void readDoubleArray(ObjectElement obj, String name, DataInputStream in) throws IOException {
		double[] value = new double[in.readInt()];
		for(int i = 0; i < value.length; i++) {
			value[i] = in.readDouble();
		}
		obj.setDoubleArray(name, value);
	}

	private void readCharArray(ObjectElement obj, String name, DataInputStream in) throws IOException {
		char[] value = new char[in.readInt()];
		for(int i = 0; i < value.length; i++) {
			value[i] = in.readChar();
		}
		obj.setCharArray(name, value);
	}

	private void readStringArray(ObjectElement obj, String name, DataInputStream in) throws IOException {
		String[] value = new String[in.readInt()];
		for(int i = 0; i < value.length; i++) {
			value[i] = in.readUTF();
		}
		obj.setStringArray(name, value);
	}

	private void readObject(ObjectElement obj, String name, DataInputStream in) throws IOException {
		obj.setObject(name, deserializeObject(in, new ObjectElement()));
	}

	private void readObjectArray(ObjectElement obj, String name, DataInputStream in) throws IOException {
		ObjectElement[] value = new ObjectElement[in.readInt()];
		for(int i = 0; i < value.length; i++) {
			value[i] = deserializeObject(in, new ObjectElement());
		}
		obj.setObjectArray(name, value);
	}

	private interface BinaryDeserializer {

		void deserialize(ObjectElement obj, String name, DataInputStream in) throws IOException;

	}

}
