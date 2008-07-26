package hudson.zipscript.parser.context;

import hudson.zipscript.ResourceContainer;
import hudson.zipscript.parser.Constants;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.directive.macrodir.MacroDirective;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class NestedContextWrapper implements ExtendedContext {

	private ExtendedContext parentContext;
	private HashMap map = new HashMap(4);
	private boolean travelUp;
	private Element scopedElement;

	public NestedContextWrapper (ExtendedContext parentContext, Element scopedElement) {
		this (parentContext, scopedElement, true);
	}

	public NestedContextWrapper (
			ExtendedContext parentContext, Element scopedElement, boolean travelUp) {
		this.parentContext = parentContext;
		this.scopedElement = scopedElement;
		this.travelUp = travelUp;
		map.put(Constants.VARS, this);
	}

	public Object get(Object key) {
		Object obj = map.get(key);
		if (null == obj && travelUp)
			obj = parentContext.get(key);
		return obj;
	}

	public Set getKeys() {
		return map.keySet();
	}

	public void put(Object key, Object value, boolean travelUp) {
		if (travelUp && this.travelUp)
			parentContext.put(key, value, true);
		else
			map.put(key, value);
	}

	public void put(Object key, Object value) {
		this.put(key, value, false);
	}

	public void putGlobal(Object key, Object value) {
		parentContext.putGlobal(key, value);
	}

	public Object remove(Object key) {
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

	public void setLocale(Locale locale) {
		parentContext.setLocale(locale);
	}

	public ResourceContainer getResourceContainer() {
		return parentContext.getResourceContainer();
	}

	public void setResourceContainer (ResourceContainer resourceContainer) {
		parentContext.setResourceContainer(resourceContainer);
	}

	public ExtendedContext getRootContext () {
		return parentContext.getRootContext();
	}

	public Element getScopedElement() {
		return scopedElement;
	}

	public ExtendedContext getParentContext() {
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

	public boolean isTravelUp() {
		return travelUp;
	}

	public void setTravelUp(boolean travelUp) {
		this.travelUp = travelUp;
	}

	public void addToElementScope(List nestingStack) {
		parentContext.addToElementScope(nestingStack);
		nestingStack.add(scopedElement);
	}

	public void setInitialized(boolean val) {
		parentContext.setInitialized(val);
	}

	public boolean isInitialized() {
		return parentContext.isInitialized();
	}

	public MacroDirective getMacro (String name) {
		return parentContext.getMacro(name);
	}

	public String getMacroImportPath (String namespace) {
		return parentContext.getMacroImportPath(namespace);
	}

	public void addMacroImport(String namespace, String macroPath) {
		parentContext.addMacroImport(namespace, macroPath);
	}
}