package hudson.zipscript.resource;

import hudson.zipscript.parser.exception.InitializationException;

import org.apache.commons.configuration.Configuration;

public interface ResourceLoader {

	public void config (Configuration config) throws InitializationException;

	public Resource getResource(String path);
}
