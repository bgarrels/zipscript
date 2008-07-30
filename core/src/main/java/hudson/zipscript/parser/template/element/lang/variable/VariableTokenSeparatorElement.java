package hudson.zipscript.parser.template.element.lang.variable;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.template.element.lang.variable.adapter.RetrievalContext;

public interface VariableTokenSeparatorElement {

	public boolean requiresInput();

	public Object execute (
			Object source, RetrievalContext retrievalContext,
			String contextHint, ExtendedContext context);

	public RetrievalContext getExpectedType ();
}
