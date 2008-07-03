package hudson.zipscript.parser.template.element.lang.variable.special.object;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.template.element.lang.variable.special.SpecialMethod;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;

public class IsSequenceSpecialMethod implements SpecialMethod {

	public static IsSequenceSpecialMethod INSTANCE = new IsSequenceSpecialMethod();

	public Object execute(Object source, ZSContext context) throws Exception {
		return new Boolean(source instanceof Object[]
		    || source instanceof Collection
		    || source instanceof Iterator
		    || source instanceof Enumeration);
	}
}
