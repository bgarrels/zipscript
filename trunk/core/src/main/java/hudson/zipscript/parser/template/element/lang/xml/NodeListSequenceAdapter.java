package hudson.zipscript.parser.template.element.lang.xml;

import org.w3c.dom.NodeList;

import hudson.zipscript.parser.template.element.lang.variable.adapter.RetrievalContext;
import hudson.zipscript.parser.template.element.lang.variable.adapter.SequenceAdapter;

public class NodeListSequenceAdapter implements SequenceAdapter {

	public static NodeListSequenceAdapter INSTANCE = new NodeListSequenceAdapter();

	public void addItemAt(int index, Object value, Object sequence)
			throws ClassCastException {
		throw new UnsupportedOperationException();
	}

	public boolean appliesTo(Object object) {
		return (object instanceof NodeList);
	}

	public boolean contains(Object obj, Object sequence)
			throws ClassCastException {
		throw new UnsupportedOperationException();
	}

	public Object getItemAt(int index, Object sequence,
			RetrievalContext retrievalContext, String contextHint)
			throws ClassCastException {
		return ((NodeList) sequence).item(index);
	}

	public int getLength(Object sequence) throws ClassCastException {
		return ((NodeList) sequence).getLength();
	}

	public boolean hasNext(int index, Object previousItem, Object sequence) {
		return ((NodeList) sequence).getLength() > (index+1);
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
		return ((NodeList) sequence).item(index);
	}

	public void setItemAt(int index, Object value, Object sequence)
			throws ClassCastException {
		throw new UnsupportedOperationException();
	}
}