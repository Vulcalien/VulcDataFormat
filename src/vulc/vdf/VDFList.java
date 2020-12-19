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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

import vulc.vdf.io.binary.BinaryIO;
import vulc.vdf.io.text.TextIO;
import vulc.vdf.io.text.VDFParseException;

/**
 * An instance of this class represents a VDF list, "<i>a structure that contains ordered
 * elements</i>"
 * 
 * <p>TO-DOC
 * 
 * @author Vulcalien
 */
public class VDFList implements Iterable<Object> {

	private final ArrayList<Object> list = new ArrayList<Object>();

	/**
	 * Returns the number of elements in this list.
	 * 
	 * @return  the number of elements in this list
	 * @see     java.util.ArrayList#size()
	 */
	public int size() {
		return list.size();
	}

	/**
	 * Returns the element at the specified position in this list.
	 * 
	 * <p>This should be avoided if the type is known. For example, if we know that the element at
	 * position 3 is an {@code Integer} element then {@code getInt(3)} should be preferred, since
	 * it will return a primitive {@code int} value.
	 * 
	 * @param   i  the index of the element to return
	 * @return  the element at the specified position
	 * @throws  IndexOutOfBoundsException  if the index is out of bounds
	 *          (<tt>i &lt; 0 or i &gt;= size()</tt>)
	 * @see     java.util.ArrayList#get(int)
	 */
	public Object getElement(int i) {
		return list.get(i);
	}

	/**
	 * Replaces the element at the specified position in this list with the specified element.
	 * 
	 * @param   i  the index of the element to replace
	 * @param   e  the element to store at the specified position
	 * @return  the element that was replaced
	 * @throws  IndexOutOfBoundsException  if the index is out of bounds
	 *          (<tt>i &lt; 0 or i &gt;= size()</tt>)
	 * @see     java.util.ArrayList#set(int, Object)
	 */
	public Object setElement(int i, Object e) {
		return list.set(i, e);
	}

	/**
	 * Appends an element to the end of this list.
	 * 
	 * @param  e  the element to append
	 */
	public void addElement(Object e) {
		list.add(e);
	}

	/**
	 * Removes the element at the specified position in this list.
	 * 
	 * @param   i  the index of the element to remove
	 * @return  the removed element
	 * @throws  IndexOutOfBoundsException  if the index is out of bounds
	 *          (<tt>i &lt; 0 or i &gt;= size()</tt>)
	 * @see     java.util.ArrayList#remove(int)
	 */
	public Object removeElement(int i) {
		return list.remove(i);
	}

	/**
	 * Removes all the elements.
	 * 
	 * @see  java.util.ArrayList#clear()
	 */
	public void clear() {
		list.clear();
	}

	/**
	 * Returns an iterator over the elements in this list in proper sequence.
	 * 
	 * <p>The iterator is fail-fast.
	 * 
	 * @return  an iterator over the elements in this list in proper sequence
	 * @see     java.util.Iterator
	 * @see     java.util.ArrayList#iterator()
	 */
	public Iterator<Object> iterator() {
		return list.iterator();
	}

	// getters and setters

	public boolean getBoolean(int i) {
		return (Boolean) getElement(i);
	}

	public Object setBoolean(int i, boolean value) {
		return setElement(i, value);
	}

	public void addBoolean(boolean value) {
		addElement(value);
	}

	public byte getByte(int i) {
		return (Byte) getElement(i);
	}

	public Object setByte(int i, byte value) {
		return setElement(i, value);
	}

	public void addByte(byte value) {
		addElement(value);
	}

	public short getShort(int i) {
		return (Short) getElement(i);
	}

	public Object setShort(int i, short value) {
		return setElement(i, value);
	}

	public void addShort(short value) {
		addElement(value);
	}

	public int getInt(int i) {
		return (Integer) getElement(i);
	}

	public Object setInt(int i, int value) {
		return setElement(i, value);
	}

	public void addInt(int value) {
		addElement(value);
	}

	public long getLong(int i) {
		return (Long) getElement(i);
	}

	public Object setLong(int i, long value) {
		return setElement(i, value);
	}

	public void addLong(long value) {
		addElement(value);
	}

	public float getFloat(int i) {
		return (Float) getElement(i);
	}

	public Object setFloat(int i, float value) {
		return setElement(i, value);
	}

	public void addFloat(float value) {
		addElement(value);
	}

	public double getDouble(int i) {
		return (Double) getElement(i);
	}

