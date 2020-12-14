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
import java.util.ArrayList;
import java.util.Iterator;

import vulc.vdf.io.binary.BinaryIO;
import vulc.vdf.io.text.TextIO;

public class VDFList extends Element implements Iterable<Element> {

	private final ArrayList<Element> list = new ArrayList<Element>();

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
	 * position 3 is an {@code IntElement} then {@code getInt(3)} should be preferred, since it
	 * will return a primitive {@code int} value.
	 * 
	 * @param   i  the index of the element to return
	 * @return  the element at the specified position
	 * @throws  IndexOutOfBoundsException  if the index is out of bounds (i < 0 or i >= size())
	 * @see     java.util.ArrayList#get(int)
	 */
	public Element getElement(int i) {
		return list.get(i);
	}

	/**
	 * Replaces the element at the specified position in this list with the specified element.
	 * 
	 * @param   i  the index of the element to replace
	 * @param   e  the element to store at the specified position
	 * @throws  IndexOutOfBoundsException  if the index is out of bounds (i < 0 or i >= size())
	 * @see     java.util.ArrayList#set(int, Object)
	 */
	public void setElement(int i, Element e) { // TODO add return old value
		list.set(i, e);
	}

	/**
	 * Appends an element to the end of this list.
	 * 
	 * @param  e  the element to append
	 */
	// TODO don't allow null value for e
	public void addElement(Element e) {
		list.add(e);
	}

