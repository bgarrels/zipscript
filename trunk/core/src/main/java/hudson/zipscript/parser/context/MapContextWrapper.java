package hudson.zipscript.parser.context;

import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

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

	public Locale getLocale () {
		return locale;
	}
}
