package hudson.zipscript.parser.template.element.lang.variable.adapter;

import java.util.Iterator;

public class IteratorAdapter implements SequenceAdapter {

	public static IteratorAdapter INSTANCE = new IteratorAdapter();

	public void addItemAt(int index, Object value, Object sequence)
			throws ClassCastException {
		throw new UnsupportedOperationException();
	}

	public boolean appliesTo(Object object) {
		return (object instanceof Iterator);
	}

	public Object getItemAt(int index, Object sequence)
			throws ClassCastException {
		throw new UnsupportedOperationException();
	}

	public int getLength(Object sequence) throws ClassCastException {
		throw new UnsupportedOperationException();
	}

	public boolean hasNext(int index, Object previousItem, Object sequence) {
		return ((Iterator) sequence).hasNext();
	}

	public int indexOf(Object object, Object sequence)
			throws ClassCastException {
		throw new UnsupportedOperationException();
	}

	public int lastIndexOf(Object object, Object sequence)
			throws ClassCastException {
		throw new UnsupportedOperationException();
	}

	public Object nextItem(int index, Object lastVal, Object sequence)
			throws ClassCastException {
		return ((Iterator) sequence).next();
	}

	public void setItemAt(int index, Object value, Object sequence)
			throws ClassCastException {
		throw new UnsupportedOperationException();
	}
}