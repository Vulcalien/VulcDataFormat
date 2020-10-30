package vulc.vdf.io.binary;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import vulc.vdf.VDFList;
import vulc.vdf.VDFObject;

public abstract class BinaryIO {

	private static final BinaryReader READER = new BinaryReader();
	private static final BinaryWriter WRITER = new BinaryWriter();

	public static VDFObject deserialize(DataInputStream in, VDFObject obj) throws IOException {
		return READER.deserializeObject(in, obj);
	}

	public static VDFList deserialize(DataInputStream in, VDFList list) throws IOException {
		return READER.deserializeList(in, list);
	}

	public static void serialize(DataOutputStream out, VDFObject obj) throws IOException {
		WRITER.serializeObject(out, obj);
	}

	public static void serialize(DataOutputStream out, VDFList list) throws IOException {
		WRITER.serializeList(out, list);
	}

}
