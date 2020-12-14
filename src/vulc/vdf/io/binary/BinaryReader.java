package vulc.vdf.io.binary;

import static vulc.vdf.io.VDFCodes.*;

import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.Array;

import vulc.vdf.VDFList;
import vulc.vdf.VDFObject;
import vulc.vdf.io.VDFCodes;
import vulc.vdf.io.VDFReader;

class BinaryReader extends VDFReader<DataInputStream> {

	private final ElementDeserializer[] deserializers = new ElementDeserializer[VDFCodes.TYPES];

	protected BinaryReader() {
		add(() -> in.readBoolean(), BOOLEAN);
		add(() -> in.readByte(), BYTE);
		add(() -> in.readShort(), SHORT);
		add(() -> in.readInt(), INT);
		add(() -> in.readLong(), LONG);
		add(() -> in.readFloat(), FLOAT);
		add(() -> in.readDouble(), DOUBLE);
		add(() -> in.readChar(), CHAR);
		add(() -> in.readUTF(), STRING);
		add(() -> deserializeObject(new VDFObject()), OBJECT);
		add(() -> deserializeList(new VDFList()), LIST);

		add(getArrayReader(boolean[].class, (array, i) -> array[i] = in.readBoolean()), BOOLEAN_A);
		add(getArrayReader(byte[].class, (array, i) -> array[i] = in.readByte()), BYTE_A);
		add(getArrayReader(short[].class, (array, i) -> array[i] = in.readShort()), SHORT_A);
		add(getArrayReader(int[].class, (array, i) -> array[i] = in.readInt()), INT_A);
		add(getArrayReader(long[].class, (array, i) -> array[i] = in.readLong()), LONG_A);
		add(getArrayReader(float[].class, (array, i) -> array[i] = in.readFloat()), FLOAT_A);
		add(getArrayReader(double[].class, (array, i) -> array[i] = in.readDouble()), DOUBLE_A);
		add(getArrayReader(char[].class, (array, i) -> array[i] = in.readChar()), CHAR_A);
		add(getArrayReader(String[].class, (array, i) -> array[i] = in.readUTF()), STRING_A);
		add(getArrayReader(VDFObject[].class, (array, i) -> array[i] = deserializeObject(new VDFObject())), OBJECT_A);
		add(getArrayReader(VDFList[].class, (array, i) -> array[i] = deserializeList(new VDFList())), LIST_A);
	}

	private void add(ElementDeserializer deserializer, byte code) {
		deserializers[code] = deserializer;
	}

	public VDFObject deserializeObject(VDFObject obj) throws IOException {
		byte code;
		while((code = in.readByte()) != -1) {							// read code, until end mark (-1) is found
			String name = in.readUTF();									// read name

			obj.setElement(name, deserializers[code].deserialize());	// deserialize and add element to object
		}
		return obj;
	}

	public VDFList deserializeList(VDFList list) throws IOException {
		byte code;
		while((code = in.readByte()) != -1) {							// read code, until end mark (-1) is found
			list.addElement(deserializers[code].deserialize());			// deserialize and add element to list
		}
		return list;
	}

	private <T> ElementDeserializer getArrayReader(Class<T> type,
	                                               ArrayElementDeserializer<T> arrayElementDeserializer) {
		return () -> {
			int length = in.readInt();

			T array = type.cast(Array.newInstance(type.getComponentType(), length));
			for(int i = 0; i < length; i++) {
				arrayElementDeserializer.deserialize(array, i);
			}
			return array;
		};
	}

	private interface ElementDeserializer {

		Object deserialize() throws IOException;

	}

	private interface ArrayElementDeserializer<T> {

		void deserialize(T array, int i) throws IOException;

	}

}
