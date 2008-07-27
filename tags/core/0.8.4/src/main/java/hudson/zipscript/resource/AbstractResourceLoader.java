package hudson.zipscript.resource;

import hudson.zipscript.parser.exception.InitializationException;

import java.util.Map;

public abstract class AbstractResourceLoader implements ResourceLoader {

	private String pathPrefix;

	public void configure(Map properties) throws InitializationException {
		pathPrefix = (String) properties.get("pathPrefix");
	}

	protected String getRealPath (String path) {
		if (null == pathPrefix) return path;
		else return pathPrefix + path;
	}
}