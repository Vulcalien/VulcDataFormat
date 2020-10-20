package vulc.vdf.io;

import static vulc.vdf.io.VDFCodes.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;

import vulc.vdf.ObjectElement;

class BinaryWriter {

	private final BinarySerializer[] serializers = new BinarySerializer[VDFCodes.TYPES];

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
		add((value, out) -> serializeObject(out, (ObjectElement) value), OBJECT);

		add(getArrayWriter(boolean[].class, (array, i, out) -> out.writeBoolean(array[i])), BOOLEAN_A);
		add(getArrayWriter(byte[].class, (array, i, out) -> out.writeByte(array[i])), BYTE_A);
		add(getArrayWriter(short[].class, (array, i, out) -> out.writeShort(array[i])), SHORT_A);
		add(getArrayWriter(int[].class, (array, i, out) -> out.writeInt(array[i])), INT_A);
		add(getArrayWriter(long[].class, (array, i, out) -> out.writeLong(array[i])), LONG_A);
		add(getArrayWriter(float[].class, (array, i, out) -> out.writeFloat(array[i])), FLOAT_A);
		add(getArrayWriter(double[].class, (array, i, out) -> out.writeDouble(array[i])), DOUBLE_A);
		add(getArrayWriter(char[].class, (array, i, out) -> out.writeChar(array[i])), CHAR_A);
		add(getArrayWriter(String[].class, (array, i, out) -> out.writeUTF(array[i])), STRING_A);
		add(getArrayWriter(ObjectElement[].class, (array, i, out) -> serializeObject(out, array[i])), OBJECT_A);
	}

	private void add(BinarySerializer serializer, byte code) {
		serializers[code] = serializer;
	}

	protected void serializeObject(DataOutputStream out, ObjectElement obj) throws IOException {
		for(String name : obj.keySet()) {
			Object value = obj.getValue(name);

			byte code = CODES.get(value.getClass());

			out.writeByte(code);						// write code
			out.writeUTF(name);							// write name

			serializers[code].serialize(value, out);	// serialize
		}
		out.writeByte(-1);								// write end mark
	}

	private <T> BinarySerializer getArrayWriter(Class<T> type, ArrayElementSerializer<T> elementSerializer) {
		return (value, out) -> {
			int length = Array.getLength(value);

			out.writeInt(length);
			for(int i = 0; i < length; i++) {
				elementSerializer.serialize(type.cast(value), i, out);
			}
		};
	}

	private interface BinarySerializer {

		void serialize(Object value, DataOutputStream out) throws IOException;

	}

	private interface ArrayElementSerializer<T> {

		void serialize(T array, int i, DataOutputStream out) throws IOException;

	}

}
