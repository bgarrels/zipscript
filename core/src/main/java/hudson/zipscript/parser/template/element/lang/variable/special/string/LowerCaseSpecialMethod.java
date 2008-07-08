package hudson.zipscript.parser.template.element.lang.variable.special.string;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.template.element.lang.variable.special.SpecialMethod;

public class LowerCaseSpecialMethod implements SpecialMethod {

	public static final LowerCaseSpecialMethod INSTANCE = new LowerCaseSpecialMethod();

	public Object execute(Object source, ExtendedContext context) throws Exception {
		return source.toString().toLowerCase();
	}
}