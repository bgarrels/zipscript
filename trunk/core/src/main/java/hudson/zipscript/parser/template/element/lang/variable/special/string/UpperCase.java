package hudson.zipscript.parser.template.element.lang.variable.special.string;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.template.element.lang.variable.special.SpecialMethod;

public class UpperCase implements SpecialMethod {

	public static final UpperCase INSTANCE = new UpperCase();

	public Object execute(Object source, ZSContext context) throws Exception {
		return source.toString().toUpperCase();
	}
}