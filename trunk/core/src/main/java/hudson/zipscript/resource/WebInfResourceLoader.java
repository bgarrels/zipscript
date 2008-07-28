package hudson.zipscript.resource;

import hudson.zipscript.parser.exception.ExecutionException;

import java.io.InputStream;

import javax.servlet.ServletContext;

public class WebInfResourceLoader extends AbstractResourceLoader {

	private static final String PREFIX = "WEB-INF/";

	public Resource getResource(String path, Object servletContext) {
		InputStream is = ((ServletContext) servletContext).getResourceAsStream(
				PREFIX + getRealPath(path));
		if (null == is) {
				is = ClassLoader.getSystemResourceAsStream(getRealPath(path));
		}
		if (null == is) {
			throw new ExecutionException("Invalid classpath resource '" + getRealPath(path), null);
		}
		return new StreamOnlyResource(is);
	}
}