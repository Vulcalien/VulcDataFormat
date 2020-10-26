package vulc.vdf.io.binary;

import static vulc.vdf.io.VDFCodes.*;

import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.Array;

import vulc.vdf.VDFObject;
import vulc.vdf.io.VDFCodes;

class BinaryReader {

	private final BinaryDeserializer[] deserializers = new BinaryDeserializer[VDFCodes.TYPES];

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
		add((obj, name, in) -> obj.setObject(name, deserializeObject(in, new VDFObject())), OBJECT);

		add(getArrayReader(boolean[].class,
		                   (array, i, in) -> array[i] = in.readBoolean(),
		                   (obj, name, array) -> obj.setBooleanArray(name, array)),
		    BOOLEAN_A);

		add(getArrayReader(byte[].class,
		                   (array, i, in) -> array[i] = in.readByte(),
		                   (obj, name, array) -> obj.setByteArray(name, array)),
		    BYTE_A);

		add(getArrayReader(short[].class,
		                   (array, i, in) -> array[i] = in.readShort(),
		                   (obj, name, array) -> obj.setShortArray(name, array)),
		    SHORT_A);

		add(getArrayReader(int[].class,
		                   (array, i, in) -> array[i] = in.readInt(),
		                   (obj, name, array) -> obj.setIntArray(name, array)),
		    INT_A);

		add(getArrayReader(long[].class,
		                   (array, i, in) -> array[i] = in.readLong(),
		                   (obj, name, array) -> obj.setLongArray(name, array)),
		    LONG_A);

		add(getArrayReader(float[].class,
		                   (array, i, in) -> array[i] = in.readFloat(),
		                   (obj, name, array) -> obj.setFloatArray(name, array)),
		    FLOAT_A);

		add(getArrayReader(double[].class,
		                   (array, i, in) -> array[i] = in.readDouble(),
		                   (obj, name, array) -> obj.setDoubleArray(name, array)),
		    DOUBLE_A);

		add(getArrayReader(char[].class,
		                   (array, i, in) -> array[i] = in.readChar(),
		                   (obj, name, array) -> obj.setCharArray(name, array)),
		    CHAR_A);

		add(getArrayReader(String[].class,
		                   (array, i, in) -> array[i] = in.readUTF(),
		                   (obj, name, array) -> obj.setStringArray(name, array)),
		    STRING_A);

		add(getArrayReader(VDFObject[].class,
		                   (array, i, in) -> array[i] = deserializeObject(in, new VDFObject()),
		                   (obj, name, array) -> obj.setObjectArray(name, array)),
		    OBJECT_A);
	}

	private void add(BinaryDeserializer deserializer, byte code) {
		deserializers[code] = deserializer;
	}

	protected VDFObject deserializeObject(DataInputStream in, VDFObject obj) throws IOException {
		byte code;
		while((code = in.readByte()) != -1) {				// read code, until end mark (-1) is found
			String name = in.readUTF();						// read name

			deserializers[code].deserialize(obj, name, in);	// deserialize and add element to object
		}
		return obj;
	}

	private <T> BinaryDeserializer getArrayReader(Class<T> type,
	                                              ArrayElementDeserializer<T> elementDeserializer,
	                                              ArrayAdder<T> arrayAdder) {
		return (obj, name, in) -> {
			int length = in.readInt();

			T array = type.cast(Array.newInstance(type.getComponentType(), length));
			for(int i = 0; i < length; i++) {
				elementDeserializer.deserialize(array, i, in);
			}
			arrayAdder.add(obj, name, array);
		};
	}

	private interface BinaryDeserializer {

		void deserialize(VDFObject obj, String name, DataInputStream in) throws IOException;

	}

	private interface ArrayElementDeserializer<T> {

		void deserialize(T array, int i, DataInputStream in) throws IOException;

	}

	private interface ArrayAdder<T> {

		void add(VDFObject obj, String name, T array);

	}

}
