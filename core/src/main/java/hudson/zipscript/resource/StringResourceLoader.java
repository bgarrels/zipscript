package hudson.zipscript.resource;

import java.io.ByteArrayInputStream;

public class StringResourceLoader extends AbstractResourceLoader {

	public static StringResourceLoader INSTANCE = new StringResourceLoader();

	public Resource getResource(String path) {
		return new StreamOnlyResource(new ByteArrayInputStream(path.getBytes()));
	}
}