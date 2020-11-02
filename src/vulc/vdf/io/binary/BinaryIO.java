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
		reuseIO = flag;
		if(reuseIO) {
			reader = new BinaryReader();
			writer = new BinaryWriter();
		} else {
			reader = null;
			writer = null;
		}
	}

	// TODO test all these functions

	public static VDFObject deserialize(DataInputStream in, VDFObject obj) throws IOException {
		if(!reuseIO) reader = new BinaryReader();

		reader.in = in;
		reader.deserializeObject(obj);

		if(!reuseIO) reader = null;

		return obj;
	}

	public static VDFList deserialize(DataInputStream in, VDFList list) throws IOException {
		if(!reuseIO) reader = new BinaryReader();

		reader.in = in;
		reader.deserializeList(list);

		if(!reuseIO) reader = null;

		return list;
	}

	public static void serialize(DataOutputStream out, VDFObject obj) throws IOException {
		if(!reuseIO) writer = new BinaryWriter();

		writer.out = out;
		writer.serializeObject(obj);

		if(!reuseIO) reader = null;
	}

	public static void serialize(DataOutputStream out, VDFList list) throws IOException {
		if(!reuseIO) writer = new BinaryWriter();

		writer.out = out;
		writer.serializeList(list);

		if(!reuseIO) writer = null;
	}

}
