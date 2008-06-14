package hudson.zipscript.parser.template.element.lang.variable;

import hudson.zipscript.parser.context.ZSContext;

public interface VariableTokenSeparatorElement {

	public boolean requiresInput(ZSContext context);

	public Object execute (Object source, ZSContext context);
}
