package hudson.zipscript.resource;

import hudson.zipscript.parser.exception.InitializationException;

import org.apache.commons.configuration.Configuration;

public abstract class AbstractResourceLoader implements ResourceLoader {

	private String pathPrefix;

	public void config(Configuration config) throws InitializationException {
		pathPrefix = config.getString("pathPrefix");
	}

	protected String getRealPath (String path) {
		if (null == pathPrefix) return path;
		else return pathPrefix + path;
	}
}
