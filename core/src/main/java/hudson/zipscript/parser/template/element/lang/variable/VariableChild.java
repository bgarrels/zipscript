package hudson.zipscript.parser.template.element.lang.variable;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.template.element.lang.variable.adapter.RetrievalContext;

public interface VariableChild {

	public Object execute (Object parent, ExtendedContext context) throws ExecutionException;

	public String getPropertyName ();

	public boolean shouldReturnSomething();

	public void setRetrievalContext (RetrievalContext retrievalContext);
}
