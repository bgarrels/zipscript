package hudson.zipscript.parser.template.data;

import java.util.Map;

public class ParseParameters {

	public boolean cleanWhitespace = true;
	public boolean trim;
	private Map initParameters;

	public ParseParameters (boolean cleanWhitespace, boolean trim) {
		this (cleanWhitespace, trim, null);
	}

	public ParseParameters (boolean cleanWhitespace, boolean trim, Map initParameters) {
		this.cleanWhitespace = cleanWhitespace;
		this.trim = trim;
		this.initParameters = initParameters;
	}

	public Object getProperty (Object key) {
		if (null == initParameters) return null;
		else return initParameters.get(key);
	}

	public Map getInitParameters () {
		return initParameters;
	}
}
