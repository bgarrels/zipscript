package hudson.zipscript.parser.template.data;


public class ParseParameters {

	public boolean cleanWhitespace = true;
	public boolean trim;
	private ResourceContainer resourceContainer;

	public ParseParameters (
			ResourceContainer resourceContainer, boolean cleanWhitespace, boolean trim) {
		this.cleanWhitespace = cleanWhitespace;
		this.trim = trim;
		this.resourceContainer = resourceContainer;
	}

	public Object getProperty (Object key) {
		if (null == resourceContainer.getInitParameters()) return null;
		else return resourceContainer.getInitParameters().get(key);
	}

	public ResourceContainer getResourceContainer() {
		return resourceContainer;
	}
}
