package hudson.zipscript.parser.context;

import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.directive.macrodir.MacroDirective;
import hudson.zipscript.resource.macrolib.MacroManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

public class NestedContextWrapper implements ZSContext {

	private ZSContext parentContext;
	private HashMap map = new HashMap(4);
	private boolean travelUp = true;
	private Element scopedElement;

	public NestedContextWrapper (ZSContext parentContext, Element scopedElement) {
		this.parentContext = parentContext;
		this.scopedElement = scopedElement;
	}

	public NestedContextWrapper (
			ZSContext parentContext, Element scopedElement, boolean travelUp) {
		this.parentContext = parentContext;
		this.scopedElement = scopedElement;
		this.travelUp = travelUp;
	}

	public Object get(String key) {
		Object obj = map.get(key);
		if (null == obj && travelUp)
			obj = parentContext.get(key);
		return obj;
	}

	public Iterator getKeys() {
		return map.keySet().iterator();
	}

	public void put(String key, Object value) {
		map.put(key, value);
	}

	public void putGlobal(String key, Object value) {
		parentContext.putGlobal(key, value);
	}

	public Object remove(String key) {
		return map.remove(key);
	}

	public Object getSuper () {
		return parentContext;
	}

	public ParsingSession getParsingSession() {
		return parentContext.getParsingSession();
	}

	public void setParsingSession(ParsingSession session) {
		parentContext.setParsingSession(session);
	}

	public Locale getLocale () {
		return parentContext.getLocale();
	}

	public MacroManager getMacroManager() {
		return parentContext.getMacroManager();
	}

	public void setMacroManager (MacroManager macroManager) {
		parentContext.setMacroManager(macroManager);
	}

	public ZSContext getRootContext () {
		return parentContext.getRootContext();
	}

	public Element getScopedElement() {
		return scopedElement;
	}

	public ZSContext getParentContext() {
		return parentContext;
	}

	public HashMap getMap() {
		return map;
	}

	public void appendMacroNestedAttributes(Map m) {
		m.putAll(map);
		if (!(getScopedElement() instanceof MacroDirective)) {
			parentContext.appendMacroNestedAttributes(m);
		}
	}
}