package vulc.vdf.io;

import java.io.IOException;
import java.io.InputStream;

import vulc.vdf.ObjectElement;

@SuppressWarnings("unchecked")
abstract class VDFReader<T extends InputStream> {

	protected final Deserializer<T>[] deserializers = new Deserializer[BinaryCodes.TYPES]; // TODO remove reference to a "binary" class

	protected void add(Deserializer<T> deserializer, byte code) {
		deserializers[code] = deserializer;
	}

	protected abstract ObjectElement deserializeObject(T in, ObjectElement obj) throws IOException;

	protected static interface Deserializer<S> {

		void deserialize(ObjectElement obj, String name, S in) throws IOException;

	}

}
