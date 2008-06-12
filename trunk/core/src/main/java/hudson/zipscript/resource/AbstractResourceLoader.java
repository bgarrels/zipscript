package hudson.zipscript.resource;

import java.io.IOException;
import java.io.InputStream;

import hudson.zipscript.parser.exception.InitializationException;
import hudson.zipscript.template.Template;

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
