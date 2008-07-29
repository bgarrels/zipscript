package hudson.zipscript.parser.template.element.lang.variable.special.string;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.template.element.lang.variable.adapter.RetrievalContext;
import hudson.zipscript.parser.template.element.lang.variable.special.SpecialMethod;

public class UpperCaseSpecialMethod implements SpecialMethod {

	public static final UpperCaseSpecialMethod INSTANCE = new UpperCaseSpecialMethod();

	public Object execute(
			Object source, RetrievalContext retrievalContext, ExtendedContext context) throws Exception {
		return source.toString().toUpperCase();
	}

	public RetrievalContext getExpectedType() {
		return RetrievalContext.TEXT;
	}
}