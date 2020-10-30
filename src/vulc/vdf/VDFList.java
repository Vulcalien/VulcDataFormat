package vulc.vdf;

import java.util.ArrayList;
import java.util.List;

public class VDFList extends Element {

	private final List<Element> list = new ArrayList<Element>();

	public int size() {
		return list.size();
	}

	public Element getElement(int i) {
		return list.get(i);
	}

	public void setElement(int i, Element e) {
		list.set(i, e);
	}

	public void addElement(Element e) {
		list.add(e);
	}

	public void removeElement(int i) {
		list.remove(i);
	}

	public void clear() {
		list.clear();
	}

	public Object get() {
		return this;
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

	// TODO test these
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

	// TODO test these
	public VDFList[] getListArray(int i) {
		return ((ListArrayElement) getElement(i)).value;
	}

	public void setListArray(int i, VDFList[] value) {
		setElement(i, new ListArrayElement(value));
	}

	public void addListArray(VDFList[] value) {
		addElement(new ListArrayElement(value));
	}

	// binary IO TODO

	// text IO TODO

}
