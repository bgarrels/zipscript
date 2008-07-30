package hudson.zipscript.parser.template.element.lang.variable.adapter;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class JavaMapAdapter implements MapAdapter {

	public static final JavaMapAdapter INSTANCE = new JavaMapAdapter();
	
	public boolean appliesTo(Object object) {
		return (object instanceof Map);
	}

	public Object get(
			Object key, Object map, RetrievalContext retrievalContext, String contextHint) {
		return ((Map) map).get(key);
	}

	public Set getKeys(Object map) {
		return ((Map) map).keySet();
	}

	public Collection getValues(Object map) throws ClassCastException {
		return ((Map) map).values();
	}

	public void put(Object key, Object value, Object map) {
		((Map) map).put(key, value);
	}

	public Object remove(Object key, Object map) {
		return ((Map) map).remove(key);
	}

}
