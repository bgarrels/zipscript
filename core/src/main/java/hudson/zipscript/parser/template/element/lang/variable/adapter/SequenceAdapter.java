package hudson.zipscript.parser.template.element.lang.variable.adapter;


public interface SequenceAdapter {

	public boolean appliesTo(Object object);

	public int getLength(Object sequence) throws ClassCastException;

	public Object nextItem(int index, Object lastVal, Object sequence) throws ClassCastException;

	public boolean hasNext (int index, Object previousItem, Object sequence);

	public Object getItemAt(int index, Object sequence, RetrievalContext retrievalContext) throws ClassCastException;

	public void setItemAt(int index, Object value, Object sequence) throws ClassCastException;

	public void addItemAt(int index, Object value, Object sequence) throws ClassCastException;

	public int indexOf(Object object, Object sequence) throws ClassCastException;

	public int lastIndexOf(Object object, Object sequence) throws ClassCastException;

	public boolean contains (Object obj, Object sequence) throws ClassCastException;
}
