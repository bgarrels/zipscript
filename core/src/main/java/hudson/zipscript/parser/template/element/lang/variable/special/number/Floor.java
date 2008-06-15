package hudson.zipscript.parser.template.element.lang.variable.special.number;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.template.element.lang.variable.special.SpecialMethod;

public class Floor implements SpecialMethod {

	public static final Floor INSTANCE = new Floor();

	public Object execute(Object source, ZSContext context) throws Exception {
		return new Long((long) Math.floor(((Number) source).doubleValue()));
	}
}