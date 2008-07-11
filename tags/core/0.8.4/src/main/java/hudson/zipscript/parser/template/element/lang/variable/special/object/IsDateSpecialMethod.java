package hudson.zipscript.parser.template.element.lang.variable.special.object;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.template.element.lang.variable.special.SpecialMethod;

import java.util.Date;

public class IsDateSpecialMethod implements SpecialMethod {

	public static IsDateSpecialMethod INSTANCE = new IsDateSpecialMethod();

	public Object execute(Object source, ExtendedContext context) throws Exception {
		return new Boolean(source instanceof Date);
	}
}
