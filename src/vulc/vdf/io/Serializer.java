package vulc.vdf.io;

import java.io.DataOutputStream;
import java.io.IOException;

interface Serializer {

	void serialize(Object value, DataOutputStream out) throws IOException;

}