	public Object setDouble(int i, double value) {
		return setElement(i, value);
	}

	public void addDouble(double value) {
		addElement(value);
	}

	public char getChar(int i) {
		return (Character) getElement(i);
	}

	public Object setChar(int i, char value) {
		return setElement(i, value);
	}

	public void addChar(char value) {
		addElement(value);
	}

	public String getString(int i) {
		return (String) getElement(i);
	}

	public Object setString(int i, String value) {
		return setElement(i, value);
	}

	public void addString(String value) {
		addElement(value);
	}

	public VDFObject getObject(int i) {
		return (VDFObject) getElement(i);
	}

	public Object setObject(int i, VDFObject objectElement) {
		return setElement(i, objectElement);
	}

	public void addObject(VDFObject objectElement) {
		addElement(objectElement);
	}

	public VDFList getList(int i) {
		return (VDFList) getElement(i);
	}

	public Object setList(int i, VDFList listElement) {
		return setElement(i, listElement);
	}

	public void addList(VDFList listElement) {
		addElement(listElement);
	}

	// arrays

	public boolean[] getBooleanArray(int i) {
		return (boolean[]) getElement(i);
	}

	public Object setBooleanArray(int i, boolean[] value) {
		return setElement(i, value);
	}

	public void addBooleanArray(boolean[] value) {
		addElement(value);
	}

	public byte[] getByteArray(int i) {
		return (byte[]) getElement(i);
	}

	public Object setByteArray(int i, byte[] value) {
		return setElement(i, value);
	}

	public void addByteArray(byte[] value) {
		addElement(value);
	}

	public short[] getShortArray(int i) {
		return (short[]) getElement(i);
	}

	public Object setShortArray(int i, short[] value) {
		return setElement(i, value);
	}

	public void addShortArray(short[] value) {
		addElement(value);
	}

	public int[] getIntArray(int i) {
		return (int[]) getElement(i);
	}

	public Object setIntArray(int i, int[] value) {
		return setElement(i, value);
	}

	public void addIntArray(int[] value) {
		addElement(value);
	}

	public long[] getLongArray(int i) {
		return (long[]) getElement(i);
	}

	public Object setLongArray(int i, long[] value) {
		return setElement(i, value);
	}

	public void addLongArray(long[] value) {
		addElement(value);
	}

	public float[] getFloatArray(int i) {
		return (float[]) getElement(i);
	}

	public Object setFloatArray(int i, float[] value) {
		return setElement(i, value);
	}

	public void addFloatArray(float[] value) {
		addElement(value);
	}

	public double[] getDoubleArray(int i) {
		return (double[]) getElement(i);
	}

	public Object setDoubleArray(int i, double[] value) {
		return setElement(i, value);
	}

	public void addDoubleArray(double[] value) {
		addElement(value);
	}

	public char[] getCharArray(int i) {
		return (char[]) getElement(i);
	}

	public Object setCharArray(int i, char[] value) {
		return setElement(i, value);
	}

	public void addCharArray(char[] value) {
		addElement(value);
	}

	public String[] getStringArray(int i) {
		return (String[]) getElement(i);
	}

	public Object setStringArray(int i, String[] value) {
		return setElement(i, value);
	}

	public void addStringArray(String[] value) {
		addElement(value);
	}

	public VDFObject[] getObjectArray(int i) {
		return (VDFObject[]) getElement(i);
	}

	public Object setObjectArray(int i, VDFObject[] value) {
		return setElement(i, value);
	}

	public void addObjectArray(VDFObject[] value) {
		addElement(value);
	}

	public VDFList[] getListArray(int i) {
		return (VDFList[]) getElement(i);
	}

	public Object setListArray(int i, VDFList[] value) {
		return setElement(i, value);
	}

	public void addListArray(VDFList[] value) {
		addElement(value);
	}

	// binary IO

	/**
	 * Reads a list from a {@code DataInputStream} and adds the elements to this list, without
	 * removing contained elements.
	 * 
	 * @param   in  a data input stream
	 * @return  this list
	 * 
	 * @throws  EOFException  if the stream reaches the end before a list can be read
	 * @throws  IOException   if an IO error occurs
	 */
	public VDFList deserialize(DataInputStream in) throws IOException {
		BinaryIO.deserialize(in, this);
		return this;
	}

