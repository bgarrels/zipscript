package hudson.zipscript.parser.template.element.lang.variable.adapter;

import java.util.Iterator;
import java.util.Map;

public class JavaMapAdapter implements MapAdapter {

	public static final JavaMapAdapter INSTANCE = new JavaMapAdapter();
	
	public boolean appliesTo(Object object) {
		return (object instanceof Map);
	}

	public Object get(Object key, Object map) {
		return ((Map) map).get(key);
	}

	public Iterator getKeys(Object map) {
		return ((Map) map).keySet().iterator();
	}

	public void put(Object key, Object value, Object map) {
		((Map) map).put(key, value);
	}

	public Object remove(Object key, Object map) {
		return ((Map) map).remove(key);
	}

}
