package hudson.zipscript.parser.template.element.lang.variable.special.number;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.template.element.lang.variable.special.SpecialMethod;

public class Ceiling implements SpecialMethod {

	public static final Ceiling INSTANCE = new Ceiling();

	public Object execute(Object source, ZSContext context) throws Exception {
		return new Long((long) Math.ceil(((Number) source).doubleValue()));
	}
}