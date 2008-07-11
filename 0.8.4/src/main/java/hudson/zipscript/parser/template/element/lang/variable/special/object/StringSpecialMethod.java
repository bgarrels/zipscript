package hudson.zipscript.parser.template.element.lang.variable.special.object;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.template.element.ToStringWithContextElement;
import hudson.zipscript.parser.template.element.lang.variable.special.SpecialMethod;

public class StringSpecialMethod implements SpecialMethod {

	private SpecialMethod stringSpecialMethod;

	public StringSpecialMethod (SpecialMethod stringSpecialMethod) {
		this.stringSpecialMethod = stringSpecialMethod;
	}

	public Object execute(Object source, ExtendedContext context) throws Exception {
		if (null == source) return null;
		else if ((source instanceof String)) {
			if (source instanceof ToStringWithContextElement) {
				source = ((ToStringWithContextElement) source).toString(context);
			}
			else {
				source = source.toString();
			}
		}
		return stringSpecialMethod.execute(source, context);
	}
}
