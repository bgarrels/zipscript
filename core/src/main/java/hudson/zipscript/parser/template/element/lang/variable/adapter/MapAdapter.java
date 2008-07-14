package hudson.zipscript.parser.template.element.lang.variable.adapter;

import java.util.Iterator;

public interface MapAdapter {

	public boolean appliesTo (Object object);

	public void put (Object key, Object value, Object map) throws ClassCastException;

	public Object get (Object key, Object map) throws ClassCastException;

	public Object remove (Object key, Object map) throws ClassCastException;

	public Iterator getKeys (Object map) throws ClassCastException;
}
