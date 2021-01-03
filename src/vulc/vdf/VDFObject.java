/*******************************************************************************
 * Copyright 2019 - 2021 Vulcalien
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
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import vulc.vdf.io.binary.BinaryVDF;
import vulc.vdf.io.text.TextVDF;
import vulc.vdf.io.text.VDFParseException;

/**
 * An instance of this class represents a VDF object, "<i>a structure that contains unordered
 * key-value pairs</i>".
 * 
 * @author Vulcalien
 */
public class VDFObject {

	private final HashMap<String, Object> map = new HashMap<String, Object>();

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
	 * Returns the element associated with the specified key or {@code null} if this object contains
	 * no element associated with the key.
	 * 
	 * <p>This should be avoided if the type is known. For example, if we know that the key 'x' is
	 * associated with an {@code Integer} element then {@code getInt("x")} should be preferred,
	 * since it will return a primitive {@code int} value.
	 * 
	 * @param   key  the key associated with the element to return
	 * @return  the element associated with the specified key, or {@code null} if this object
	 *          contains no element associated with the key
	 * @see     java.util.HashMap#get(Object)
	 * @see     vulc.vdf.Element
	 */
	public Object getElement(String key) {
		return map.get(key);
	}

	/**
	 * Associates the given element with the given key. If this object contained an element
	 * associated with that key, the old element is replaced.
	 * 
	 * @param   key  the key to associate with the element
	 * @param   e    the element to associate with the key
	 * @return  the old element associated with the specified key, or {@code null} if the key was
	 *          not associated with any element
	 * @see     java.util.HashMap#put(Object, Object)
	 */
	public Object setElement(String key, Object e) {
		if(key == null) throw new NullPointerException();
		return map.put(key, e);
	}

	/**
	 * Copies all of the associations from the given object to this object. If an element in this
	 * object and an element in the given object are associated with the same key, the element in
	 * the given object replaces the element in this object.
	 * 
	 * @param  object  the object containing the associations to copy
	 * @see    java.util.HashMap#putAll(Map)
	 */
	public void setAll(VDFObject object) {
		this.map.putAll(object.map);
	}

	/**
	 * Removes the element associated with the specified key if present.
	 * 
	 * @param   key  the key associated with the element to remove
	 * @return  the removed element, or {@code null} if the key was not associated with any element
	 * @see     java.util.HashMap#remove(Object)
	 */
	public Object removeElement(String key) {
		return map.remove(key);
	}

	/**
	 * Removes all the elements.
	 * 
	 * @see  java.util.HashMap#clear()
	 */
	public void clear() {
		map.clear();
	}

	// getters and setters

	public boolean getBoolean(String key) {
		return (Boolean) getElement(key);
	}

	public Object setBoolean(String key, boolean element) {
		return setElement(key, element);
	}

	public byte getByte(String key) {
		return (Byte) getElement(key);
	}

	public Object setByte(String key, byte element) {
		return setElement(key, element);
	}

	public short getShort(String key) {
		return (Short) getElement(key);
	}

	public Object setShort(String key, short element) {
		return setElement(key, element);
	}

	public int getInt(String key) {
		return (Integer) getElement(key);
	}

	public Object setInt(String key, int element) {
		return setElement(key, element);
	}

	public long getLong(String key) {
		return (Long) getElement(key);
	}

	public Object setLong(String key, long element) {
		return setElement(key, element);
	}

	public float getFloat(String key) {
		return (Float) getElement(key);
	}

	public Object setFloat(String key, float element) {
		return setElement(key, element);
	}

	public double getDouble(String key) {
		return (Double) getElement(key);
	}

	public Object setDouble(String key, double element) {
		return setElement(key, element);
	}

	public char getChar(String key) {
		return (Character) getElement(key);
	}

	public Object setChar(String key, char element) {
		return setElement(key, element);
	}

	public String getString(String key) {
		return (String) getElement(key);
	}

	public Object setString(String key, String element) {
		return setElement(key, element);
	}

	public VDFObject getObject(String key) {
		return (VDFObject) getElement(key);
	}

	public Object setObject(String key, VDFObject element) {
		return setElement(key, element);
	}

	public VDFList getList(String key) {
		return (VDFList) getElement(key);
	}

	public Object setList(String key, VDFList element) {
		return setElement(key, element);
	}

	// arrays

