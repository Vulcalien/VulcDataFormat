/*******************************************************************************
 * Copyright 2019 - 2020 Vulcalien
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package vulc.vdf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Set;

import vulc.vdf.io.binary.BinaryIO;
import vulc.vdf.io.text.TextIO;

public class ObjectElement extends Element {

	private final HashMap<String, Element> map = new HashMap<String, Element>();

	public int size() {
		return map.size();
	}

	public Set<String> keySet() {
		return map.keySet();
	}

	public Element getElement(String name) {
		Element e = map.get(name);

		if(e == null) throw new NoSuchElementException(name);
		return e;
	}

	public Object getValue(String name) {
		return getElement(name).get();
	}

	public Class<?> getType(String name) {
		return getValue(name).getClass();
	}

	public void removeElement(String name) {
		map.remove(name);
	}

	public void clear() {
		map.clear();
	}

	protected Object get() {
		return this;
	}

	// getters and setters

	public boolean getBoolean(String name) {
		return ((BooleanElement) getElement(name)).value;
	}

	public void setBoolean(String name, boolean value) {
		map.put(name, new BooleanElement(value));
	}

	public byte getByte(String name) {
		return ((ByteElement) getElement(name)).value;
	}

	public void setByte(String name, byte value) {
		map.put(name, new ByteElement(value));
	}

	public short getShort(String name) {
		return ((ShortElement) getElement(name)).value;
	}

	public void setShort(String name, short value) {
		map.put(name, new ShortElement(value));
	}

	public int getInt(String name) {
		return ((IntElement) getElement(name)).value;
	}

	public void setInt(String name, int value) {
		map.put(name, new IntElement(value));
	}

	public long getLong(String name) {
		return ((LongElement) getElement(name)).value;
	}

	public void setLong(String name, long value) {
		map.put(name, new LongElement(value));
	}

	public float getFloat(String name) {
		return ((FloatElement) getElement(name)).value;
	}

	public void setFloat(String name, float value) {
		map.put(name, new FloatElement(value));
	}

	public double getDouble(String name) {
		return ((DoubleElement) getElement(name)).value;
	}

	public void setDouble(String name, double value) {
		map.put(name, new DoubleElement(value));
	}

	public char getChar(String name) {
		return ((CharElement) getElement(name)).value;
	}

	public void setChar(String name, char value) {
		map.put(name, new CharElement(value));
	}

	public String getString(String name) {
		return ((StringElement) getElement(name)).value;
	}

	public void setString(String name, String value) {
		map.put(name, new StringElement(value));
	}

	public ObjectElement getObject(String name) {
		return (ObjectElement) getElement(name);
	}

	public void setObject(String name, ObjectElement objectElement) {
		map.put(name, objectElement);
	}

	// arrays

	public boolean[] getBooleanArray(String name) {
		return ((BooleanArrayElement) getElement(name)).value;
	}

	public void setBooleanArray(String name, boolean[] value) {
		map.put(name, new BooleanArrayElement(value));
	}

	public byte[] getByteArray(String name) {
		return ((ByteArrayElement) getElement(name)).value;
	}

	public void setByteArray(String name, byte[] value) {
		map.put(name, new ByteArrayElement(value));
	}

	public short[] getShortArray(String name) {
		return ((ShortArrayElement) getElement(name)).value;
	}

	public void setShortArray(String name, short[] value) {
		map.put(name, new ShortArrayElement(value));
	}

	public int[] getIntArray(String name) {
		return ((IntArrayElement) getElement(name)).value;
	}

	public void setIntArray(String name, int[] value) {
		map.put(name, new IntArrayElement(value));
	}

	public long[] getLongArray(String name) {
		return ((LongArrayElement) getElement(name)).value;
	}

	public void setLongArray(String name, long[] value) {
		map.put(name, new LongArrayElement(value));
	}

	public float[] getFloatArray(String name) {
		return ((FloatArrayElement) getElement(name)).value;
	}

	public void setFloatArray(String name, float[] value) {
		map.put(name, new FloatArrayElement(value));
	}

	public double[] getDoubleArray(String name) {
		return ((DoubleArrayElement) getElement(name)).value;
	}

	public void setDoubleArray(String name, double[] value) {
		map.put(name, new DoubleArrayElement(value));
	}

	public char[] getCharArray(String name) {
		return ((CharArrayElement) getElement(name)).value;
	}

	public void setCharArray(String name, char[] value) {
		map.put(name, new CharArrayElement(value));
	}

	public String[] getStringArray(String name) {
		return ((StringArrayElement) getElement(name)).value;
	}

	public void setStringArray(String name, String[] value) {
		map.put(name, new StringArrayElement(value));
	}

	public ObjectElement[] getObjectArray(String name) {
		return ((ObjectArrayElement) getElement(name)).value;
	}

	public void setObjectArray(String name, ObjectElement[] value) {
		map.put(name, new ObjectArrayElement(value));
	}

	// IO

	public ObjectElement deserialize(DataInputStream in) throws IOException {
		return BinaryIO.deserialize(in, this);
	}

	public void serialize(DataOutputStream out) throws IOException {
		BinaryIO.serialize(out, this);
	}

	public ObjectElement parse(String string) {
		return TextIO.deserialize(string, this);
	}

	public String toString(boolean format) {
		return TextIO.stringify(this, format);
	}

	public String toString() {
		return toString(false);
	}

}
