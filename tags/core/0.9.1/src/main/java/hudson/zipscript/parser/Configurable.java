package hudson.zipscript.parser;

import hudson.zipscript.parser.exception.InitializationException;

import java.util.Map;

public interface Configurable {

	public void configure (Map properties) throws InitializationException;
}
