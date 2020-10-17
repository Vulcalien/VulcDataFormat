package vulc.vdf.io;

import java.io.IOException;

import vulc.vdf.ObjectElement;

@SuppressWarnings("unchecked")
abstract class VDFWriter<T> {

	protected final Serializer<T>[] serializers = new Serializer[VDFCodes.TYPES];

	protected void add(Serializer<T> serializer, byte code) {
		serializers[code] = serializer;
	}

	protected abstract void serializeObject(T out, ObjectElement obj) throws IOException;

	protected interface Serializer<S> {

		void serialize(Object value, S out) throws IOException;

	}

}
