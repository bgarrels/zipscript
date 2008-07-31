package hudson.zipscript.parser.template.element.lang.variable.adapter;

import java.util.Iterator;

public interface ObjectAdapter {

	public boolean appliesTo (Object object);

	public Object get (
			String key, Object object, RetrievalContext context, String contextHint) throws ClassCastException;

	public void set (String key, Object value, Object object) throws ClassCastException;

	public Iterator getProperties (Object object) throws ClassCastException;

	public Object call (String key, Object[] parameters, Object object) throws ClassCastException;
}
