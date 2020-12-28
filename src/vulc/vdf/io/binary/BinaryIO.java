package vulc.vdf.io.binary;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import vulc.vdf.VDFList;
import vulc.vdf.VDFObject;

public final class BinaryIO {

	private BinaryIO() {
	}

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
		else reader.in = null;
	}

	public static void deserialize(DataInputStream in, VDFObject obj) throws IOException {
		deserialize(in, reader -> reader.deserializeObject(obj));
	}

	public static void deserialize(DataInputStream in, VDFList list) throws IOException {
		deserialize(in, reader -> reader.deserializeList(list));
	}

	// serialize

	public static void serialize(DataOutputStream out, Object element) throws IOException {
		if(!reuseIO) writer = new BinaryWriter();
		writer.out = out;

		writer.serialize(element);

		if(!reuseIO) writer = null;
		else writer.out = null;
	}

	// interfaces

	private interface Deserializer<T> {

		void deserialize(BinaryReader reader) throws IOException;

	}

}
