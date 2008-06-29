package hudson.zipscript.parser.template.element.lang.variable.special.sequence;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.template.element.lang.variable.special.SpecialMethod;

import java.util.Collection;

public class FirstSpecialMethod implements SpecialMethod {

	public Object execute(
			Object source, ZSContext context) throws Exception {
		if (source instanceof Object[]) {
			if (((Object[]) source).length > 0) {
				return ((Object[]) source)[0];
			}
			else return null;
		}
		else if (source instanceof Collection) {
			if (((Collection) source).size() > 0) {
				return ((Collection) source).iterator().next();
			}
			else return null;
		}
		else return null;
	}

}
