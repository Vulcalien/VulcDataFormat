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

	public Object deserialize(T in) throws IOException {
		this.in = in;
		Object element = deserializeTopLevel(in);
		this.in = null;
		return element;
	}

	protected abstract Object deserializeTopLevel(T in) throws IOException;

	public abstract VDFObject deserializeObject(VDFObject obj) throws IOException;

	public abstract VDFList deserializeList(VDFList list) throws IOException;

	protected abstract <K> ElementDeserializer getArrayReader(Class<K> type, ArrayElementDeserializer<K> deserializer);

	protected interface ElementDeserializer {
		Object deserialize() throws IOException;
	}

	protected interface ArrayElementDeserializer<T> {
		void deserialize(T array, int i) throws IOException;
	}

}
