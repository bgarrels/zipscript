package hudson.zipscript.parser.context;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class SimpleContextWrapper extends AbstractContext {

	private Context context;
	private Locale locale;

	public SimpleContextWrapper (Context context) {
		this.context = context;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public void put(Object key, Object value, boolean travelUp) {
		context.put(key, value);
	}

	public void putGlobal(Object key, Object value) {
		context.put(key, value);
	}

	public Object get(Object key) {
		return context.get(key);
	}

	public Set getKeys() {
		return context.getKeys();
	}

	public void put(Object key, Object value) {
		context.put(key, value);
	}

	public Object remove(Object key) {
		return context.remove(key);
	}

	public ExtendedContext getRootContext() {
		return this;
	}

	public void appendMacroNestedAttributes(Map m) {
		// if we are using this context we are at the top level
		// and not in a macro definition
	}

	public void addToElementScope(List nestingStack) {
		// this is a root element	
	}
}