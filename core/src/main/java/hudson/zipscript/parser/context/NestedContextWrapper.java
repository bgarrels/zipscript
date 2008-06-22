package hudson.zipscript.parser.context;

import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.resource.macrolib.MacroManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class NestedContextWrapper implements ZSContext {

	private ZSContext context;
	private HashMap map = new HashMap(4);
	private boolean travelUp = true;

	public NestedContextWrapper (ZSContext context) {
		this.context = context;
	}

	public NestedContextWrapper (ZSContext context, boolean travelUp) {
		this.context = context;
		this.travelUp = travelUp;
	}

	public Object get(String key) {
		Object obj = map.get(key);
		if (null == obj && travelUp)
			return context.get(key);
		else
			return obj;
	}

	public Iterator getKeys() {
		return map.keySet().iterator();
	}

	public void put(String key, Object value) {
		map.put(key, value);
	}

	public void putGlobal(String key, Object value) {
		context.putGlobal(key, value);
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

	public Locale getLocale () {
		return context.getLocale();
	}

	public MacroManager getMacroManager() {
		return context.getMacroManager();
	}

	public void setMacroManager (MacroManager macroManager) {
		context.setMacroManager(macroManager);
	}

	public ZSContext getRootContext () {
		return context.getRootContext();
	}
}