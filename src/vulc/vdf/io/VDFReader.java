package vulc.vdf.io;

import java.io.IOException;

import vulc.vdf.ObjectElement;

@SuppressWarnings("unchecked")
abstract class VDFReader<T> {

	protected final Deserializer<T>[] deserializers = new Deserializer[VDFCodes.TYPES];

	protected void add(Deserializer<T> deserializer, byte code) {
		deserializers[code] = deserializer;
	}

	protected abstract ObjectElement deserializeObject(T in, ObjectElement obj) throws IOException;

	protected interface Deserializer<S> {

		void deserialize(ObjectElement obj, String name, S in) throws IOException;

	}

}
