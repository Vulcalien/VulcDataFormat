package vulc.vdf.io.binary;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import vulc.vdf.VDFList;
import vulc.vdf.VDFObject;

public abstract class BinaryIO {

	private static BinaryReader reader = null;
	private static BinaryWriter writer = null;

	private static boolean reuseIO = false;

	public static void setReuseIO(boolean flag) {
		if(flag == reuseIO) return;

		reuseIO = flag;
		if(reuseIO) {
			reader = new BinaryReader();
			writer = new BinaryWriter();
		} else {
			reader = null;
			writer = null;
		}
	}

	// deserialize

	private static <T> void deserialize(DataInputStream in, Deserializer<T> deserializer) throws IOException {
		if(!reuseIO) reader = new BinaryReader();

		reader.in = in;
		deserializer.deserialize(reader);

		if(!reuseIO) reader = null;
	}

	public static void deserialize(DataInputStream in, VDFObject obj) throws IOException {
		deserialize(in, reader -> reader.deserializeObject(obj));
	}

	public static void deserialize(DataInputStream in, VDFList list) throws IOException {
		deserialize(in, reader -> reader.deserializeList(list));
	}

	// serialize

	private static <T> void serialize(DataOutputStream out, Serializer<T> serializer) throws IOException {
		if(!reuseIO) writer = new BinaryWriter();

		writer.out = out;
		serializer.serialize(writer);

		if(!reuseIO) writer = null;
	}

	public static void serialize(DataOutputStream out, VDFObject obj) throws IOException {
		serialize(out, writer -> writer.serializeObject(obj));
	}

	public static void serialize(DataOutputStream out, VDFList list) throws IOException {
		serialize(out, writer -> writer.serializeList(list));
	}

	// interfaces

	private interface Deserializer<T> {

		void deserialize(BinaryReader reader) throws IOException;

	}

	private interface Serializer<T> {

		void serialize(BinaryWriter writer) throws IOException;

	}

}
