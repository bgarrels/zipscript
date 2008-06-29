package hudson.zipscript.parser.template.element.lang.variable.special;

import hudson.zipscript.parser.context.ZSContext;

public interface SpecialMethod {

	public Object execute (Object source, ZSContext context) throws Exception;
}
