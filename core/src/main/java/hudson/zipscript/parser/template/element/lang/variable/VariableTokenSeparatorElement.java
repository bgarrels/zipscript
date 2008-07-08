package hudson.zipscript.parser.template.element.lang.variable;

import hudson.zipscript.parser.context.ExtendedContext;

public interface VariableTokenSeparatorElement {

	public boolean requiresInput(ExtendedContext context);

	public Object execute (Object source, ExtendedContext context);
}
