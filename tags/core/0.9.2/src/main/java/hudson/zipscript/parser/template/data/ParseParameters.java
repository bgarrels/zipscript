package hudson.zipscript.parser.template.data;

import hudson.zipscript.ResourceContainer;


public class ParseParameters {

	public boolean cleanWhitespace = true;
	public boolean trim;
	private ResourceContainer resourceContainer;

	public ParseParameters (
			ResourceContainer resourceContainer,
			boolean cleanWhitespace, boolean trim) {
		this.cleanWhitespace = cleanWhitespace;
		this.trim = trim;
		this.resourceContainer = resourceContainer;
	}

	public Object getProperty (Object key) {
		return resourceContainer.getInitParameters().get(key);
	}

	public boolean getPropertyAsBoolean (Object key, boolean defaultVal) {
		Object val = resourceContainer.getInitParameters().get(key);
		if (null == val) return defaultVal;
		else if (val instanceof Boolean) return ((Boolean) val).booleanValue();
		else if (val instanceof String) return Boolean.TRUE.equals(val);
		else return defaultVal;
	}

	public ResourceContainer getResourceContainer() {
		return resourceContainer;
	}
}
