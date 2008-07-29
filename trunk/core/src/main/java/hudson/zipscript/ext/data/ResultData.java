package hudson.zipscript.ext.data;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ResultData implements Map {

	private static final char SEPARATOR = ';';
	private static final char ASSIGNMENT = '=';
	private static final String LAYOUT = "layout";
	private static final String PAGE = "page";
	
	private String template;
	private String page;
	private String layout;
	private Map parameters;

	public ResultData (String templateLocation) {
		int index = templateLocation.indexOf(']');
		if (index >= 0 && templateLocation.charAt(0) == '[') {
			this.template = templateLocation.substring(index+1).trim();
			loadParams(templateLocation.substring(1, index));
		}
		else {
			this.template = templateLocation;
		}
	}

	private void loadParams(String params) {
		StringBuffer key = new StringBuffer();
		StringBuffer value = new StringBuffer();
		StringBuffer currentBuffer = key;
		
		for (int i=0; i<params.length(); i++) {
			char c = params.charAt(i);
			if (c == ASSIGNMENT) {
				currentBuffer = value;
			}
			else if (c == SEPARATOR) {
				setProperty(key, value);
				key = new StringBuffer();
				value = new StringBuffer();
				currentBuffer = key;
			}
			else
				currentBuffer.append(c);
		}
		setProperty(key, value);
	}

	private void setProperty (StringBuffer key, StringBuffer value) {
		String keyS = key.toString().trim();
		String valueS = value.toString().trim();
		if (keyS.length() > 0 && valueS.length() > 0) {
			if (keyS.equals(LAYOUT))
				this.layout = valueS;
			else if (keyS.equals(PAGE))
				this.page = valueS;
			else {
				if (null == parameters)
					parameters = new HashMap();
				parameters.put(keyS, valueS);
			}
		}
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public String getParameter (String key) {
		return (String) get(key);
	}

	public void clear() {
		if (null != parameters)
			parameters.clear();
	}

	public boolean containsKey(Object key) {
		if (null == parameters) return false;
		else return parameters.containsKey(key);
	}

	public boolean containsValue(Object value) {
		if (null == parameters) return false;
		else return parameters.containsValue(value);
	}

	public Set entrySet() {
		if (null == parameters)
			return Collections.EMPTY_SET;
		else
			return parameters.entrySet();
	}

	public Object get(Object key) {
		if (null == parameters) return null;
		else return parameters.get(key);
	}

	public boolean isEmpty() {
		if (null == parameters)
			return true;
		else
			return parameters.isEmpty();
	}

	public Set keySet() {
		if (null == parameters)
			return Collections.EMPTY_SET;
		else
			return parameters.keySet();
	}

	public Object put(Object arg0, Object arg1) {
		if (null == parameters)
			parameters = new HashMap();
		return parameters.put(arg0, arg1);
	}

	public void putAll(Map arg0) {
		if (null == parameters)
			parameters = new HashMap();
		parameters.putAll(arg0);
	}

	public Object remove(Object key) {
		if (null == parameters) return null;
		else return parameters.remove(key);
	}

	public int size() {
		if (null == parameters) return 0;
		else return parameters.size();
	}

	public Collection values() {
		if (null == parameters) return null;
		else return Collections.EMPTY_LIST;
	}
}