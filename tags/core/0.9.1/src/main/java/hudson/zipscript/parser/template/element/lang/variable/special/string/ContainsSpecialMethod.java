package hudson.zipscript.parser.template.element.lang.variable.special.string;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.lang.variable.special.SpecialMethod;


public class ContainsSpecialMethod implements SpecialMethod {

	private Element checkElement;

	public ContainsSpecialMethod (Element[] vars) {
		if (null != vars && vars.length > 0) {
			checkElement = vars[0];
		}
	}

	public Object execute(Object source, ExtendedContext context) throws Exception {
		String s = source.toString();
		String check = checkElement.objectValue(context).toString();
		if (s.indexOf(check) >= 0) return Boolean.TRUE;
		else return Boolean.FALSE;
	}
}