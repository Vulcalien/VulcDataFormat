package vulc.vdf.io.binary;

import static vulc.vdf.io.VDFCodes.*;

import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.Array;

import vulc.vdf.BooleanArrayElement;
import vulc.vdf.BooleanElement;
import vulc.vdf.ByteArrayElement;
import vulc.vdf.ByteElement;
import vulc.vdf.CharArrayElement;
import vulc.vdf.CharElement;
import vulc.vdf.DoubleArrayElement;
import vulc.vdf.DoubleElement;
import vulc.vdf.Element;
import vulc.vdf.FloatArrayElement;
import vulc.vdf.FloatElement;
import vulc.vdf.IntArrayElement;
import vulc.vdf.IntElement;
import vulc.vdf.LongArrayElement;
import vulc.vdf.LongElement;
import vulc.vdf.ObjectArrayElement;
import vulc.vdf.ShortArrayElement;
import vulc.vdf.ShortElement;
import vulc.vdf.StringArrayElement;
import vulc.vdf.StringElement;
import vulc.vdf.VDFObject;
import vulc.vdf.io.VDFCodes;

class BinaryReader {

	private final ElementDeserializer[] deserializers = new ElementDeserializer[VDFCodes.TYPES];

	protected BinaryReader() {
		add(in -> new BooleanElement(in.readBoolean()), BOOLEAN);
		add(in -> new ByteElement(in.readByte()), BYTE);
		add(in -> new ShortElement(in.readShort()), SHORT);
		add(in -> new IntElement(in.readInt()), INT);
		add(in -> new LongElement(in.readLong()), LONG);
		add(in -> new FloatElement(in.readFloat()), FLOAT);
		add(in -> new DoubleElement(in.readDouble()), DOUBLE);
		add(in -> new CharElement(in.readChar()), CHAR);
		add(in -> new StringElement(in.readUTF()), STRING);
		add(in -> deserializeObject(in, new VDFObject()), OBJECT);

		add(getArrayReader(boolean[].class,
		                   (array, i, in) -> array[i] = in.readBoolean(),
		                   array -> new BooleanArrayElement(array)),
		    BOOLEAN_A);

		add(getArrayReader(byte[].class,
		                   (array, i, in) -> array[i] = in.readByte(),
		                   array -> new ByteArrayElement(array)),
		    BYTE_A);

		add(getArrayReader(short[].class,
		                   (array, i, in) -> array[i] = in.readShort(),
		                   array -> new ShortArrayElement(array)),
		    SHORT_A);

		add(getArrayReader(int[].class,
		                   (array, i, in) -> array[i] = in.readInt(),
		                   array -> new IntArrayElement(array)),
		    INT_A);

		add(getArrayReader(long[].class,
		                   (array, i, in) -> array[i] = in.readLong(),
		                   array -> new LongArrayElement(array)),
		    LONG_A);

		add(getArrayReader(float[].class,
		                   (array, i, in) -> array[i] = in.readFloat(),
		                   array -> new FloatArrayElement(array)),
		    FLOAT_A);

		add(getArrayReader(double[].class,
		                   (array, i, in) -> array[i] = in.readDouble(),
		                   array -> new DoubleArrayElement(array)),
		    DOUBLE_A);

		add(getArrayReader(char[].class,
		                   (array, i, in) -> array[i] = in.readChar(),
		                   array -> new CharArrayElement(array)),
		    CHAR_A);

		add(getArrayReader(String[].class,
		                   (array, i, in) -> array[i] = in.readUTF(),
		                   array -> new StringArrayElement(array)),
		    STRING_A);

		add(getArrayReader(VDFObject[].class,
		                   (array, i, in) -> array[i] = deserializeObject(in, new VDFObject()),
		                   array -> new ObjectArrayElement(array)),
		    OBJECT_A);
	}

	private void add(ElementDeserializer deserializer, byte code) {
		deserializers[code] = deserializer;
	}

	protected VDFObject deserializeObject(DataInputStream in, VDFObject obj) throws IOException {
		byte code;
		while((code = in.readByte()) != -1) {							// read code, until end mark (-1) is found
			String name = in.readUTF();									// read name

			obj.setElement(name, deserializers[code].deserialize(in));	// deserialize and add element to object
		}
		return obj;
	}

	private <T> ElementDeserializer getArrayReader(Class<T> type,
	                                               ArrayElementDeserializer<T> arrayElementDeserializer,
	                                               ArrayMaker<T> arrayMaker) {
		return in -> {
			int length = in.readInt();

			T array = type.cast(Array.newInstance(type.getComponentType(), length));
			for(int i = 0; i < length; i++) {
				arrayElementDeserializer.deserialize(array, i, in);
			}
			return arrayMaker.make(array);
		};
	}

	private interface ElementDeserializer {

		Element deserialize(DataInputStream in) throws IOException;

	}

	private interface ArrayElementDeserializer<T> {

		void deserialize(T array, int i, DataInputStream in) throws IOException;

	}

	private interface ArrayMaker<T> {

		Element make(T array);

	}

}
