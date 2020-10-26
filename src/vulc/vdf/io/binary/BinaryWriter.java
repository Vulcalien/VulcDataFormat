package vulc.vdf.io.binary;

import static vulc.vdf.io.VDFCodes.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;

import vulc.vdf.BooleanElement;
import vulc.vdf.ByteElement;
import vulc.vdf.CharElement;
import vulc.vdf.DoubleElement;
import vulc.vdf.Element;
import vulc.vdf.FloatElement;
import vulc.vdf.IntElement;
import vulc.vdf.LongElement;
import vulc.vdf.ShortElement;
import vulc.vdf.StringElement;
import vulc.vdf.VDFObject;
import vulc.vdf.io.VDFCodes;

class BinaryWriter {

	private final BinarySerializer[] serializers = new BinarySerializer[VDFCodes.TYPES];

	protected BinaryWriter() {
		add((e, out) -> out.writeBoolean(((BooleanElement) e).value), BOOLEAN);
		add((e, out) -> out.writeByte(((ByteElement) e).value), BYTE);
		add((e, out) -> out.writeShort(((ShortElement) e).value), SHORT);
		add((e, out) -> out.writeInt(((IntElement) e).value), INT);
		add((e, out) -> out.writeLong(((LongElement) e).value), LONG);
		add((e, out) -> out.writeFloat(((FloatElement) e).value), FLOAT);
		add((e, out) -> out.writeDouble(((DoubleElement) e).value), DOUBLE);
		add((e, out) -> out.writeChar(((CharElement) e).value), CHAR);
		add((e, out) -> out.writeUTF(((StringElement) e).value), STRING);
		add((e, out) -> serializeObject(out, (VDFObject) e), OBJECT);

		add(getArrayWriter(boolean[].class, (array, i, out) -> out.writeBoolean(array[i])), BOOLEAN_A);
		add(getArrayWriter(byte[].class, (array, i, out) -> out.writeByte(array[i])), BYTE_A);
		add(getArrayWriter(short[].class, (array, i, out) -> out.writeShort(array[i])), SHORT_A);
		add(getArrayWriter(int[].class, (array, i, out) -> out.writeInt(array[i])), INT_A);
		add(getArrayWriter(long[].class, (array, i, out) -> out.writeLong(array[i])), LONG_A);
		add(getArrayWriter(float[].class, (array, i, out) -> out.writeFloat(array[i])), FLOAT_A);
		add(getArrayWriter(double[].class, (array, i, out) -> out.writeDouble(array[i])), DOUBLE_A);
		add(getArrayWriter(char[].class, (array, i, out) -> out.writeChar(array[i])), CHAR_A);
		add(getArrayWriter(String[].class, (array, i, out) -> out.writeUTF(array[i])), STRING_A);
		add(getArrayWriter(VDFObject[].class, (array, i, out) -> serializeObject(out, array[i])), OBJECT_A);
	}

	private void add(BinarySerializer serializer, byte code) {
		serializers[code] = serializer;
	}

	protected void serializeObject(DataOutputStream out, VDFObject obj) throws IOException {
		for(String name : obj.keySet()) {
			Element e = obj.getElement(name);

			byte code = VDFCodes.get(e.getClass());

			out.writeByte(code);						// write code
			out.writeUTF(name);							// write name

			serializers[code].serialize(e, out);		// serialize
		}
		out.writeByte(-1);								// write end mark
	}

	private <T> BinarySerializer getArrayWriter(Class<T> type, ArrayElementSerializer<T> elementSerializer) {
		return (e, out) -> {
			Object array = e.get();
			int length = Array.getLength(array);

			out.writeInt(length);
			for(int i = 0; i < length; i++) {
				elementSerializer.serialize(type.cast(array), i, out);
			}
		};
	}

	private interface BinarySerializer {

		void serialize(Element e, DataOutputStream out) throws IOException;

	}

	private interface ArrayElementSerializer<T> {

		void serialize(T array, int i, DataOutputStream out) throws IOException;

	}

}
