package hudson.zipscript.parser.template.element.lang.variable.special.sequence;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.template.element.lang.variable.special.SpecialMethod;

import java.util.Collection;


public class LengthSpecialMethod implements SpecialMethod {

	public LengthSpecialMethod () {
	}

	public Object execute(Object source, ExtendedContext context) throws Exception {
		if (source instanceof Object[]) {
			Object[] arr = (Object[]) source;
			return new Integer(arr.length);
		}
		else if (source instanceof Collection) {
			Collection c = (Collection) source;
			return new Integer(c.size());
		}
		else return new Integer(0);
	}
}