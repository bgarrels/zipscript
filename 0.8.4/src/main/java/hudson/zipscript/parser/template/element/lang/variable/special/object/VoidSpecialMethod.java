package hudson.zipscript.parser.template.element.lang.variable.special.object;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.template.element.lang.variable.special.SpecialMethod;

public class VoidSpecialMethod implements SpecialMethod {

	private static String RTN = "";
	public static VoidSpecialMethod INSTANCE = new VoidSpecialMethod();

	public Object execute(Object source, ExtendedContext context) throws Exception {
		return RTN;
	}
}
