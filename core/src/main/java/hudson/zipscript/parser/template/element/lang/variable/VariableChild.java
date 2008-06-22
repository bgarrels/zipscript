package hudson.zipscript.parser.template.element.lang.variable;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ExecutionException;

public interface VariableChild {

	public Object execute (Object parent, ZSContext context) throws ExecutionException;

	public String getPropertyName ();

	public boolean shouldReturnSomething();
}
