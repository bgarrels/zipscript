package hudson.zipscript.template;

import java.io.StringWriter;

import hudson.zipscript.parser.exception.ExecutionException;

public interface Template {

	public String merge (Object context) throws ExecutionException;

	public void merge (Object context, StringWriter writer) throws ExecutionException;
}
