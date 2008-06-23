package hudson.zipscript.parser.context;

import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.resource.macrolib.MacroManager;

import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

public class BiContext implements ZSContext {

	private Map primary;
	private ZSContext secondary;

	public BiContext (Map primary, ZSContext secondary) {
		this.primary = primary;
		this.secondary = secondary;
	}
	
	public Object get(String key) {
		Object obj = primary.get(key);
		if (null == obj) obj = secondary.get(key);
		return obj;
	}

	public Iterator getKeys() {
		return secondary.getKeys();
	}

	public Locale getLocale() {
		return secondary.getLocale();
	}

	public MacroManager getMacroManager() {
		return secondary.getMacroManager();
	}

	public ParsingSession getParsingSession() {
		return secondary.getParsingSession();
	}

	public ZSContext getRootContext() {
		return secondary.getRootContext();
	}

	public void put(String key, Object value) {
		secondary.put(key, value);
	}

	public void putGlobal(String key, Object value) {
		secondary.putGlobal(key, value);
	}

	public Object remove(String key) {
		return secondary.remove(key);
	}

	public void setMacroManager(MacroManager macroManager) {
		secondary.setMacroManager(macroManager);
	}

	public void setParsingSession(ParsingSession session) {
		secondary.setParsingSession(session);
	}
}