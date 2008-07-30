package hudson.zipscript.parser.template.element.lang.variable.special.object;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.template.element.lang.variable.adapter.RetrievalContext;
import hudson.zipscript.parser.template.element.lang.variable.special.SpecialMethod;

import org.w3c.dom.Node;

public class IsXMLSpecialMethod implements SpecialMethod {

	public static IsXMLSpecialMethod INSTANCE = new IsXMLSpecialMethod();

	public Object execute(
			Object source, RetrievalContext retrievalContext, String contextHint,
			ExtendedContext context) throws Exception {
		return new Boolean(source instanceof Node);
	}

	public RetrievalContext getExpectedType() {
		return RetrievalContext.UNKNOWN;
	}
}
