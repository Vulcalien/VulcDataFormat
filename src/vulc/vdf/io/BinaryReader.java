package vulc.vdf.io;

import static vulc.vdf.io.BinaryCodes.*;

import java.io.DataInputStream;
import java.io.IOException;

import vulc.vdf.ObjectElement;

abstract class BinaryReader {

	private static final Deserializer[] DESERIALIZERS = new Deserializer[TYPES];

	static {
		add((obj, name, in) -> obj.setBoolean(name, in.readBoolean()), BOOLEAN);
		add((obj, name, in) -> obj.setByte(name, in.readByte()), BYTE);
		add((obj, name, in) -> obj.setShort(name, in.readShort()), SHORT);
		add((obj, name, in) -> obj.setInt(name, in.readInt()), INT);
		add((obj, name, in) -> obj.setLong(name, in.readLong()), LONG);
		add((obj, name, in) -> obj.setFloat(name, in.readFloat()), FLOAT);
		add((obj, name, in) -> obj.setDouble(name, in.readDouble()), DOUBLE);
		add((obj, name, in) -> obj.setChar(name, in.readChar()), CHAR);
		add((obj, name, in) -> obj.setString(name, in.readUTF()), STRING);

		add(BinaryReader::readBooleanArray, BOOLEAN_A);
		add(BinaryReader::readByteArray, BYTE_A);
		add(BinaryReader::readShortArray, SHORT_A);
		add(BinaryReader::readIntArray, INT_A);
		add(BinaryReader::readLongArray, LONG_A);
		add(BinaryReader::readFloatArray, FLOAT_A);
		add(BinaryReader::readDoubleArray, DOUBLE_A);
		add(BinaryReader::readCharArray, CHAR_A);
		add(BinaryReader::readStringArray, STRING_A);

		add(BinaryReader::readObject, OBJECT);
		add(BinaryReader::readObjectArray, OBJECT_A);
	}

	private static void add(Deserializer deserializer, byte code) {
		DESERIALIZERS[code] = deserializer;
	}

	protected static ObjectElement deserializeObject(DataInputStream in) throws IOException {
		ObjectElement obj = new ObjectElement();

		byte code;
		while((code = in.readByte()) != -1) {				// read code, until end mark (-1) is found
			String name = in.readUTF();						// read name

			DESERIALIZERS[code].deserialize(obj, name, in);	// deserialize and add element to object
		}
		return obj;
	}

	// read

	private static void readBooleanArray(ObjectElement obj, String name, DataInputStream in) throws IOException {
		boolean[] value = new boolean[in.readInt()];
		for(int i = 0; i < value.length; i++) {
			value[i] = in.readBoolean();
		}
		obj.setBooleanArray(name, value);
	}

	private static void readByteArray(ObjectElement obj, String name, DataInputStream in) throws IOException {
		byte[] value = new byte[in.readInt()];
		for(int i = 0; i < value.length; i++) {
			value[i] = in.readByte();
		}
		obj.setByteArray(name, value);
	}

	private static void readShortArray(ObjectElement obj, String name, DataInputStream in) throws IOException {
		short[] value = new short[in.readInt()];
		for(int i = 0; i < value.length; i++) {
			value[i] = in.readShort();
		}
		obj.setShortArray(name, value);
	}

	private static void readIntArray(ObjectElement obj, String name, DataInputStream in) throws IOException {
		int[] value = new int[in.readInt()];
		for(int i = 0; i < value.length; i++) {
			value[i] = in.readInt();
		}
		obj.setIntArray(name, value);
	}

	private static void readLongArray(ObjectElement obj, String name, DataInputStream in) throws IOException {
		long[] value = new long[in.readInt()];
		for(int i = 0; i < value.length; i++) {
			value[i] = in.readLong();
		}
		obj.setLongArray(name, value);
	}

	private static void readFloatArray(ObjectElement obj, String name, DataInputStream in) throws IOException {
		float[] value = new float[in.readInt()];
		for(int i = 0; i < value.length; i++) {
			value[i] = in.readFloat();
		}
		obj.setFloatArray(name, value);
	}

	private static void readDoubleArray(ObjectElement obj, String name, DataInputStream in) throws IOException {
		double[] value = new double[in.readInt()];
		for(int i = 0; i < value.length; i++) {
			value[i] = in.readDouble();
		}
		obj.setDoubleArray(name, value);
	}

	private static void readCharArray(ObjectElement obj, String name, DataInputStream in) throws IOException {
		char[] value = new char[in.readInt()];
		for(int i = 0; i < value.length; i++) {
			value[i] = in.readChar();
		}
		obj.setCharArray(name, value);
	}

	private static void readStringArray(ObjectElement obj, String name, DataInputStream in) throws IOException {
		String[] value = new String[in.readInt()];
		for(int i = 0; i < value.length; i++) {
			value[i] = in.readUTF();
		}
		obj.setStringArray(name, value);
	}

	private static void readObject(ObjectElement obj, String name, DataInputStream in) throws IOException {
		obj.setObject(name, deserializeObject(in));
	}

	private static void readObjectArray(ObjectElement obj, String name, DataInputStream in) throws IOException {
		ObjectElement[] value = new ObjectElement[in.readInt()];
		for(int i = 0; i < value.length; i++) {
			value[i] = deserializeObject(in);
		}
		obj.setObjectArray(name, value);
	}

}
