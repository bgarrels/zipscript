package hudson.zipscript.parser.context;

import hudson.zipscript.parser.template.data.ParsingSession;

import java.util.HashMap;
import java.util.Iterator;

public class NestedContextWrapper implements ZSContext {

	private ZSContext context;
	private HashMap map = new HashMap(4);

	public NestedContextWrapper (ZSContext context) {
		this.context = context;
	}

	public Object get(String key) {
		Object obj = map.get(key);
		if (null == obj) obj = context.get(key);
		return obj;
	}

	public Iterator getKeys() {
		return context.getKeys();
	}

	public void put(String key, Object value) {
		map.put(key, value);
	}

	public Object remove(String key) {
		return map.remove(key);
	}

	public Object getSuper () {
		return context;
	}

	public ParsingSession getParsingSession() {
		return context.getParsingSession();
	}

	public void setParsingSession(ParsingSession session) {
		context.setParsingSession(session);
	}
}