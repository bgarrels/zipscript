package hudson.zipscript.parser.context;

import java.util.Iterator;


public interface ZSContext {

	public Object get (String key);

	public Object remove (String key);

	public void put (String key, Object value);

	public Iterator getKeys ();
}
