package hudson.zipscript.resource;

import hudson.zipscript.parser.exception.InitializationException;

import java.util.Map;

public interface ResourceLoader {

	public void config (Map properties) throws InitializationException;

	public Resource getResource(String path);
}
