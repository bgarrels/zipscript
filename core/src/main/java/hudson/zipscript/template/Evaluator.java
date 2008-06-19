package hudson.zipscript.template;

import hudson.zipscript.parser.exception.ExecutionException;

public interface Evaluator {

	public boolean booleanValue (Object context) throws ExecutionException;

	public Object objectValue (Object context) throws ExecutionException;
}
