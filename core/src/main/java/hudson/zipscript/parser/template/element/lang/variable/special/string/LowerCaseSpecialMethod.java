package hudson.zipscript.parser.template.element.lang.variable.special.string;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.template.element.lang.variable.special.SpecialMethod;

public class LowerCaseSpecialMethod implements SpecialMethod {

	public static final LowerCaseSpecialMethod INSTANCE = new LowerCaseSpecialMethod();

	public Object execute(Object source, ZSContext context) throws Exception {
		return source.toString().toLowerCase();
	}
}