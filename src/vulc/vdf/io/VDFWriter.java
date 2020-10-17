package vulc.vdf.io;

import java.io.DataOutputStream;
import java.io.IOException;

import vulc.vdf.ObjectElement;

abstract class VDFWriter {

	protected final Serializer[] SERIALIZERS = new Serializer[BinaryCodes.TYPES];

	protected void add(Serializer serializer, byte code) {
		SERIALIZERS[code] = serializer;
	}

	protected abstract void serializeObject(DataOutputStream out, ObjectElement obj) throws IOException;

	static interface Serializer {

		void serialize(Object value, DataOutputStream out) throws IOException;

	}

}
