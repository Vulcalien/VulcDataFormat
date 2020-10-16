package vulc.vdf.io;

import java.io.DataInputStream;
import java.io.IOException;

import vulc.vdf.ObjectElement;

interface Deserializer {

	void deserialize(ObjectElement obj, String name, DataInputStream in) throws IOException;

}
