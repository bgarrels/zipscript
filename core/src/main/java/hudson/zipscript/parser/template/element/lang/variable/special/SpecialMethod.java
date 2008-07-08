package hudson.zipscript.parser.template.element.lang.variable.special;

import hudson.zipscript.parser.context.ExtendedContext;

public interface SpecialMethod {

	public Object execute (Object source, ExtendedContext context) throws Exception;
}
