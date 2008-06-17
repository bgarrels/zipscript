package hudson.zipscript.parser.template.element.lang.variable.special.sequence;

import java.util.Collection;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.template.element.lang.variable.special.SpecialMethod;

public class LastSpecialMethod implements SpecialMethod {

	public Object execute(
			Object source, ZSContext context) throws Exception {
		if (source instanceof Object[]) {
			Object[] o = (Object[]) source;
			if (o.length > 0) {
				return o[o.length-1];
			}
			else return null;
		}
		else if (source instanceof Collection) {
			Collection c = (Collection) source;
			if (c.size() > 0) {
				return c.toArray()[c.size()-1];
			}
			else return null;
		}
		else return null;
	}
}