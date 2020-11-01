package vulc.vdf.io.binary;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import vulc.vdf.VDFList;
import vulc.vdf.VDFObject;

public abstract class BinaryIO {

	// TODO somehow delete these after use
	private static BinaryReader reader = new BinaryReader();
	private static BinaryWriter writer = new BinaryWriter();

	// TODO test all these functions

	public static VDFObject deserialize(DataInputStream in, VDFObject obj) throws IOException {
		reader.in = in;
		return reader.deserializeObject(obj);
	}

	public static VDFList deserialize(DataInputStream in, VDFList list) throws IOException {
		reader.in = in;
		return reader.deserializeList(list);
	}

	public static void serialize(DataOutputStream out, VDFObject obj) throws IOException {
		writer.out = out;
		writer.serializeObject(obj);
	}

	public static void serialize(DataOutputStream out, VDFList list) throws IOException {
		writer.out = out;
		writer.serializeList(list);
	}

}
