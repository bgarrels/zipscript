package hudson.zipscript.resource;

import java.io.File;

public class FileResourceLoader extends AbstractResourceLoader {

	public Resource getResource(String path, Object parameter) {
		return new FileResource(new File(getRealPath(path)));
	}
}