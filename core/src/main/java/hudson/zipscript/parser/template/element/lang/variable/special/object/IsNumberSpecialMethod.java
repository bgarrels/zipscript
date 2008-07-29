package hudson.zipscript.parser.template.element.lang.variable.special.object;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.template.element.lang.variable.adapter.RetrievalContext;
import hudson.zipscript.parser.template.element.lang.variable.special.SpecialMethod;

public class IsNumberSpecialMethod implements SpecialMethod {

	public static IsNumberSpecialMethod INSTANCE = new IsNumberSpecialMethod();

	public Object execute(
			Object source, RetrievalContext retrievalContext, ExtendedContext context) throws Exception {
		return new Boolean(source instanceof Number);
	}

	public RetrievalContext getExpectedType() {
		return RetrievalContext.SCALAR;
	}
}
