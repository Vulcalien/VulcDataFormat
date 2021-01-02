package vulc.vdf.io.binary;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class BinaryVDF {

	private BinaryVDF() {
	}

	private static BinaryReader reader = null;
	private static BinaryWriter writer = null;

	private static boolean reuseIO = false;

	public static boolean getReuseIO() {
		return reuseIO;
	}

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

	// wrapper functions

	public static Object deserialize(InputStream in) throws IOException {
		return deserialize(new DataInputStream(in));
	}

	public static Object deserialize(File file) throws IOException {
		try(InputStream in = new BufferedInputStream(new FileInputStream(file))) {
			return deserialize(in);
		}
	}

	public static void serialize(OutputStream out, Object element) throws IOException {
		serialize(new DataOutputStream(out), element);
	}

	public static void serialize(File file, Object element) throws IOException {
		try(OutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
			serialize(out, element);
		}
	}

}