	/**
	 * Reads a list from an {@code InputStream} and adds the elements to this list, without
	 * removing contained elements.
	 * 
	 * @param   in  an input stream
	 * @return  this list
	 * 
	 * @throws  EOFException  if the stream reaches the end before a list can be read
	 * @throws  IOException   if an IO error occurs
	 * 
	 * @see     vulc.vdf.VDFList#deserialize(DataInputStream)
	 */
	public VDFList deserialize(InputStream in) throws IOException {
		return deserialize(new DataInputStream(in));
	}

	/**
	 * Reads a list from a file and adds the elements to this list, without removing contained
	 * elements.
	 * 
	 * @param   file  the file to read
	 * @return  this list
	 * 
	 * @throws  EOFException  if the stream reaches the end before a list can be read
	 * @throws  IOException   if an IO error occurs
	 * 
	 * @see     vulc.vdf.VDFList#deserialize(DataInputStream)
	 */
	public VDFList deserialize(File file) throws IOException {
		try(InputStream in = new BufferedInputStream(new FileInputStream(file))) {
			return deserialize(in);
		}
	}

	/**
	 * Writes this list to a {@code DataOutputStream}.
	 * 
	 * @param   out  a data output stream
	 * @throws  IOException  if an IO error occurs
	 */
	public void serialize(DataOutputStream out) throws IOException {
		BinaryIO.serialize(out, this);
	}

	/**
	 * Writes this list to an {@code OutputStream}.
	 * 
	 * @param   out  an output stream
	 * @throws  IOException  if an IO error occurs
	 * @see     vulc.vdf.VDFList#serialize(DataOutputStream)
	 */
	public void serialize(OutputStream out) throws IOException {
		serialize(new DataOutputStream(out));
	}

	/**
	 * Writes this list to a file.
	 * 
	 * @param   file  the file to write this list to
	 * @throws  IOException  if an IO error occurs
	 * @see     vulc.vdf.VDFList#serialize(DataOutputStream)
	 */
	public void serialize(File file) throws IOException {
		try(OutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
			serialize(out);
		}
	}

	// text IO

	/**
	 * Reads a list from a {@code Reader} and adds the elements to this list, without removing
	 * contained elements.
	 * 
	 * @param   in  the reader
	 * @return  this list
	 * 
	 * @throws  IOException  if an IO error occurs
	 * @throws  VDFParseException  if the file could not be parsed properly
	 */
	public VDFList parse(Reader in) throws IOException {
		TextIO.deserialize(in, this);
		return this;
	}

	/**
	 * Reads a list from a {@code String} and adds the elements to this list, without removing
	 * contained elements.
	 * 
	 * @param   string  the text to parse
	 * @return  this list
	 * @see     vulc.vdf.VDFList#parse(Reader)
	 */
	public VDFList parse(String string) {
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
	 * Returns a string representation of this list.
	 * The flag {@code format} states if the string should be formatted or not.
	 * 
	 * <p>{@code toString()} can be used instead of {@code toString(false)}.
	 * 
	 * @param   format  a flag stating if the output string should be formatted or not
	 *                  ({@code true} = formatted, {@code false} = unformatted)
	 * @return  a string representation of this list
	 * @see     vulc.vdf.VDFList#write(Writer, boolean)
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
	 * Returns an unformatted string representation of this list.
	 * This method is equivalent to {@code toString(false)}.
	 * 
	 * @return  an unformatted string representation of this list
	 * @see     vulc.vdf.VDFList#toString(boolean)
	 */
	public String toString() {
		return toString(false);
	}

	/**
	 * Compares this {@code VDFList} to the given {@code Object}.
	 * 
	 * <p>This list and the given {@code Object} are equal if all of the following are true:
	 * <ul>
	 * <li>the given object is an instance of {@code VDFList}
	 * <li>this list's size is equal to the other list's size
	 * <li>both lists contain equal elements
	 * </ul>
	 * 
	 * @param   obj  the object to compare
	 * @return  {@code true} if this list and the given one are equal; {@code false} otherwise
	 */
	public boolean equals(Object obj) {
		if(obj instanceof VDFList) {
			VDFList toCompare = (VDFList) obj;

			if(this.size() != toCompare.size()) {
				return false; // the lists have different size
			}

			int size = this.size();
			for(int i = 0; i < size; i++) {
				if(!Objects.deepEquals(this.getElement(i), toCompare.getElement(i))) {
					return false; // two elements at the same position are not equal
				}
			}
			return true; // the two lists have the same size and each of their elements is equal
		}
		return false; // 'obj' is not a VDFList
	}

}
