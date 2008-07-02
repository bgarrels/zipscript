package hudson.zipscript.parser.template.element.lang.variable.special.object;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.template.element.lang.variable.special.SpecialMethod;

public class IsBooleanSpecialMethod implements SpecialMethod {

	public static IsBooleanSpecialMethod INSTANCE = new IsBooleanSpecialMethod();

	public Object execute(Object source, ZSContext context) throws Exception {
		return new Boolean(source instanceof Boolean);
	}
}
