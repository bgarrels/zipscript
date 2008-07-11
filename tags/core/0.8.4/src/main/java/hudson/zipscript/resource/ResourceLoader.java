package hudson.zipscript.resource;

import hudson.zipscript.parser.Configurable;


public interface ResourceLoader extends Configurable {

	public Resource getResource(String path);
}
