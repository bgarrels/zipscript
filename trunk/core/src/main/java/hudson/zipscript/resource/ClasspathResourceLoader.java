package hudson.zipscript.resource;

import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.exception.InitializationException;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.configuration.Configuration;

public class ClasspathResourceLoader extends AbstractResourceLoader {

	private String path;
	private File sourceFile;

	public Resource getResource(String path) {
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(
				getRealPath(path));
		if (null == is) {
			throw new ExecutionException("Invalid classpath resource '" + getRealPath(path), null);
		}
		return new StreamOnlyResource(is);
	}
}