package vulc.vdf.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import vulc.vdf.ObjectElement;

public abstract class BinaryIO {

	private static final BinaryReader READER = new BinaryReader();
	private static final BinaryWriter WRITER = new BinaryWriter();

	public static ObjectElement deserialize(DataInputStream in, ObjectElement obj) throws IOException {
		return READER.deserializeObject(in, obj);
	}

	public static void serialize(DataOutputStream out, ObjectElement obj) throws IOException {
		WRITER.serializeObject(out, obj);
	}

}
