package hudson.zipscript.template;

import hudson.zipscript.parser.exception.ExecutionException;

import java.io.StringWriter;

public interface Template {

	public String merge (Object context) throws ExecutionException;

	public void merge (Object context, StringWriter writer) throws ExecutionException;
}
