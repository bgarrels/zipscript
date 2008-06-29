package hudson.zipscript.parser.template.element.lang.variable.special.number;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.template.element.lang.variable.special.SpecialMethod;

public class RoundSpecialMethod implements SpecialMethod {

	public static final RoundSpecialMethod INSTANCE = new RoundSpecialMethod();

	public Object execute(Object source, ZSContext context) throws Exception {
		return new Long(Math.round(((Number) source).doubleValue()));
	}

}
