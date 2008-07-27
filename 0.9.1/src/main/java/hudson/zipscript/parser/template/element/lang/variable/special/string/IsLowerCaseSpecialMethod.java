package hudson.zipscript.parser.template.element.lang.variable.special.string;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.template.element.lang.variable.special.SpecialMethod;

public class IsLowerCaseSpecialMethod implements SpecialMethod {

	public static final IsLowerCaseSpecialMethod INSTANCE = new IsLowerCaseSpecialMethod();

	public Object execute(Object source, ExtendedContext context) throws Exception {
		String s = source.toString();
		return new Boolean(s.toLowerCase().equals(s));
	}
}