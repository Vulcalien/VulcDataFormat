package vulc.vdf.io.binary;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import vulc.vdf.VDFObject;

public abstract class BinaryIO {

	private static final BinaryReader READER = new BinaryReader();
	private static final BinaryWriter WRITER = new BinaryWriter();

	public static VDFObject deserialize(DataInputStream in, VDFObject obj) throws IOException {
		return READER.deserializeObject(in, obj);
	}

	public static void serialize(DataOutputStream out, VDFObject obj) throws IOException {
		WRITER.serializeObject(out, obj);
	}

}