	/**
	 * Removes the element at the specified position in this list.
	 * 
	 * @param  i  the index of the element to remove
	 * @throws  IndexOutOfBoundsException  if the index is out of bounds (i < 0 or i >= size())
	 * @see    java.util.ArrayList#remove(int)
	 */
	// TODO add return removed value
	public void removeElement(int i) {
		list.remove(i);
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
	 * Returns this list as a Java {@code Object}.
	 * 
	 * @return  this list
	 */
	public Object get() {
		return this;
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
	public Iterator<Element> iterator() {
		return list.iterator();
	}

	// getters and setters

	public boolean getBoolean(int i) {
		return ((BooleanElement) getElement(i)).value;
	}

	public void setBoolean(int i, boolean value) {
		setElement(i, new BooleanElement(value));
	}

	public void addBoolean(boolean value) {
		addElement(new BooleanElement(value));
	}

	public byte getByte(int i) {
		return ((ByteElement) getElement(i)).value;
	}

	public void setByte(int i, byte value) {
		setElement(i, new ByteElement(value));
	}

	public void addByte(byte value) {
		addElement(new ByteElement(value));
	}

	public short getShort(int i) {
		return ((ShortElement) getElement(i)).value;
	}

	public void setShort(int i, short value) {
		setElement(i, new ShortElement(value));
	}

	public void addShort(short value) {
		addElement(new ShortElement(value));
	}

	public int getInt(int i) {
		return ((IntElement) getElement(i)).value;
	}

	public void setInt(int i, int value) {
		setElement(i, new IntElement(value));
	}

	public void addInt(int value) {
		addElement(new IntElement(value));
	}

	public long getLong(int i) {
		return ((LongElement) getElement(i)).value;
	}

	public void setLong(int i, long value) {
		setElement(i, new LongElement(value));
	}

	public void addLong(long value) {
		addElement(new LongElement(value));
	}

	public float getFloat(int i) {
		return ((FloatElement) getElement(i)).value;
	}

	public void setFloat(int i, float value) {
		setElement(i, new FloatElement(value));
	}

	public void addFloat(float value) {
		addElement(new FloatElement(value));
	}

	public double getDouble(int i) {
		return ((DoubleElement) getElement(i)).value;
	}

	public void setDouble(int i, double value) {
		setElement(i, new DoubleElement(value));
	}

	public void addDouble(double value) {
		addElement(new DoubleElement(value));
	}

	public char getChar(int i) {
		return ((CharElement) getElement(i)).value;
	}

	public void setChar(int i, char value) {
		setElement(i, new CharElement(value));
	}

	public void addChar(char value) {
		addElement(new CharElement(value));
	}

	public String getString(int i) {
		return ((StringElement) getElement(i)).value;
	}

	public void setString(int i, String value) {
		setElement(i, new StringElement(value));
	}

	public void addString(String value) {
		addElement(new StringElement(value));
	}

	public VDFObject getObject(int i) {
		return (VDFObject) getElement(i);
	}

	public void setObject(int i, VDFObject objectElement) {
		setElement(i, objectElement);
	}

	public void addObject(VDFObject objectElement) {
		addElement(objectElement);
	}

	public VDFList getList(int i) {
		return (VDFList) getElement(i);
	}

	public void setList(int i, VDFList listElement) {
		setElement(i, listElement);
	}

	public void addList(VDFList listElement) {
		addElement(listElement);
	}

	// arrays

	public boolean[] getBooleanArray(int i) {
		return ((BooleanArrayElement) getElement(i)).value;
	}

	public void setBooleanArray(int i, boolean[] value) {
		setElement(i, new BooleanArrayElement(value));
	}

	public void addBooleanArray(boolean[] value) {
		addElement(new BooleanArrayElement(value));
	}

	public byte[] getByteArray(int i) {
		return ((ByteArrayElement) getElement(i)).value;
	}

	public void setByteArray(int i, byte[] value) {
		setElement(i, new ByteArrayElement(value));
	}

	public void addByteArray(byte[] value) {
		addElement(new ByteArrayElement(value));
	}

	public short[] getShortArray(int i) {
		return ((ShortArrayElement) getElement(i)).value;
	}

	public void setShortArray(int i, short[] value) {
		setElement(i, new ShortArrayElement(value));
	}

	public void addShortArray(short[] value) {
		addElement(new ShortArrayElement(value));
	}

	public int[] getIntArray(int i) {
		return ((IntArrayElement) getElement(i)).value;
	}

	public void setIntArray(int i, int[] value) {
		setElement(i, new IntArrayElement(value));
	}

	public void addIntArray(int[] value) {
		addElement(new IntArrayElement(value));
	}

	public long[] getLongArray(int i) {
		return ((LongArrayElement) getElement(i)).value;
	}

	public void setLongArray(int i, long[] value) {
		setElement(i, new LongArrayElement(value));
	}

	public void addLongArray(long[] value) {
		addElement(new LongArrayElement(value));
	}

	public float[] getFloatArray(int i) {
		return ((FloatArrayElement) getElement(i)).value;
	}

	public void setFloatArray(int i, float[] value) {
		setElement(i, new FloatArrayElement(value));
	}

	public void addFloatArray(float[] value) {
		addElement(new FloatArrayElement(value));
	}

	public double[] getDoubleArray(int i) {
		return ((DoubleArrayElement) getElement(i)).value;
	}

	public void setDoubleArray(int i, double[] value) {
		setElement(i, new DoubleArrayElement(value));
	}

	public void addDoubleArray(double[] value) {
		addElement(new DoubleArrayElement(value));
	}

	public char[] getCharArray(int i) {
		return ((CharArrayElement) getElement(i)).value;
	}

	public void setCharArray(int i, char[] value) {
		setElement(i, new CharArrayElement(value));
	}

	public void addCharArray(char[] value) {
		addElement(new CharArrayElement(value));
	}

	public String[] getStringArray(int i) {
		return ((StringArrayElement) getElement(i)).value;
	}

	public void setStringArray(int i, String[] value) {
		setElement(i, new StringArrayElement(value));
	}

	public void addStringArray(String[] value) {
		addElement(new StringArrayElement(value));
	}

	public VDFObject[] getObjectArray(int i) {
		return ((ObjectArrayElement) getElement(i)).value;
	}

	public void setObjectArray(int i, VDFObject[] value) {
		setElement(i, new ObjectArrayElement(value));
	}

	public void addObjectArray(VDFObject[] value) {
		addElement(new ObjectArrayElement(value));
	}

	public VDFList[] getListArray(int i) {
		return ((ListArrayElement) getElement(i)).value;
	}

	public void setListArray(int i, VDFList[] value) {
		setElement(i, new ListArrayElement(value));
	}

	public void addListArray(VDFList[] value) {
		addElement(new ListArrayElement(value));
	}

	// binary IO

	/**
	 * Reads a list from a {@code DataInputStream} and adds the element to this object, without
	 * removing contained elements.
	 * 
	 * @param   in  a data input stream
	 * @return  this object
	 * 
	 * @throws  EOFException  if the stream reaches the end before an object can be read
	 * @throws  IOException   if an IO error occurs
	 */
	public VDFList deserialize(DataInputStream in) throws IOException {
		BinaryIO.deserialize(in, this);
		return this;
	}

	/**
	 * Reads a list from an {@code InputStream} and adds the element to this object, without
	 * removing contained elements.
	 * 
	 * @param   in  an input stream
	 * @return  this object
	 * 
	 * @throws  EOFException  if the stream reaches the end before an object can be read
	 * @throws  IOException   if an IO error occurs
	 * 
	 * @see     vulc.vdf.VDFList#deserialize(DataInputStream)
	 */
	public VDFList deserialize(InputStream in) throws IOException {
		return deserialize(new DataInputStream(in));
	}

	/**
	 * Reads a list from a {@code FileInputStream} and adds the element to this object, without
	 * removing contained elements.
	 * 
	 * @param   file  the file to read
	 * @return  this object
	 * 
	 * @throws  EOFException  if the stream reaches the end before an object can be read
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
	 * Writes this list to a {@code FileOutputStream}.
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

	public VDFList parse(String string) {
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
