package vulc.vdf.io;

import java.io.IOException;

public abstract class VDFWriter<T> {

	public T out;
	protected final ElementSerializer[] serializers = new ElementSerializer[VDFCodes.TYPES];

	protected void add(ElementSerializer serializer, byte code) {
		serializers[code] = serializer;
	}

	public void serialize(Object element) throws IOException {
		byte code = VDFCodes.get(element.getClass());
		serializers[code].serialize(element);
	}

	protected abstract <K> ElementSerializer getArrayWriter(Class<K> type, ArrayElementSerializer<K> serializer);

	protected interface ElementSerializer {
		void serialize(Object e) throws IOException;
	}

	protected interface ArrayElementSerializer<K> {
		void serialize(K array, int i) throws IOException;
	}

}
