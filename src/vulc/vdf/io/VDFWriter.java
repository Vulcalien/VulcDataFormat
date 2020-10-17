package vulc.vdf.io;

import java.io.IOException;
import java.io.OutputStream;

import vulc.vdf.ObjectElement;

@SuppressWarnings("unchecked")
abstract class VDFWriter<T extends OutputStream> {

	protected final Serializer<T>[] serializers = new Serializer[BinaryCodes.TYPES];

	protected void add(Serializer<T> serializer, byte code) {
		serializers[code] = serializer;
	}

	protected abstract void serializeObject(T out, ObjectElement obj) throws IOException;

	protected static interface Serializer<S> {

		void serialize(Object value, S out) throws IOException;

	}

}
