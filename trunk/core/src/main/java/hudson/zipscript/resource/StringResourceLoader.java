package hudson.zipscript.resource;

import java.io.ByteArrayInputStream;
import java.io.File;

public class StringResourceLoader extends AbstractResourceLoader {

	private String path;
	private File sourceFile;

	public Resource getResource(String path) {
		return new StreamOnlyResource(new ByteArrayInputStream(path.getBytes()));
	}
}