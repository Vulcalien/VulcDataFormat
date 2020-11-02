package vulc.vdf.io;

import vulc.vdf.VDFList;
import vulc.vdf.VDFObject;

public abstract class VDFWriter<T> {

	public T out;

	public abstract void serializeObject(VDFObject obj) throws Exception;

	public abstract void serializeList(VDFList list) throws Exception;

}
