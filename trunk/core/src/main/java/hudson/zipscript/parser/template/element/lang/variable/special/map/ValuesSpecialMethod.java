package hudson.zipscript.parser.template.element.lang.variable.special.map;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.template.element.lang.variable.special.SpecialMethod;

import java.util.Map;

public class ValuesSpecialMethod implements SpecialMethod {

	public static final ValuesSpecialMethod INSTANCE = new ValuesSpecialMethod();

	public Object execute(Object source, ExtendedContext context) throws Exception {
		return ((Map) source).values();
	}
}
