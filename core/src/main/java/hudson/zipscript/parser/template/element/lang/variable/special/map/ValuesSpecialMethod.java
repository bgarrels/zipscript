package hudson.zipscript.parser.template.element.lang.variable.special.map;

import java.util.Map;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.template.element.lang.variable.special.SpecialMethod;

public class ValuesSpecialMethod implements SpecialMethod {

	public static final ValuesSpecialMethod INSTANCE = new ValuesSpecialMethod();

	public Object execute(Object source, ZSContext context) throws Exception {
		return ((Map) source).values();
	}
}
