package hudson.zipscript.parser.template.element.lang.variable.special.number;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.template.element.lang.variable.special.SpecialMethod;

public class FloorSpecialMethod implements SpecialMethod {

	public static final FloorSpecialMethod INSTANCE = new FloorSpecialMethod();

	public Object execute(Object source, ExtendedContext context) throws Exception {
		return new Long((long) Math.floor(((Number) source).doubleValue()));
	}
}