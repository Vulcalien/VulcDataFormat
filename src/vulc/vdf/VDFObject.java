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
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Set;

import vulc.vdf.io.binary.BinaryIO;
import vulc.vdf.io.text.TextIO;

public class VDFObject extends Element {

	private final HashMap<String, Element> map = new HashMap<String, Element>();

	/**
	 * Returns the number of elements contained in this object.
	 * 
	 * @return the number of elements contained in this object
	 * @see    java.util.HashMap#size()
	 */
	public int size() {
		return map.size();
	}

	/**
	 * Returns a Set of the keys associated with an element in this object.
	 * 
	 * @return  a Set of the keys
	 * @see     java.util.HashMap#keySet()
	 */
	public Set<String> keySet() {
		return map.keySet();
	}

	/**
	 * Returns an {@code Element} or {@code null} if the object contains no element associated with
	 * the specified key.
	 * 
	 * <p>This should be avoided if the type is known. For example, if we know that the key 'x' is
	 * associated to an {@code IntElement} then {@code getInt("x")} should be preferred, since it
	 * will return a primitive {@code int} value.
	 * 
	 * @param   name  the key
	 * @return  the element associated with the specified key, or null if the object contains no
	 *          element associated with the specified key
	 * @see     java.util.HashMap#get(Object)
	 * @see     vulc.vdf.Element
	 */
	public Element getElement(String name) {
		return map.get(name);
	}

	/**
	 * Associates the given element with the given name. If this object contained an element
	 * associated to that name, the old element is replaced.
	 * 
	 * @param  name  the key which the element will be associated
	 * @param  e     the element
	 * @see    java.util.HashMap#put(Object, Object)
	 */
	public void setElement(String name, Element e) { // TODO add return old value
		map.put(name, e);
	}

	/**
	 * Removes the element associated with the specified key if present.
	 * 
	 * @param  name  the key associated with the element to be removed
	 * @see    java.util.HashMap#remove(Object)
	 */
	public void removeElement(String name) { // TODO add return removed value
		map.remove(name);
	}

	/**
	 * Removes all the elements.
	 * 
	 * @see  java.util.HashMap#clear()
	 */
	public void clear() {
		map.clear();
	}

	/**
	 * Returns this object.
	 * 
	 * @return  this object
	 */
	public Object get() {
		return this;
	}

	// getters and setters

	public boolean getBoolean(String name) {
		return ((BooleanElement) getElement(name)).value;
	}

	public void setBoolean(String name, boolean value) {
		// TODO add return old value
		// something like
		// return setElement(name, new BooleanElement(value)).value;
		// should be enought
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

	public VDFList[] getListArray(String name) {
		return ((ListArrayElement) getElement(name)).value;
	}

	public void setListArray(String name, VDFList[] value) {
		setElement(name, new ListArrayElement(value));
	}

	// binary IO

	/**
	 * Reads an object from a {@code DataInputStream} and adds the element to this object, without
	 * removing contained elements.
	 * 
	 * @param   in  a data input stream
	 * @return  this object
	 * 
	 * @throws  EOFException  if the stream reaches the end before an object can be read
	 * @throws  IOException   if an IO error occurs
	 */
	public VDFObject deserialize(DataInputStream in) throws IOException {
		BinaryIO.deserialize(in, this);
		return this;
	}

	/**
	 * Reads an object from a {@code InputStream} and adds the element to this object, without
	 * removing contained elements.
	 * 
	 * @param   in  an input stream
	 * @return  this object
	 * 
	 * @throws  EOFException  if the stream reaches the end before an object can be read
	 * @throws  IOException   if an IO error occurs
	 * 
	 * @see     vulc.vdf.VDFObject#deserialize(DataInputStream)
	 */
	public VDFObject deserialize(InputStream in) throws IOException {
		return deserialize(new DataInputStream(in));
	}

	/**
	 * Reads an object from a {@code FileInputStream} and adds the element to this object, without
	 * removing contained elements.
	 * 
	 * @param   file  the file that will be read
	 * @return  this object
	 * 
	 * @throws  EOFException  if the stream reaches the end before an object can be read
	 * @throws  IOException   if an IO error occurs
	 * 
	 * @see     vulc.vdf.VDFObject#deserialize(DataInputStream)
	 */
	public VDFObject deserialize(File file) throws IOException {
		try(InputStream in = new BufferedInputStream(new FileInputStream(file))) {
			return deserialize(in);
		}
	}

	/**
	 * Writes an object to a {@code DataOutputStream}.
	 * 
	 * @param   out  a data output stream
	 * @throws  IOException  if an IO error occurs
	 */
	public void serialize(DataOutputStream out) throws IOException {
		BinaryIO.serialize(out, this);
	}

	/**
	 * Writes an object to an {@code OutputStream}.
	 * 
	 * @param   out  an output stream
	 * @throws  IOException  if an IO error occurs
	 * @see     vulc.vdf.VDFObject#serialize(DataOutputStream)
	 */
	public void serialize(OutputStream out) throws IOException {
		serialize(new DataOutputStream(out));
	}

	/**
	 * Writes an object to an {@code OutputStream}.
	 * 
	 * @param   file  the file to which the object will be written
	 * @throws  IOException  if an IO error occurs
	 * @see     vulc.vdf.VDFObject#serialize(DataOutputStream)
	 */
	public void serialize(File file) throws IOException {
		try(OutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
			serialize(out);
		}
	}

	// text IO

	// TODO documentation here

	public VDFObject parse(String string) {
		TextIO.deserialize(string, this);
		return this;
	}

	public String toString(boolean format) {
		return TextIO.stringify(this, format);
	}

	public String toString() {
		return toString(false);
	}

}
