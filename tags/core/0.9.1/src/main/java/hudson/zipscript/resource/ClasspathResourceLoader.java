package hudson.zipscript.resource;

import hudson.zipscript.parser.exception.ExecutionException;

import java.io.InputStream;

public class ClasspathResourceLoader extends AbstractResourceLoader {

	public Resource getResource(String path) {
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(getRealPath(path));
		if (null == is) {
				is = ClassLoader.getSystemResourceAsStream(getRealPath(path));
		}
		if (null == is) {
			throw new ExecutionException("Invalid classpath resource '" + getRealPath(path), null);
		}
		return new StreamOnlyResource(is);
	}
}