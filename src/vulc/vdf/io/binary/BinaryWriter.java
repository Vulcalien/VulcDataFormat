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
import vulc.vdf.VDFList;
import vulc.vdf.VDFObject;
import vulc.vdf.io.VDFCodes;
import vulc.vdf.io.VDFWriter;

class BinaryWriter extends VDFWriter<DataOutputStream> {

	private final BinarySerializer[] serializers = new BinarySerializer[VDFCodes.TYPES];

	protected BinaryWriter() {
		add(e -> out.writeBoolean(((BooleanElement) e).value), BOOLEAN);
		add(e -> out.writeByte(((ByteElement) e).value), BYTE);
		add(e -> out.writeShort(((ShortElement) e).value), SHORT);
		add(e -> out.writeInt(((IntElement) e).value), INT);
		add(e -> out.writeLong(((LongElement) e).value), LONG);
		add(e -> out.writeFloat(((FloatElement) e).value), FLOAT);
		add(e -> out.writeDouble(((DoubleElement) e).value), DOUBLE);
		add(e -> out.writeChar(((CharElement) e).value), CHAR);
		add(e -> out.writeUTF(((StringElement) e).value), STRING);
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

	private void add(BinarySerializer serializer, byte code) {
		serializers[code] = serializer;
	}

	public void serializeObject(VDFObject obj) throws IOException {
		for(String name : obj.keySet()) {
			Element e = obj.getElement(name);

			byte code = VDFCodes.get(e.getClass());

			out.writeByte(code);						// write code
			out.writeUTF(name);							// write name

			serializers[code].serialize(e);				// serialize
		}
		out.writeByte(-1);								// write end mark
	}

	public void serializeList(VDFList list) throws IOException {
		for(Element e : list) {
			byte code = VDFCodes.get(e.getClass());

			out.writeByte(code);						// write code
			serializers[code].serialize(e);				// serialize
		}
		out.writeByte(-1);								// write end mark
	}

	private <T> BinarySerializer getArrayWriter(Class<T> type, ArrayElementSerializer<T> elementSerializer) {
		return e -> {
			Object array = e.get();
			int length = Array.getLength(array);

			out.writeInt(length);
			for(int i = 0; i < length; i++) {
				elementSerializer.serialize(type.cast(array), i);
			}
		};
	}

	private interface BinarySerializer {

		void serialize(Element e) throws IOException;

	}

	private interface ArrayElementSerializer<T> {

		void serialize(T array, int i) throws IOException;

	}

}
