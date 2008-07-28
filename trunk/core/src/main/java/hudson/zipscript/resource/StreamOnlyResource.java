package hudson.zipscript.resource;

import java.io.InputStream;

public class StreamOnlyResource extends AbstractResource {

	private InputStream is;

	public StreamOnlyResource (InputStream is) {
		this.is = is;
	}

	public InputStream getInputStream() {
		return is;
	}

	public boolean hasBeenModified() {
		return false;
	}
}
