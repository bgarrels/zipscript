package hudson.zipscript.parser.context;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class MapContextWrapper extends AbstractContext {

	private Map map;
	private Locale locale;

	public MapContextWrapper (Map map) {
		this(map, Locale.getDefault());
	}

	public MapContextWrapper(Map map, Locale locale) {
		this.map = map;
		if (null == locale) this.locale = Locale.getDefault();
		else this.locale = locale;
	}

	public Object get(Object key) {
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