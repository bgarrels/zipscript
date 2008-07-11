package hudson.zipscript.resource;

import java.io.File;

public class FileResourceLoader extends AbstractResourceLoader {

	public Resource getResource(String path) {
		return new FileResource(new File(getRealPath(path)));
	}
}