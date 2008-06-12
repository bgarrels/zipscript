package hudson.zipscript.parser.context;

import java.util.Iterator;
import java.util.Map;

public class MapContextWrapper extends AbstractContext {

	private Map map;

	public MapContextWrapper (Map map) {
		this.map = map;
	}

	public Object get(String key) {
		return map.get(key);
	}

	public Object remove(String key) {
		return map.remove(key);
	}

	public void put(String key, Object value) {
		map.put(key, value);
	}

	public Iterator getKeys() {
		return map.keySet().iterator();
	}
}
