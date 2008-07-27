package hudson.zipscript.parser.template.element.lang.variable.special.string;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.template.element.lang.variable.special.SpecialMethod;

public class IsUpperCaseSpecialMethod implements SpecialMethod {

	public static final IsUpperCaseSpecialMethod INSTANCE = new IsUpperCaseSpecialMethod();

	public Object execute(Object source, ExtendedContext context) throws Exception {
		String s = source.toString();
		return new Boolean(s.toUpperCase().equals(s));
	}
}