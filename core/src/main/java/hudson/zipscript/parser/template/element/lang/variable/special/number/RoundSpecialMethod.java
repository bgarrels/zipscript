package hudson.zipscript.parser.template.element.lang.variable.special.number;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.template.element.lang.variable.adapter.RetrievalContext;
import hudson.zipscript.parser.template.element.lang.variable.special.SpecialMethod;

public class RoundSpecialMethod implements SpecialMethod {

	public static final RoundSpecialMethod INSTANCE = new RoundSpecialMethod();

	public Object execute(
			Object source, RetrievalContext retrievalContext, ExtendedContext context) throws Exception {
		return new Long(Math.round(((Number) source).doubleValue()));
	}

	public RetrievalContext getExpectedType() {
		return RetrievalContext.NUMBER;
	}
}
