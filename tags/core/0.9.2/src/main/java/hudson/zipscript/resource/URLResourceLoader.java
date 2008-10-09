package hudson.zipscript.resource;

import hudson.zipscript.parser.exception.ExecutionException;

import java.net.MalformedURLException;
import java.net.URL;

public class URLResourceLoader extends AbstractResourceLoader {

	public URLResourceLoader () {
		super();
	}

	public URLResourceLoader (String pathPrefix) {
		super (pathPrefix);
	}

	public Resource getResource(String path, Object parameter) {
		try {
			return new URLResource(new URL(getRealPath(path)));
		}
		catch (MalformedURLException e) {
			throw new ExecutionException("Invalid URL path '" + getRealPath(path), null);
		}
	}
}