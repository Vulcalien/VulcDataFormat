package vulc.vdf.io;

import java.io.IOException;

public abstract class VDFWriter<T> {

	protected T out;
	protected final ElementSerializer[] serializers = new ElementSerializer[VDFCodes.TYPES];

	protected void add(ElementSerializer serializer, byte code) {
		serializers[code] = serializer;
	}

	public void serialize(T out, Object element) throws IOException {
		this.out = out;

		byte code = VDFCodes.get(element);
		serializers[code].serialize(element);

		this.out = null;
	}

	protected abstract <K> ElementSerializer getArrayWriter(Class<K> type, ArrayElementSerializer<K> serializer);

	protected interface ElementSerializer {
		void serialize(Object e) throws IOException;
	}

	protected interface ArrayElementSerializer<K> {
		void serialize(K array, int i) throws IOException;
	}

}
