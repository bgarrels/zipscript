package hudson.zipscript.resource;

import hudson.zipscript.parser.exception.ExecutionException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class URLResource extends AbstractResource {

	URL resource;

	public URLResource (URL resource) {
		this.resource = resource;
	}

	public InputStream getInputStream() {
		try {
			return resource.openStream();
		}
		catch (IOException e) {
			throw new ExecutionException("The file '" + resource.getPath() + "' could not be located", null, e);
		}
	}

	public boolean hasBeenModifiedSince(long currentMilis) {
		return false;
	}
}
