package hudson.zipscript.parser.template.element.lang.variable.special.object;

import java.util.Date;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.template.element.lang.variable.special.SpecialMethod;

public class IsDateSpecialMethod implements SpecialMethod {

	public static IsDateSpecialMethod INSTANCE = new IsDateSpecialMethod();

	public Object execute(Object source, ZSContext context) throws Exception {
		return new Boolean(source instanceof Date);
	}
}
