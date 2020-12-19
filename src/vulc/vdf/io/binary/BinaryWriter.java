package vulc.vdf.io.binary;

import static vulc.vdf.io.VDFCodes.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;

import vulc.vdf.VDFList;
import vulc.vdf.VDFObject;
import vulc.vdf.io.VDFCodes;
import vulc.vdf.io.VDFWriter;

class BinaryWriter extends VDFWriter<DataOutputStream> {

	protected BinaryWriter() {
		add(e -> out.writeBoolean((Boolean) e), BOOLEAN);
		add(e -> out.writeByte((Byte) e), BYTE);
		add(e -> out.writeShort((Short) e), SHORT);
		add(e -> out.writeInt((Integer) e), INT);
		add(e -> out.writeLong((Long) e), LONG);
		add(e -> out.writeFloat((Float) e), FLOAT);
		add(e -> out.writeDouble((Double) e), DOUBLE);
		add(e -> out.writeChar((Character) e), CHAR);
		add(e -> out.writeUTF((String) e), STRING);
		add(e -> serializeObject((VDFObject) e), OBJECT);
		add(e -> serializeList((VDFList) e), LIST);

		add(getArrayWriter(boolean[].class, (array, i) -> out.writeBoolean(array[i])), BOOLEAN_A);
		add(getArrayWriter(byte[].class, (array, i) -> out.writeByte(array[i])), BYTE_A);
		add(getArrayWriter(short[].class, (array, i) -> out.writeShort(array[i])), SHORT_A);
		add(getArrayWriter(int[].class, (array, i) -> out.writeInt(array[i])), INT_A);
		add(getArrayWriter(long[].class, (array, i) -> out.writeLong(array[i])), LONG_A);
		add(getArrayWriter(float[].class, (array, i) -> out.writeFloat(array[i])), FLOAT_A);
		add(getArrayWriter(double[].class, (array, i) -> out.writeDouble(array[i])), DOUBLE_A);
		add(getArrayWriter(char[].class, (array, i) -> out.writeChar(array[i])), CHAR_A);
		add(getArrayWriter(String[].class, (array, i) -> out.writeUTF(array[i])), STRING_A);
		add(getArrayWriter(VDFObject[].class, (array, i) -> serializeObject(array[i])), OBJECT_A);
		add(getArrayWriter(VDFList[].class, (array, i) -> serializeList(array[i])), LIST_A);
	}

	public void serializeObject(VDFObject obj) throws IOException {
		for(String key : obj.keySet()) {
			Object e = obj.getElement(key);

			byte code = VDFCodes.get(e.getClass());

			out.writeByte(code);						// write code
			out.writeUTF(key);							// write key

			serializers[code].serialize(e);				// serialize
		}
		out.writeByte(-1);								// write end mark
	}

	public void serializeList(VDFList list) throws IOException {
		for(Object e : list) {
			byte code = VDFCodes.get(e.getClass());

			out.writeByte(code);						// write code
			serializers[code].serialize(e);				// serialize
		}
		out.writeByte(-1);								// write end mark
	}

	protected <K> ElementSerializer getArrayWriter(Class<K> type, ArrayElementSerializer<K> serializer) {
		return array -> {
			int length = Array.getLength(array);

			out.writeInt(length);
			for(int i = 0; i < length; i++) {
				serializer.serialize(type.cast(array), i);
			}
		};
	}

}
