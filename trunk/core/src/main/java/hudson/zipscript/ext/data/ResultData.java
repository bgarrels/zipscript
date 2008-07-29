package hudson.zipscript.ext.data;

import java.util.HashMap;
import java.util.Map;

public class ResultData {

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

	public Object getParameter (String key) {
		if (null == parameters) return null;
		else return parameters.get(key);
	}
}
