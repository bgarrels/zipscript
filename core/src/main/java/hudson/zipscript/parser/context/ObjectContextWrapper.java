package hudson.zipscript.parser.context;

import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.util.BeanUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

public class ObjectContextWrapper extends AbstractContext {

	private static final String SET = "SET";
	private static final Object[] NO_PARAMS = new Object[0];

	private Object object;
	private Map setMap = new HashMap();
	private Map methodMap = new HashMap();
	private Locale locale;

	public ObjectContextWrapper (Object object) {
		this(object, Locale.getDefault());
	}

	public ObjectContextWrapper(Object object, Locale locale) {
		this.object = object;
		if (null == locale) this.locale = Locale.getDefault();
		else this.locale = locale;
	}

	public Object get(Object key) {
		if (null == key) return null;
		Object obj = methodMap.get(key);
		Method m = null;
		if (null == obj) {
			obj = BeanUtil.getPropertyMethod(object, key.toString(), null);
			if (null == obj) {
				methodMap.put(key, SET);
			}
			else {
				methodMap.put(key, obj);
			}
		}
		if (obj instanceof Method) {
			try {
				m = (Method) obj;
				return m.invoke(object, NO_PARAMS);
			}
			catch (Exception e) {
				throw new ExecutionException(
						"An error occured while calling '" + m.getName() + "' on '" + object.getClass().getName() + "'", null, e);
			}
		}
		else {
			return setMap.get(key);
		}
	}

	public Object remove(Object key) {
		return null;
	}

	public void put(Object key, Object value, boolean travelUp) {
		setMap.put(key, value);
	}

	public void putGlobal(Object key, Object value) {
		setMap.put(key, value);
	}

	public Iterator getKeys() {
		return setMap.keySet().iterator();
	}

	public Locale getLocale () {
		return locale;
	}

	public ZSContext getRootContext() {
		return this;
	}

	public void appendMacroNestedAttributes(Map m) {
		// if we are using this context we are at the top level
		// and not in a macro definition
	}
}