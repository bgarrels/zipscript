package hudson.zipscript.parser.template.element.lang.variable.special;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.template.element.lang.variable.adapter.RetrievalContext;

public interface SpecialMethod {

	public Object execute (
			Object source, RetrievalContext retrievalContext, ExtendedContext context) throws Exception;

	public RetrievalContext getExpectedType ();
}
