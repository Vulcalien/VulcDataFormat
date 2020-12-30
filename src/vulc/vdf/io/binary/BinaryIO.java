package vulc.vdf.io.binary;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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

	public static Object deserialize(DataInputStream in) throws IOException {
		if(!reuseIO) reader = new BinaryReader();

		Object element = reader.deserialize(in);

		if(!reuseIO) reader = null;
		return element;
	}

	public static void serialize(DataOutputStream out, Object element) throws IOException {
		if(!reuseIO) writer = new BinaryWriter();

		writer.serialize(out, element);

		if(!reuseIO) writer = null;
	}

}
