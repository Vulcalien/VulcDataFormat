package vulc.vdf.io;

import java.io.IOException;

public abstract class VDFReader<T> {

	protected T in;
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

	protected abstract <K> ElementDeserializer getArrayReader(Class<K> type, ArrayElementDeserializer<K> deserializer);

	protected interface ElementDeserializer {
		Object deserialize() throws IOException;
	}

	protected interface ArrayElementDeserializer<T> {
		void deserialize(T array, int i) throws IOException;
	}

}
