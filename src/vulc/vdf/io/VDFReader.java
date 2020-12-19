package vulc.vdf.io;

import java.io.IOException;

import vulc.vdf.VDFList;
import vulc.vdf.VDFObject;

public abstract class VDFReader<T> {

	public T in;
	protected final ElementDeserializer[] deserializers = new ElementDeserializer[VDFCodes.TYPES];

	protected void add(ElementDeserializer deserializer, byte code) {
		deserializers[code] = deserializer;
	}

	public abstract VDFObject deserializeObject(VDFObject obj) throws Exception;

	public abstract VDFList deserializeList(VDFList list) throws Exception;

	protected abstract <K> ElementDeserializer getArrayReader(Class<K> type, ArrayElementDeserializer<K> deserializer);

	protected interface ElementDeserializer {

		Object deserialize() throws IOException;

	}

	protected interface ArrayElementDeserializer<T> {

		void deserialize(T array, int i) throws IOException;

	}

}
