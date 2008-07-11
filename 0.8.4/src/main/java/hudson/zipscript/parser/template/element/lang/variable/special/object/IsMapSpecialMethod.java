package hudson.zipscript.parser.template.element.lang.variable.special.object;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.template.element.lang.variable.special.SpecialMethod;

import java.util.Map;

public class IsMapSpecialMethod implements SpecialMethod {

	public static IsMapSpecialMethod INSTANCE = new IsMapSpecialMethod();

	public Object execute(Object source, ExtendedContext context) throws Exception {
		return new Boolean(source instanceof Map);
	}
}
