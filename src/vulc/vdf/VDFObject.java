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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Set;

import vulc.vdf.io.binary.BinaryIO;
import vulc.vdf.io.text.TextIO;

public class VDFObject extends Element {

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

	public void setElement(String name, Element e) {
		map.put(name, e);
	}

	public void removeElement(String name) {
		map.remove(name);
	}

	public void clear() {
		map.clear();
	}

	public Object get() {
		return this;
	}

	// getters and setters

	public boolean getBoolean(String name) {
		return ((BooleanElement) getElement(name)).value;
	}

	public void setBoolean(String name, boolean value) {
		setElement(name, new BooleanElement(value));
	}

	public byte getByte(String name) {
		return ((ByteElement) getElement(name)).value;
	}

	public void setByte(String name, byte value) {
		setElement(name, new ByteElement(value));
	}

	public short getShort(String name) {
		return ((ShortElement) getElement(name)).value;
	}

	public void setShort(String name, short value) {
		setElement(name, new ShortElement(value));
	}

	public int getInt(String name) {
		return ((IntElement) getElement(name)).value;
	}

	public void setInt(String name, int value) {
		setElement(name, new IntElement(value));
	}

	public long getLong(String name) {
		return ((LongElement) getElement(name)).value;
	}

	public void setLong(String name, long value) {
		setElement(name, new LongElement(value));
	}

	public float getFloat(String name) {
		return ((FloatElement) getElement(name)).value;
	}

	public void setFloat(String name, float value) {
		setElement(name, new FloatElement(value));
	}

	public double getDouble(String name) {
		return ((DoubleElement) getElement(name)).value;
	}

	public void setDouble(String name, double value) {
		setElement(name, new DoubleElement(value));
	}

	public char getChar(String name) {
		return ((CharElement) getElement(name)).value;
	}

	public void setChar(String name, char value) {
		setElement(name, new CharElement(value));
	}

	public String getString(String name) {
		return ((StringElement) getElement(name)).value;
	}

	public void setString(String name, String value) {
		setElement(name, new StringElement(value));
	}

	public VDFObject getObject(String name) {
		return (VDFObject) getElement(name);
	}

	public void setObject(String name, VDFObject objectElement) {
		setElement(name, objectElement);
	}

	// TODO test these
	public VDFList getList(String name) {
		return (VDFList) getElement(name);
	}

	public void setList(String name, VDFList listElement) {
		setElement(name, listElement);
	}

	// arrays

	public boolean[] getBooleanArray(String name) {
		return ((BooleanArrayElement) getElement(name)).value;
	}

	public void setBooleanArray(String name, boolean[] value) {
		setElement(name, new BooleanArrayElement(value));
	}

	public byte[] getByteArray(String name) {
		return ((ByteArrayElement) getElement(name)).value;
	}

	public void setByteArray(String name, byte[] value) {
		setElement(name, new ByteArrayElement(value));
	}

	public short[] getShortArray(String name) {
		return ((ShortArrayElement) getElement(name)).value;
	}

	public void setShortArray(String name, short[] value) {
		setElement(name, new ShortArrayElement(value));
	}

	public int[] getIntArray(String name) {
		return ((IntArrayElement) getElement(name)).value;
	}

	public void setIntArray(String name, int[] value) {
		setElement(name, new IntArrayElement(value));
	}

	public long[] getLongArray(String name) {
		return ((LongArrayElement) getElement(name)).value;
	}

	public void setLongArray(String name, long[] value) {
		setElement(name, new LongArrayElement(value));
	}

	public float[] getFloatArray(String name) {
		return ((FloatArrayElement) getElement(name)).value;
	}

	public void setFloatArray(String name, float[] value) {
		setElement(name, new FloatArrayElement(value));
	}

	public double[] getDoubleArray(String name) {
		return ((DoubleArrayElement) getElement(name)).value;
	}

	public void setDoubleArray(String name, double[] value) {
		setElement(name, new DoubleArrayElement(value));
	}

	public char[] getCharArray(String name) {
		return ((CharArrayElement) getElement(name)).value;
	}

	public void setCharArray(String name, char[] value) {
		setElement(name, new CharArrayElement(value));
	}

	public String[] getStringArray(String name) {
		return ((StringArrayElement) getElement(name)).value;
	}

	public void setStringArray(String name, String[] value) {
		setElement(name, new StringArrayElement(value));
	}

	public VDFObject[] getObjectArray(String name) {
		return ((ObjectArrayElement) getElement(name)).value;
	}

	public void setObjectArray(String name, VDFObject[] value) {
		setElement(name, new ObjectArrayElement(value));
	}

	// TODO test these
	public VDFList[] getListArray(String name) {
		return ((ListArrayElement) getElement(name)).value;
	}

	public void setListArray(String name, VDFList[] value) {
		setElement(name, new ListArrayElement(value));
	}

	// binary IO

	public VDFObject deserialize(DataInputStream in) throws IOException {
		return BinaryIO.deserialize(in, this);
	}

	public VDFObject deserialize(InputStream in) throws IOException {
		return deserialize(new DataInputStream(in));
	}

	public VDFObject deserialize(File file) throws IOException {
		try(InputStream in = new BufferedInputStream(new FileInputStream(file))) {
			return deserialize(in);
		}
	}

	public void serialize(DataOutputStream out) throws IOException {
		BinaryIO.serialize(out, this);
	}

	public void serialize(OutputStream out) throws IOException {
		serialize(new DataOutputStream(out));
	}

	public void serialize(File file) throws IOException {
		try(OutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
			serialize(out);
		}
	}

	// text IO

	public VDFObject parse(String string) {
		return TextIO.deserialize(string, this);
	}

	public String toString(boolean format) {
		return TextIO.stringify(this, format);
	}

	public String toString() {
		return toString(false);
	}

}
