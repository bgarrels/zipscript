package hudson.zipscript.template;

import hudson.zipscript.parser.exception.ExecutionException;

public interface EvaluationTemplate {

	public boolean booleanValue (Object context) throws ExecutionException;

	public Object objectValue (Object context) throws ExecutionException;
}
