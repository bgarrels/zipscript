package hudson.zipscript.resource;

import java.io.InputStream;


public interface Resource {

	public InputStream getInputStream ();

	public boolean hasBeenModified ();
}