	public boolean[] getBooleanArray(String key) {
		return (boolean[]) getElement(key);
	}

	public Object setBooleanArray(String key, boolean[] element) {
		return setElement(key, element);
	}

	public byte[] getByteArray(String key) {
		return (byte[]) getElement(key);
	}

	public Object setByteArray(String key, byte[] element) {
		return setElement(key, element);
	}

	public short[] getShortArray(String key) {
		return (short[]) getElement(key);
	}

	public Object setShortArray(String key, short[] element) {
		return setElement(key, element);
	}

	public int[] getIntArray(String key) {
		return (int[]) getElement(key);
	}

	public Object setIntArray(String key, int[] element) {
		return setElement(key, element);
	}

	public long[] getLongArray(String key) {
		return (long[]) getElement(key);
	}

	public Object setLongArray(String key, long[] element) {
		return setElement(key, element);
	}

	public float[] getFloatArray(String key) {
		return (float[]) getElement(key);
	}

	public Object setFloatArray(String key, float[] element) {
		return setElement(key, element);
	}

	public double[] getDoubleArray(String key) {
		return (double[]) getElement(key);
	}

	public Object setDoubleArray(String key, double[] element) {
		return setElement(key, element);
	}

	public char[] getCharArray(String key) {
		return (char[]) getElement(key);
	}

	public Object setCharArray(String key, char[] element) {
		return setElement(key, element);
	}

	public String[] getStringArray(String key) {
		return (String[]) getElement(key);
	}

	public Object setStringArray(String key, String[] element) {
		return setElement(key, element);
	}

	public VDFObject[] getObjectArray(String key) {
		return (VDFObject[]) getElement(key);
	}

	public Object setObjectArray(String key, VDFObject[] element) {
		return setElement(key, element);
	}

	public VDFList[] getListArray(String key) {
		return (VDFList[]) getElement(key);
	}

	public Object setListArray(String key, VDFList[] element) {
		return setElement(key, element);
	}

	// binary IO

	/**
	 * Reads an object from a {@code DataInputStream} and adds the elements, each associated with
	 * its key, to this object.
	 * 
	 * <p>This method does not empty this object, but it may replace an old value if a new value
	 * associated with the same key is found.
	 * 
	 * @param   in  a data input stream
	 * @return  this object
	 * 
	 * @throws  EOFException  if the stream reaches the end before an object can be read
	 * @throws  IOException   if an IO error occurs
	 * @see     vulc.vdf.io.binary.BinaryVDF#deserialize(DataInputStream)
	 */
	public VDFObject deserialize(DataInputStream in) throws IOException {
		this.setAll((VDFObject) BinaryVDF.deserialize(in));
		return this;
	}

	/**
	 * Reads an object from a {@code InputStream} and adds the elements, each associated with its
	 * key, to this object.
	 * 
	 * <p>This method does not empty this object, but it may replace an old value if a new value
	 * associated with the same key is found.
	 * 
	 * @param   in  an input stream
	 * @return  this object
	 * 
	 * @throws  EOFException  if the stream reaches the end before an object can be read
	 * @throws  IOException   if an IO error occurs
	 * 
	 * @see     vulc.vdf.io.binary.BinaryVDF#deserialize(InputStream)
	 */
	public VDFObject deserialize(InputStream in) throws IOException {
		setAll((VDFObject) BinaryVDF.deserialize(in));
		return this;
	}

	/**
	 * Reads an object from a file and adds the elements, each associated with its key, to this
	 * object.
	 * 
	 * <p>This method does not empty this object, but it may replace an old value if a new value
	 * associated with the same key is found.
	 * 
	 * @param   file  the file to read
	 * @return  this object
	 * 
	 * @throws  EOFException  if the stream reaches the end before an object can be read
	 * @throws  IOException   if an IO error occurs
	 * 
	 * @see     vulc.vdf.io.binary.BinaryVDF#deserialize(File)
	 */
	public VDFObject deserialize(File file) throws IOException {
		setAll((VDFObject) BinaryVDF.deserialize(file));
		return this;
	}

	/**
	 * Writes this object to a {@code DataOutputStream}.
	 * 
	 * @param   out  a data output stream
	 * @throws  IOException  if an IO error occurs
	 * @see     vulc.vdf.io.binary.BinaryVDF#serialize(DataOutputStream, Object)
	 */
	public void serialize(DataOutputStream out) throws IOException {
		BinaryVDF.serialize(out, this);
	}

