package vulc.vdf.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import vulc.vdf.ObjectElement;

public abstract class BinaryIO {

	public static ObjectElement deserialize(DataInputStream in, ObjectElement obj) throws IOException {
		return BinaryReader.deserializeObject(in, obj);
	}

	public static ObjectElement deserialize(DataInputStream in) throws IOException {
		return BinaryReader.deserializeObject(in, new ObjectElement());
	}

	public static void serialize(DataOutputStream out, ObjectElement obj) throws IOException {
		BinaryWriter.serializeObject(out, obj);
	}

}
