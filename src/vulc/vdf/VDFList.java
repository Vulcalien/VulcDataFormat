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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

import vulc.vdf.io.binary.BinaryVDF;
import vulc.vdf.io.text.TextVDF;
import vulc.vdf.io.text.VDFParseException;

/**
 * An instance of this class represents a VDF list, "<i>a structure that contains ordered
 * elements</i>"
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
	 * @param  e  the element to add
	 * @see    java.util.ArrayList#add(Object)
	 */
	public void addElement(Object e) {
		list.add(e);
	}

	/**
	 * Appends all the elements in the given list to the end of this list.
	 * 
	 * @param  list  the list containing the elements to add
	 * @see    java.util.ArrayList#addAll(Collection)
	 */
	public void addAll(VDFList list) {
		this.list.addAll(list.list);
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

	public Object setBoolean(int i, boolean element) {
		return setElement(i, element);
	}

	public void addBoolean(boolean element) {
		addElement(element);
	}

	public byte getByte(int i) {
		return (Byte) getElement(i);
	}

	public Object setByte(int i, byte element) {
		return setElement(i, element);
	}

	public void addByte(byte element) {
		addElement(element);
	}

	public short getShort(int i) {
		return (Short) getElement(i);
	}

	public Object setShort(int i, short element) {
		return setElement(i, element);
	}

	public void addShort(short element) {
		addElement(element);
	}

	public int getInt(int i) {
		return (Integer) getElement(i);
	}

	public Object setInt(int i, int element) {
		return setElement(i, element);
	}

	public void addInt(int element) {
		addElement(element);
	}

	public long getLong(int i) {
		return (Long) getElement(i);
	}

	public Object setLong(int i, long element) {
		return setElement(i, element);
	}

	public void addLong(long element) {
		addElement(element);
	}

	public float getFloat(int i) {
		return (Float) getElement(i);
	}

	public Object setFloat(int i, float element) {
		return setElement(i, element);
	}

	public void addFloat(float element) {
		addElement(element);
	}

	public double getDouble(int i) {
		return (Double) getElement(i);
	}

	public Object setDouble(int i, double element) {
		return setElement(i, element);
	}

	public void addDouble(double element) {
		addElement(element);
	}

	public char getChar(int i) {
		return (Character) getElement(i);
	}

	public Object setChar(int i, char element) {
		return setElement(i, element);
	}

	public void addChar(char element) {
		addElement(element);
	}

	public String getString(int i) {
		return (String) getElement(i);
	}

	public Object setString(int i, String element) {
		return setElement(i, element);
	}

	public void addString(String element) {
		addElement(element);
	}

	public VDFObject getObject(int i) {
		return (VDFObject) getElement(i);
	}

	public Object setObject(int i, VDFObject element) {
		return setElement(i, element);
	}

	public void addObject(VDFObject element) {
		addElement(element);
	}

	public VDFList getList(int i) {
		return (VDFList) getElement(i);
	}

	public Object setList(int i, VDFList element) {
		return setElement(i, element);
	}

	public void addList(VDFList element) {
		addElement(element);
	}

	// arrays

	public boolean[] getBooleanArray(int i) {
		return (boolean[]) getElement(i);
	}

	public Object setBooleanArray(int i, boolean[] element) {
		return setElement(i, element);
	}

	public void addBooleanArray(boolean[] element) {
		addElement(element);
	}

	public byte[] getByteArray(int i) {
		return (byte[]) getElement(i);
	}

	public Object setByteArray(int i, byte[] element) {
		return setElement(i, element);
	}

	public void addByteArray(byte[] element) {
		addElement(element);
	}

	public short[] getShortArray(int i) {
		return (short[]) getElement(i);
	}

	public Object setShortArray(int i, short[] element) {
		return setElement(i, element);
	}

	public void addShortArray(short[] element) {
		addElement(element);
	}

	public int[] getIntArray(int i) {
		return (int[]) getElement(i);
	}

	public Object setIntArray(int i, int[] element) {
		return setElement(i, element);
	}

	public void addIntArray(int[] element) {
		addElement(element);
	}

	public long[] getLongArray(int i) {
		return (long[]) getElement(i);
	}

	public Object setLongArray(int i, long[] element) {
		return setElement(i, element);
	}

	public void addLongArray(long[] element) {
		addElement(element);
	}

	public float[] getFloatArray(int i) {
		return (float[]) getElement(i);
	}

	public Object setFloatArray(int i, float[] element) {
		return setElement(i, element);
	}

	public void addFloatArray(float[] element) {
		addElement(element);
	}

	public double[] getDoubleArray(int i) {
		return (double[]) getElement(i);
	}

	public Object setDoubleArray(int i, double[] element) {
		return setElement(i, element);
	}

	public void addDoubleArray(double[] element) {
		addElement(element);
	}

	public char[] getCharArray(int i) {
		return (char[]) getElement(i);
	}

	public Object setCharArray(int i, char[] element) {
		return setElement(i, element);
	}

	public void addCharArray(char[] element) {
		addElement(element);
	}

	public String[] getStringArray(int i) {
		return (String[]) getElement(i);
	}

	public Object setStringArray(int i, String[] element) {
		return setElement(i, element);
	}

	public void addStringArray(String[] element) {
		addElement(element);
	}

	public VDFObject[] getObjectArray(int i) {
		return (VDFObject[]) getElement(i);
	}

	public Object setObjectArray(int i, VDFObject[] element) {
		return setElement(i, element);
	}

	public void addObjectArray(VDFObject[] element) {
		addElement(element);
	}

	public VDFList[] getListArray(int i) {
		return (VDFList[]) getElement(i);
	}

	public Object setListArray(int i, VDFList[] element) {
		return setElement(i, element);
	}

	public void addListArray(VDFList[] element) {
		addElement(element);
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
	 * @see     vulc.vdf.io.binary.BinaryVDF#deserialize(DataInputStream)
	 */
	public VDFList deserialize(DataInputStream in) throws IOException {
		this.addAll((VDFList) BinaryVDF.deserialize(in));
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
	 * @see     vulc.vdf.io.binary.BinaryVDF#deserialize(InputStream)
	 */
	public VDFList deserialize(InputStream in) throws IOException {
		addAll((VDFList) BinaryVDF.deserialize(in));
		return this;
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
	 * @see     vulc.vdf.io.binary.BinaryVDF#deserialize(File)
	 */
	public VDFList deserialize(File file) throws IOException {
		addAll((VDFList) BinaryVDF.deserialize(file));
		return this;
	}

	/**
	 * Writes this list to a {@code DataOutputStream}.
	 * 
	 * @param   out  a data output stream
	 * @throws  IOException  if an IO error occurs
	 * @see     vulc.vdf.io.binary.BinaryVDF#serialize(DataOutputStream, Object)
	 */
	public void serialize(DataOutputStream out) throws IOException {
		BinaryVDF.serialize(out, this);
	}

	/**
	 * Writes this list to an {@code OutputStream}.
	 * 
	 * @param   out  an output stream
	 * @throws  IOException  if an IO error occurs
	 * @see     vulc.vdf.io.binary.BinaryVDF#serialize(OutputStream, Object)
	 */
	public void serialize(OutputStream out) throws IOException {
		BinaryVDF.serialize(out, this);
	}

	/**
	 * Writes this list to a file.
	 * 
	 * @param   file  the file to write this list to
	 * @throws  IOException  if an IO error occurs
	 * @see     vulc.vdf.io.binary.BinaryVDF#serialize(File, Object)
	 */
	public void serialize(File file) throws IOException {
		BinaryVDF.serialize(file, this);
	}

	// text IO

	/**
	 * Reads a list from a {@code Reader} and adds the elements to this list, without removing
	 * contained elements.
	 * 
	 * @param   in  a reader
	 * @return  this list
	 * 
	 * @throws  IOException  if an IO error occurs
	 * @throws  VDFParseException  if the text could not be parsed properly
	 * 
	 * @see     vulc.vdf.io.text.TextVDF#deserialize(Reader)
	 */
	public VDFList parse(Reader in) throws IOException {
		addAll((VDFList) TextVDF.deserialize(in));
		return this;
	}

	/**
	 * Reads a list from a file and adds the elements to this list, without removing contained
	 * elements.
	 * 
	 * @param   file  the file to read
	 * @return  this list
	 * 
	 * @throws  IOException  if an IO error occurs
	 * @throws  VDFParseException  if the text could not be parsed properly
	 * 
	 * @see     vulc.vdf.io.text.TextVDF#deserialize(File)
	 */
	public VDFList parse(File file) throws IOException {
		addAll((VDFList) TextVDF.deserialize(file));
		return this;
	}

	/**
	 * Reads a list from a {@code String} and adds the elements to this list, without removing
	 * contained elements.
	 * 
	 * @param   string  the text to parse
	 * @return  this list
	 * @see     vulc.vdf.io.text.TextVDF#fromString(String)
	 */
	public VDFList parse(String string) {
		addAll((VDFList) TextVDF.fromString(string));
		return this;
	}

	/**
	 * Writes this list to a {@code Writer}.
	 * 
	 * @param   out     a writer
	 * @param   format  a flag stating if the output should be formatted or not
	 *                  ({@code true} = formatted, {@code false} = unformatted)
	 * @throws  IOException  if an IO error occurs
	 * @see     vulc.vdf.io.text.TextVDF#serialize(Writer, Object, boolean)
	 */
	public void write(Writer out, boolean format) throws IOException {
		TextVDF.serialize(out, this, format);
	}

	/**
	 * Writes this list to a file.
	 * 
	 * @param   file    the file to write this list to
	 * @param   format  a flag stating if the output should be formatted or not
	 *                  ({@code true} = formatted, {@code false} = unformatted)
	 * @throws  IOException  if an IO error occurs
	 * @see     vulc.vdf.io.text.TextVDF#serialize(File, Object, boolean)
	 */
	public void write(File file, boolean format) throws IOException {
		TextVDF.serialize(file, this, format);
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
	 * @see     vulc.vdf.io.text.TextVDF#toString(Object, boolean)
	 */
	public String toString(boolean format) {
		return TextVDF.toString(this, format);
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
