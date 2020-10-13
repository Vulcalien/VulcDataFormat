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

	public int size() {
		return map.size();
	}

	public Set<String> keySet() {
		return map.keySet();
	}

	private <T> T getTag(String name, Class<T> type) {
		Tag<?> tag = map.get(name);

		if(type.isInstance(tag)) {
			return type.cast(tag);
		} else if(tag == null) {
			throw new NoSuchElementException(name);
		} else {
			throw new ClassCastException(type.getName());
		}
	}

	public Object getValue(String name) {
		Tag<?> tag = map.get(name);

		if(tag != null) {
			return tag.get();
		} else {
			throw new NoSuchElementException(name);
		}
	}

	public Class<?> getType(String name) {
		return getValue(name).getClass();
	}

	public void removeTag(String name) {
		map.remove(name);
	}

	public void clear() {
		map.clear();
	}

	protected ObjectTag get() {
		return this;
	}

	// getters and setters

	public boolean getBoolean(String name) {
		return getTag(name, BooleanTag.class).value;
	}

	public void setBoolean(String name, boolean value) {
		map.put(name, new BooleanTag(value));
	}

	public char getChar(String name) {
		return getTag(name, CharTag.class).value;
	}

	public void setChar(String name, char value) {
		map.put(name, new CharTag(value));
	}

	public byte getByte(String name) {
		return getTag(name, ByteTag.class).value;
	}

	public void setByte(String name, byte value) {
		map.put(name, new ByteTag(value));
	}

	public short getShort(String name) {
		return getTag(name, ShortTag.class).value;
	}

	public void setShort(String name, short value) {
		map.put(name, new ShortTag(value));
	}

	public int getInt(String name) {
		return getTag(name, IntTag.class).value;
	}

	public void setInt(String name, int value) {
		map.put(name, new IntTag(value));
	}

	public long getLong(String name) {
		return getTag(name, LongTag.class).value;
	}

	public void setLong(String name, long value) {
		map.put(name, new LongTag(value));
	}

	public float getFloat(String name) {
		return getTag(name, FloatTag.class).value;
	}

	public void setFloat(String name, float value) {
		map.put(name, new FloatTag(value));
	}

	public double getDouble(String name) {
		return getTag(name, DoubleTag.class).value;
	}

	public void setDouble(String name, double value) {
		map.put(name, new DoubleTag(value));
	}

	public String getString(String name) {
		return getTag(name, StringTag.class).value;
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
		return getTag(name, BooleanArrayTag.class).value;
	}

	public void setBooleanArray(String name, boolean[] value) {
		map.put(name, new BooleanArrayTag(value));
	}

	public char[] getCharArray(String name) {
		return getTag(name, CharArrayTag.class).value;
	}

	public void setCharArray(String name, char[] value) {
		map.put(name, new CharArrayTag(value));
	}

	public byte[] getByteArray(String name) {
		return getTag(name, ByteArrayTag.class).value;
	}

	public void setByteArray(String name, byte[] value) {
		map.put(name, new ByteArrayTag(value));
	}

	public short[] getShortArray(String name) {
		return getTag(name, ShortArrayTag.class).value;
	}

	public void setShortArray(String name, short[] value) {
		map.put(name, new ShortArrayTag(value));
	}

	public int[] getIntArray(String name) {
		return getTag(name, IntArrayTag.class).value;
	}

	public void setIntArray(String name, int[] value) {
		map.put(name, new IntArrayTag(value));
	}

	public long[] getLongArray(String name) {
		return getTag(name, LongArrayTag.class).value;
	}

	public void setLongArray(String name, long[] value) {
		map.put(name, new LongArrayTag(value));
	}

	public float[] getFloatArray(String name) {
		return getTag(name, FloatArrayTag.class).value;
	}

	public void setFloatArray(String name, float[] value) {
		map.put(name, new FloatArrayTag(value));
	}

	public double[] getDoubleArray(String name) {
		return getTag(name, DoubleArrayTag.class).value;
	}

	public void setDoubleArray(String name, double[] value) {
		map.put(name, new DoubleArrayTag(value));
	}

	public String[] getStringArray(String name) {
		return getTag(name, StringArrayTag.class).value;
	}

	public void setStringArray(String name, String[] value) {
		map.put(name, new StringArrayTag(value));
	}

	public ObjectTag[] getObjectArray(String name) {
		return getTag(name, ObjectArrayTag.class).value;
	}

	public void setObjectArray(String name, ObjectTag[] value) {
		map.put(name, new ObjectArrayTag(value));
	}

	// IO

	public void serialize(DataOutputStream out) throws IOException {
		for(String name : keySet()) {
			Tag<?> tag = map.get(name);

			out.writeByte(TypeTable.getCode(tag.getClass()));	// write code
			out.writeUTF(name);									// write name

			tag.serialize(out);									// serialize
		}
		out.writeByte(-1);										// write end mark
	}

	public void deserialize(DataInputStream in) throws IOException {
		byte code;
		while((code = in.readByte()) != -1) {					// read code, until end mark (-1) is found
			Tag<?> tag = TypeTable.getTag(code);

			String name = in.readUTF();							// read name

			tag.deserialize(in);								// deserialize

			map.put(name, tag);									// add tag to this object
		}
	}

}
