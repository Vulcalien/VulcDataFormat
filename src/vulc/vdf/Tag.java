package vulc.vdf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

abstract class Tag {

	protected abstract Object get();

	public abstract void serialize(DataOutputStream out) throws IOException;

	public abstract void deserialize(DataInputStream in) throws IOException;

}
