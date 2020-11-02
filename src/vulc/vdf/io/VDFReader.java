package vulc.vdf.io;

import vulc.vdf.VDFList;
import vulc.vdf.VDFObject;

public abstract class VDFReader<T> {

	public T in;

	public abstract VDFObject deserializeObject(VDFObject obj) throws Exception;

	public abstract VDFList deserializeList(VDFList list) throws Exception;

}
