package vulc.vdf.io;

import java.io.IOException;

import vulc.vdf.VDFList;
import vulc.vdf.VDFObject;

public abstract class VDFWriter<T> {

	public T out;
	protected final ElementSerializer[] serializers = new ElementSerializer[VDFCodes.TYPES];

	protected void add(ElementSerializer serializer, byte code) {
		serializers[code] = serializer;
	}

	public abstract void serializeObject(VDFObject obj) throws Exception;

	public abstract void serializeList(VDFList list) throws Exception;

	protected abstract <K> ElementSerializer getArrayWriter(Class<K> type, ArrayElementSerializer<K> serializer);

	protected interface ElementSerializer {
		void serialize(Object e) throws IOException;
	}

	protected interface ArrayElementSerializer<K> {
		void serialize(K array, int i) throws IOException;
	}

}
