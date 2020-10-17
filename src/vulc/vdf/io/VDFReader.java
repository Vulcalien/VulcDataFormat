package vulc.vdf.io;

import java.io.DataInputStream;
import java.io.IOException;

import vulc.vdf.ObjectElement;

abstract class VDFReader {

	protected final Deserializer[] deserializers = new Deserializer[BinaryCodes.TYPES];

	protected void add(Deserializer deserializer, byte code) {
		deserializers[code] = deserializer;
	}

	protected abstract ObjectElement deserializeObject(DataInputStream in, ObjectElement obj) throws IOException;

	protected static interface Deserializer {

		void deserialize(ObjectElement obj, String name, DataInputStream in) throws IOException;

	}

}
