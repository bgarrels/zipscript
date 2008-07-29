package hudson.zipscript.parser.context;

import hudson.zipscript.parser.template.element.lang.variable.adapter.RetrievalContext;

import java.util.Map;
import java.util.Set;

public class MapContextWrapper extends AbstractContext {

	private Map map;

	public MapContextWrapper(Map map) {
		this.map = map;
	}

	public Object get(Object key, RetrievalContext retrievalContext) {
		return map.get(key);
	}

	public Object remove(Object key) {
		return map.remove(key);
	}

	public void put(Object key, Object value, boolean travelUp) {
		map.put(key, value);
	}

	public void put(Object key, Object value) {
		this.put(key, value, false);
	}

	public void putGlobal(Object key, Object value) {
		put(key, value, true);
	}

	public Set getKeys() {
		return map.keySet();
	}
}