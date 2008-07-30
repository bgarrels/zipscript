package hudson.zipscript.parser.template.element.lang.variable.adapter;

import java.util.Collection;
import java.util.Set;

public interface MapAdapter {

	public boolean appliesTo (Object object);

	public void put (Object key, Object value, Object map) throws ClassCastException;

	public Object get (
			Object key, Object map, RetrievalContext retrievalContext, String contextHint) throws ClassCastException;

	public Object remove (Object key, Object map) throws ClassCastException;

	public Set getKeys (Object map) throws ClassCastException;

	public Collection getValues (Object map) throws ClassCastException;
}
