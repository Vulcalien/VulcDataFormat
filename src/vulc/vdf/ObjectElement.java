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

import vulc.vdf.io.BinaryIO;
import vulc.vdf.io.TextIO;

public class ObjectElement extends Element {

	private final HashMap<String, Element> map = new HashMap<String, Element>();

	public int size() {
		return map.size();
	}

	public Set<String> keySet() {
		return map.keySet();
	}

	private <T> T getElement(String name, Class<T> type) {
		Element e = map.get(name);

		if(type.isInstance(e)) {
			return type.cast(e);
		} else if(e == null) {
			throw new NoSuchElementException(name);
		} else {
			throw new ClassCastException(type.getName());
		}
	}

	public Object getValue(String name) {
		Element e = map.get(name);

		if(e != null) {
			return e.get();
		} else {
			throw new NoSuchElementException(name);
		}
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
		return getElement(name, BooleanElement.class).value;
	}

	public void setBoolean(String name, boolean value) {
		map.put(name, new BooleanElement(value));
	}

	public byte getByte(String name) {
		return getElement(name, ByteElement.class).value;
	}

	public void setByte(String name, byte value) {
		map.put(name, new ByteElement(value));
	}

	public short getShort(String name) {
		return getElement(name, ShortElement.class).value;
	}

	public void setShort(String name, short value) {
		map.put(name, new ShortElement(value));
	}

	public int getInt(String name) {
		return getElement(name, IntElement.class).value;
	}

	public void setInt(String name, int value) {
		map.put(name, new IntElement(value));
	}

	public long getLong(String name) {
		return getElement(name, LongElement.class).value;
	}

	public void setLong(String name, long value) {
		map.put(name, new LongElement(value));
	}

	public float getFloat(String name) {
		return getElement(name, FloatElement.class).value;
	}

	public void setFloat(String name, float value) {
		map.put(name, new FloatElement(value));
	}

	public double getDouble(String name) {
		return getElement(name, DoubleElement.class).value;
	}

	public void setDouble(String name, double value) {
		map.put(name, new DoubleElement(value));
	}

	public char getChar(String name) {
		return getElement(name, CharElement.class).value;
	}

	public void setChar(String name, char value) {
		map.put(name, new CharElement(value));
	}

	public String getString(String name) {
		return getElement(name, StringElement.class).value;
	}

	public void setString(String name, String value) {
		map.put(name, new StringElement(value));
	}

	public ObjectElement getObject(String name) {
		return getElement(name, ObjectElement.class);
	}

	public void setObject(String name, ObjectElement objectElement) {
		map.put(name, objectElement);
	}

	// arrays

	public boolean[] getBooleanArray(String name) {
		return getElement(name, BooleanArrayElement.class).value;
	}

	public void setBooleanArray(String name, boolean[] value) {
		map.put(name, new BooleanArrayElement(value));
	}

	public byte[] getByteArray(String name) {
		return getElement(name, ByteArrayElement.class).value;
	}

	public void setByteArray(String name, byte[] value) {
		map.put(name, new ByteArrayElement(value));
	}

	public short[] getShortArray(String name) {
		return getElement(name, ShortArrayElement.class).value;
	}

	public void setShortArray(String name, short[] value) {
		map.put(name, new ShortArrayElement(value));
	}

	public int[] getIntArray(String name) {
		return getElement(name, IntArrayElement.class).value;
	}

	public void setIntArray(String name, int[] value) {
		map.put(name, new IntArrayElement(value));
	}

	public long[] getLongArray(String name) {
		return getElement(name, LongArrayElement.class).value;
	}

	public void setLongArray(String name, long[] value) {
		map.put(name, new LongArrayElement(value));
	}

	public float[] getFloatArray(String name) {
		return getElement(name, FloatArrayElement.class).value;
	}

	public void setFloatArray(String name, float[] value) {
		map.put(name, new FloatArrayElement(value));
	}

	public double[] getDoubleArray(String name) {
		return getElement(name, DoubleArrayElement.class).value;
	}

	public void setDoubleArray(String name, double[] value) {
		map.put(name, new DoubleArrayElement(value));
	}

	public char[] getCharArray(String name) {
		return getElement(name, CharArrayElement.class).value;
	}

	public void setCharArray(String name, char[] value) {
		map.put(name, new CharArrayElement(value));
	}

	public String[] getStringArray(String name) {
		return getElement(name, StringArrayElement.class).value;
	}

	public void setStringArray(String name, String[] value) {
		map.put(name, new StringArrayElement(value));
	}

	public ObjectElement[] getObjectArray(String name) {
		return getElement(name, ObjectArrayElement.class).value;
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
