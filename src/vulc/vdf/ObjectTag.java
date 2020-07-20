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

public class ObjectTag extends Tag<ObjectTag> {

	private final HashMap<String, Tag<?>> map = new HashMap<String, Tag<?>>();

	protected ObjectTag get() {
		return this;
	}

	public int size() {
		return map.size();
	}

	public Set<String> keySet() {
		return map.keySet();
	}

	public void removeTag(String name) {
		map.remove(name);
	}

	public void clear() {
		map.clear();
	}

	private <T> T getTag(String name, Class<T> type) {
		if(!map.containsKey(name)) throw new NoSuchElementException(name);

		Tag<?> tag = map.get(name);
		if(type.isInstance(tag))
		    return type.cast(tag);
		else
		    throw new ClassCastException(type.getName());
	}

	// useful when data type is not known
	public Object getValue(String name) {
		if(!map.containsKey(name)) throw new NoSuchElementException(name);

		Tag<?> tag = map.get(name);
		return tag.get();
	}

	// getters and setters

	public boolean getBoolean(String name) {
		return getTag(name, BooleanTag.class).get();
	}

	public void setBoolean(String name, boolean value) {
		map.put(name, new BooleanTag(value));
	}

	public char getChar(String name) {
		return getTag(name, CharTag.class).get();
	}

	public void setChar(String name, char value) {
		map.put(name, new CharTag(value));
	}

	public byte getByte(String name) {
		return getTag(name, ByteTag.class).get();
	}

	public void setByte(String name, byte value) {
		map.put(name, new ByteTag(value));
	}

	public short getShort(String name) {
		return getTag(name, ShortTag.class).get();
	}

	public void setShort(String name, short value) {
		map.put(name, new ShortTag(value));
	}

	public int getInt(String name) {
		return getTag(name, IntTag.class).get();
	}

	public void setInt(String name, int value) {
		map.put(name, new IntTag(value));
	}

	public long getLong(String name) {
		return getTag(name, LongTag.class).get();
	}

	public void setLong(String name, long value) {
		map.put(name, new LongTag(value));
	}

	public float getFloat(String name) {
		return getTag(name, FloatTag.class).get();
	}

	public void setFloat(String name, float value) {
		map.put(name, new FloatTag(value));
	}

	public double getDouble(String name) {
		return getTag(name, DoubleTag.class).get();
	}

	public void setDouble(String name, double value) {
		map.put(name, new DoubleTag(value));
	}

	public String getString(String name) {
		return getTag(name, StringTag.class).get();
	}

	public void setString(String name, String value) {
		map.put(name, new StringTag(value));
	}

	public ObjectTag getObject(String name) {
		return getTag(name, ObjectTag.class);
	}

	public void setObject(String name, ObjectTag objectTag) {
		map.put(name, objectTag);
	}

	// arrays

	public boolean[] getBooleanArray(String name) {
		return getTag(name, BooleanArrayTag.class).get();
	}

	public void setBooleanArray(String name, boolean[] value) {
		map.put(name, new BooleanArrayTag(value));
	}

	public char[] getCharArray(String name) {
		return getTag(name, CharArrayTag.class).get();
	}

	public void setCharArray(String name, char[] value) {
		map.put(name, new CharArrayTag(value));
	}

	public byte[] getByteArray(String name) {
		return getTag(name, ByteArrayTag.class).get();
	}

	public void setByteArray(String name, byte[] value) {
		map.put(name, new ByteArrayTag(value));
	}

	public short[] getShortArray(String name) {
		return getTag(name, ShortArrayTag.class).get();
	}

	public void setShortArray(String name, short[] value) {
		map.put(name, new ShortArrayTag(value));
	}

	public int[] getIntArray(String name) {
		return getTag(name, IntArrayTag.class).get();
	}

	public void setIntArray(String name, int[] value) {
		map.put(name, new IntArrayTag(value));
	}

	public long[] getLongArray(String name) {
		return getTag(name, LongArrayTag.class).get();
	}

	public void setLongArray(String name, long[] value) {
		map.put(name, new LongArrayTag(value));
	}

	public float[] getFloatArray(String name) {
		return getTag(name, FloatArrayTag.class).get();
	}

	public void setFloatArray(String name, float[] value) {
		map.put(name, new FloatArrayTag(value));
	}

	public double[] getDoubleArray(String name) {
		return getTag(name, DoubleArrayTag.class).get();
	}

	public void setDoubleArray(String name, double[] value) {
		map.put(name, new DoubleArrayTag(value));
	}

	public String[] getStringArray(String name) {
		return getTag(name, StringArrayTag.class).get();
	}

	public void setStringArray(String name, String[] value) {
		map.put(name, new StringArrayTag(value));
	}

	public ObjectTag[] getObjectArray(String name) {
		return getTag(name, ObjectArrayTag.class).get();
	}

	public void setObjectArray(String name, ObjectTag[] value) {
		map.put(name, new ObjectArrayTag(value));
	}

	// IO

	public void serialize(DataOutputStream out) throws IOException {
		Set<String> keys = map.keySet();

		// write size
		out.writeInt(keys.size());

		for(String name : keys) {
			Tag<?> tag = map.get(name);

			// write next tag's code
			out.writeByte(TypeTable.getCode(tag.getClass()));

			// write its name
			out.writeUTF(name);

			// serialize its data
			tag.serialize(out);
		}
	}

	public void deserialize(DataInputStream in) throws IOException {
		// read size
		int size = in.readInt();

		for(int i = 0; i < size; i++) {
			// read tag's code
			Tag<?> tag = TypeTable.getTag(in.readByte());

			// read its name
			String name = in.readUTF();

			// deserialize its data
			tag.deserialize(in);

			map.put(name, tag);
		}
	}

}
