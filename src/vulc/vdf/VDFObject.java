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
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

import vulc.vdf.io.binary.BinaryIO;
import vulc.vdf.io.text.TextIO;
import vulc.vdf.io.text.VDFParseException;

/**
 * An instance of this class represents a VDF object, "<i>a structure that contains unordered
 * key-value pairs</i>".
 * 
 * <p>TO-DOC
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
	 * @param   name  the key
	 * @return  the element associated with the specified key, or {@code null} if this object
	 *          contains no element associated with the key
	 * @see     java.util.HashMap#get(Object)
	 * @see     vulc.vdf.Element
	 */
	public Object getElement(String name) {
		return map.get(name);
	}

	/**
	 * Associates the given element with the given key. If this object contained an element
	 * associated with that key, the old element is replaced.
	 * 
	 * @param   name  the key that will be associated with the element
	 * @param   e     the element to set
	 * @return  the old element associated with the specified key, or {@code null} if the key was
	 *          not associated with any element
	 * @see     java.util.HashMap#put(Object, Object)
	 */
	public Object setElement(String name, Object e) {
		if(name == null) throw new NullPointerException();
		return map.put(name, e);
	}

	/**
	 * Removes the element associated with the specified key if present.
	 * 
	 * @param   name  the key associated with the element to remove
	 * @return  the removed element, or {@code null} if the key was not associated with any element
	 * @see     java.util.HashMap#remove(Object)
	 */
	public Object removeElement(String name) {
		return map.remove(name);
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

	public boolean getBoolean(String name) {
		return (Boolean) getElement(name);
	}

	public Object setBoolean(String name, boolean value) {
		return setElement(name, value);
	}

	public byte getByte(String name) {
		return (Byte) getElement(name);
	}

	public Object setByte(String name, byte value) {
		return setElement(name, value);
	}

	public short getShort(String name) {
		return (Short) getElement(name);
	}

	public Object setShort(String name, short value) {
		return setElement(name, value);
	}

	public int getInt(String name) {
		return (Integer) getElement(name);
	}

	public Object setInt(String name, int value) {
		return setElement(name, value);
	}

	public long getLong(String name) {
		return (Long) getElement(name);
	}

	public Object setLong(String name, long value) {
		return setElement(name, value);
	}

	public float getFloat(String name) {
		return (Float) getElement(name);
	}

	public Object setFloat(String name, float value) {
		return setElement(name, value);
	}

	public double getDouble(String name) {
		return (Double) getElement(name);
	}

	public Object setDouble(String name, double value) {
		return setElement(name, value);
	}

	public char getChar(String name) {
		return (Character) getElement(name);
	}

	public Object setChar(String name, char value) {
		return setElement(name, value);
	}

	public String getString(String name) {
		return (String) getElement(name);
	}

	public Object setString(String name, String value) {
		return setElement(name, value);
	}

	public VDFObject getObject(String name) {
		return (VDFObject) getElement(name);
	}

	public Object setObject(String name, VDFObject objectElement) {
		return setElement(name, objectElement);
	}

	public VDFList getList(String name) {
		return (VDFList) getElement(name);
	}

	public Object setList(String name, VDFList listElement) {
		return setElement(name, listElement);
	}

	// arrays

	public boolean[] getBooleanArray(String name) {
		return (boolean[]) getElement(name);
	}

	public Object setBooleanArray(String name, boolean[] value) {
		return setElement(name, value);
	}

	public byte[] getByteArray(String name) {
		return (byte[]) getElement(name);
	}

	public Object setByteArray(String name, byte[] value) {
		return setElement(name, value);
	}

	public short[] getShortArray(String name) {
		return (short[]) getElement(name);
	}

	public Object setShortArray(String name, short[] value) {
		return setElement(name, value);
	}

	public int[] getIntArray(String name) {
		return (int[]) getElement(name);
	}

	public Object setIntArray(String name, int[] value) {
		return setElement(name, value);
	}

	public long[] getLongArray(String name) {
		return (long[]) getElement(name);
	}

	public Object setLongArray(String name, long[] value) {
		return setElement(name, value);
	}

	public float[] getFloatArray(String name) {
		return (float[]) getElement(name);
	}

	public Object setFloatArray(String name, float[] value) {
		return setElement(name, value);
	}

	public double[] getDoubleArray(String name) {
		return (double[]) getElement(name);
	}

	public Object setDoubleArray(String name, double[] value) {
		return setElement(name, value);
	}

	public char[] getCharArray(String name) {
		return (char[]) getElement(name);
	}

	public Object setCharArray(String name, char[] value) {
		return setElement(name, value);
	}

	public String[] getStringArray(String name) {
		return (String[]) getElement(name);
	}

	public Object setStringArray(String name, String[] value) {
		return setElement(name, value);
	}

	public VDFObject[] getObjectArray(String name) {
		return (VDFObject[]) getElement(name);
	}

	public Object setObjectArray(String name, VDFObject[] value) {
		return setElement(name, value);
	}

	public VDFList[] getListArray(String name) {
		return (VDFList[]) getElement(name);
	}

	public Object setListArray(String name, VDFList[] value) {
		return setElement(name, value);
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
	 */
	public VDFObject deserialize(DataInputStream in) throws IOException {
		BinaryIO.deserialize(in, this);
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
	 * @see     vulc.vdf.VDFObject#deserialize(DataInputStream)
	 */
	public VDFObject deserialize(InputStream in) throws IOException {
		return deserialize(new DataInputStream(in));
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
	 * @see     vulc.vdf.VDFObject#deserialize(DataInputStream)
	 */
	public VDFObject deserialize(File file) throws IOException {
		try(InputStream in = new BufferedInputStream(new FileInputStream(file))) {
			return deserialize(in);
		}
	}

	/**
	 * Writes this object to a {@code DataOutputStream}.
	 * 
	 * @param   out  a data output stream
	 * @throws  IOException  if an IO error occurs
	 */
	public void serialize(DataOutputStream out) throws IOException {
		BinaryIO.serialize(out, this);
	}

	/**
	 * Writes this object to an {@code OutputStream}.
	 * 
	 * @param   out  an output stream
	 * @throws  IOException  if an IO error occurs
	 * @see     vulc.vdf.VDFObject#serialize(DataOutputStream)
	 */
	public void serialize(OutputStream out) throws IOException {
		serialize(new DataOutputStream(out));
	}

	/**
	 * Writes this object to a file.
	 * 
	 * @param   file  the file to write this object to
	 * @throws  IOException  if an IO error occurs
	 * @see     vulc.vdf.VDFObject#serialize(DataOutputStream)
	 */
	public void serialize(File file) throws IOException {
		try(OutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
			serialize(out);
		}
	}

	// text IO

	/**
	 * Reads an object from a {@code Reader} and adds the elements, each associated with its key,
	 * to this object.
	 * 
	 * <p>This method does not empty this object, but it may replace an old value if a new value
	 * associated with the same key is found.
	 * 
	 * @param   in  the reader
	 * @return  this object
	 * 
	 * @throws  IOException  if an IO error occurs
	 * @throws  VDFParseException  if the file could not be parsed properly
	 */
	public VDFObject parse(Reader in) throws IOException {
		TextIO.deserialize(in, this);
		return this;
	}

	/**
	 * Reads an object from a {@code String} and adds the elements, each associated with its key,
	 * to this object.
	 * 
	 * <p>This method does not empty this object, but it may replace an old value if a new value
	 * associated with the same key is found.
	 * 
	 * @param   string  the text to parse
	 * @return  this object
	 * @see     vulc.vdf.VDFObject#parse(Reader)
	 */
	public VDFObject parse(String string) {
		try(StringReader in = new StringReader(string)) {
			return parse(in);
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}

	// TO-DOC
	public void write(Writer out, boolean format) throws IOException {
		TextIO.serialize(out, this, format);
	}

	/**
	 * Returns a string representation of this object.
	 * The flag {@code format} states if the string should be formatted or not.
	 * 
	 * <p>{@code toString()} can be used instead of {@code toString(false)}.
	 * 
	 * @param   format  a flag stating if the output string should be formatted or not
	 *                  ({@code true} = formatted, {@code false} = unformatted)
	 * @return  a string representation of this object
	 * @see     vulc.vdf.VDFObject#write(Writer, boolean)
	 */
	public String toString(boolean format) {
		try(StringWriter out = new StringWriter()) {
			write(out, format);
			return out.toString();
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
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

			for(String name : this.keySet()) {
				if(!Objects.deepEquals(this.getElement(name), toCompare.getElement(name))) {
					return false; // two elements associated with the same key are not equal
				}
			}
			return true; // the two objects have the same size and each of their elements is equal
		}
		return false; // 'obj' is not a VDFObject
	}

}