	/**
	 * Writes this object to an {@code OutputStream}.
	 * 
	 * @param   out  an output stream
	 * @throws  IOException  if an IO error occurs
	 * @see     vulc.vdf.io.binary.BinaryVDF#serialize(OutputStream, Object)
	 */
	public void serialize(OutputStream out) throws IOException {
		BinaryVDF.serialize(out, this);
	}

	/**
	 * Writes this object to a file.
	 * 
	 * @param   file  the file to write this object to
	 * @throws  IOException  if an IO error occurs
	 * @see     vulc.vdf.io.binary.BinaryVDF#serialize(File, Object)
	 */
	public void serialize(File file) throws IOException {
		BinaryVDF.serialize(file, this);
	}

	// text IO

	/**
	 * Reads an object from a {@code Reader} and adds the elements, each associated with its key,
	 * to this object.
	 * 
	 * <p>This method does not empty this object, but it may replace an old value if a new value
	 * associated with the same key is found.
	 * 
	 * @param   in  a reader
	 * @return  this object
	 * 
	 * @throws  IOException  if an IO error occurs
	 * @throws  VDFParseException  if the file could not be parsed properly
	 * @see     vulc.vdf.io.text.TextVDF#deserialize(Reader)
	 */
	public VDFObject parse(Reader in) throws IOException {
		setAll((VDFObject) TextVDF.deserialize(in));
		return this;
	}

	// TODO parse(File)

	/**
	 * Reads an object from a {@code String} and adds the elements, each associated with its key,
	 * to this object.
	 * 
	 * <p>This method does not empty this object, but it may replace an old value if a new value
	 * associated with the same key is found.
	 * 
	 * @param   string  the text to parse
	 * @return  this object
	 * @see     vulc.vdf.io.text.TextVDF#fromString(String)
	 */
	public VDFObject parse(String string) {
		setAll((VDFObject) TextVDF.fromString(string));
		return this;
	}

	/**
	 * Writes this object to a {@code Writer}.
	 * 
	 * @param   out  a writer
	 * @param   format  a flag stating if the output should be formatted or not
	 *                  ({@code true} = formatted, {@code false} = unformatted)
	 * @throws  IOException  if an IO error occurs
	 */
	public void write(Writer out, boolean format) throws IOException {
		TextVDF.serialize(out, this, format);
	}

	// TODO write(File)

	/**
	 * Returns a string representation of this object.
	 * The flag {@code format} states if the string should be formatted or not.
	 * 
	 * <p>{@code toString()} can be used instead of {@code toString(false)}.
	 * 
	 * @param   format  a flag stating if the output string should be formatted or not
	 *                  ({@code true} = formatted, {@code false} = unformatted)
	 * @return  a string representation of this object
	 * @see     vulc.vdf.io.text.TextVDF#toString(Object, boolean)
	 */
	public String toString(boolean format) {
		return TextVDF.toString(this, format);
	}

	/**
	 * Returns an unformatted string representation of this object.
	 * This method is equivalent to {@code toString(false)}.
	 * 
	 * @return  an unformatted string representation of this object
	 * @see     vulc.vdf.VDFObject#toString(boolean)
	 */
	public String toString() {
		return toString(false);
	}

	/**
	 * Compares this {@code VDFObject} to the given {@code Object}.
	 * 
	 * <p>This object and the given {@code Object} are equal if all of the following are true:
	 * <ul>
	 * <li>the given object is an instance of {@code VDFObject}
	 * <li>this object's size is equal to the other object's size
	 * <li>both objects contain equal elements associated with the same key
	 * </ul>
	 * 
	 * @param   obj  the object to compare
	 * @return  {@code true} if this object and the given one are equal; {@code false} otherwise
	 */
	public boolean equals(Object obj) {
		if(obj instanceof VDFObject) {
			VDFObject toCompare = (VDFObject) obj;

			if(this.size() != toCompare.size()) {
				return false; // the objects have different size
			}

			for(String key : this.keySet()) {
				if(!Objects.deepEquals(this.getElement(key), toCompare.getElement(key))) {
					return false; // two elements associated with the same key are not equal
				}
			}
			return true; // the two objects have the same size and each of their elements is equal
		}
		return false; // 'obj' is not a VDFObject
	}

}
